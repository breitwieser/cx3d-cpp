/* File : example.h */
#include <vector>

namespace cx3d { namespace example {
class Shape {
public:
  Shape() {
    nshapes++;
  }
  virtual ~Shape() {
    nshapes--;
  }
  double  x, y;
  void    move(double dx, double dy);
  virtual double area() = 0;
  virtual double perimeter() = 0;
  static  int nshapes;
};

class Circle : public Shape {
private:
  double radius;
public:
  Circle(double r) : radius(r) { }
  virtual double area();
  virtual double perimeter();
};

class Square : public Shape {
private:
  double width;
public:
  Square(double w) : width(w) { }
  virtual double area();
  virtual double perimeter();
};

class VectorHolder {
public:
  std::vector<double> getNewVector(){
    return std::vector<double>(10,1.0);
  }

  std::vector<double>& getContent(){
    return content_;
  }

  VectorHolder(): content_(std::vector<double>(5, 2.3)){ }

  virtual ~VectorHolder(){}

private:
  std::vector<double> content_;
};

}
}
