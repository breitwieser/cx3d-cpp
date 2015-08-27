package ini.cx3d;

import ini.cx3d.swig.example.Square;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


/**
 * The two tests use two different implementations of the object Square
 * testSquareArea uses the implementation provided in libcx3d_example.so
 * testSquareDiagonal libcx3d_module2.so
 */
public class CppCodeIntegrationTest {

    static {
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("cx3d_example");
        System.loadLibrary("cx3d_module2");
    }


    @Test
    public void testSquareArea() {
        Square square = new Square(2);
        assertEquals(4, square.area(), 0.01);
    }

    @Test
    public void testSquareDiagonal() {
        ini.cx3d.swig.module2.Square square = new ini.cx3d.swig.module2.Square(3);
        assertEquals(3 * Math.sqrt(2), square.diagonal(), 0.01);
    }
}
