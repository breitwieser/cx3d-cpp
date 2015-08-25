/*
Copyright (C) 2009 Frédéric Zubler, Rodney J. Douglas,
Dennis Göhlsdorf, Toby Weston, Andreas Hauri, Roman Bauer,
Sabina Pfister & Adrian M. Whatley.

This file is part of CX3D.

CX3D is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CX3D is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CX3D.  If not, see <http://www.gnu.org/licenses/>.
*/

package ini.cx3d.simulations.tutorial;

import static ini.cx3d.utilities.Matrix.randomNoise;

import ini.cx3d.BaseSimulationTest;
import ini.cx3d.Param;
import ini.cx3d.cells.Cell;
import ini.cx3d.cells.CellFactory;
import ini.cx3d.localBiology.AbstractLocalBiologyModule;
import ini.cx3d.physics.IntracellularSubstance;
import ini.cx3d.physics.PhysicalObject;
import ini.cx3d.simulations.ECM;
import ini.cx3d.simulations.Scheduler;

public class MembraneContactTest extends BaseSimulationTest {
	public MembraneContactTest() {
		super(MembraneContactTest.class);
	}

	@Override
	public void simulation() throws Exception{
		ECM.setRandomSeed(1L);
		ECM ecm = ECM.getInstance();

		IntracellularSubstance adherence = new IntracellularSubstance("A",0,0);
		adherence.setVisibleFromOutside(true);
		adherence.setVolumeDependant(false);
		ecm.addNewIntracellularSubstanceTemplate(adherence);

		ecm.setArtificialWallsForSpheres(true);
		ecm.setBoundaries(-150, 150, -150, 150, -100, 100);

		for(int i = 0; i<10; i++){
			Cell c = CellFactory.getCellInstance(randomNoise(100, 3));
			c.setColorForAllPhysicalObjects(Param.RED);
			c.getSomaElement().getPhysical().modifyMembraneQuantity("A", 100000);
		}
		for(int i = 0; i<10; i++){
			Cell c = CellFactory.getCellInstance(randomNoise(50, 3));
			c.getSomaElement().addLocalBiologyModule(new MembraneContact());
			c.getSomaElement().addLocalBiologyModule(new SomaRandomWalkModule());
			c.setColorForAllPhysicalObjects(Param.VIOLET);
		}

		for (int i = 0; i < 1500; i++) {
			Scheduler.simulateOneStep();
		}

	}
}

class MembraneContact extends AbstractLocalBiologyModule {

	public AbstractLocalBiologyModule getCopy() {
		return new MembraneContact();
	}

	public void run() {		
		PhysicalObject physical = super.cellElement.getPhysical();
		for (PhysicalObject o: physical.getPhysicalObjectsInContact()) {
			if(o.getMembraneConcentration("A")>1){
				physical.setColor(Param.YELLOW);
				super.cellElement.cleanAllLocalBiologyModules();
			}
		}
	}

	@Override
	public StringBuilder simStateToJson(StringBuilder sb) {
//		super.simStateToJson(sb);

		sb.append("{}");
		return sb;
	}
}
