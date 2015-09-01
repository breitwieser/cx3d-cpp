//This file enables transparent type conversions between std::vector<double> (C++)
//and java.util.AbstractList<Double> (Java)
%{
#include <vector>
#include <stdexcept>
%}

namespace std {
    template<class T> class vector {
      public:
        typedef size_t size_type;
        typedef T value_type;
        typedef const value_type& const_reference;
        %rename(size_impl) size;
        vector();
        vector(size_type n);
        size_type size() const;
        size_type capacity() const;
        void reserve(size_type n);
        %rename(isEmpty) empty;
        bool empty() const;
        void clear();
        void push_back(const value_type& x);
        %extend {
            const_reference get_impl(int i) throw (std::out_of_range) {
                // at will throw if needed, swig will handle
                return self->at(i);
            }
            void set_impl(int i, const value_type& val) throw (std::out_of_range) {
                // at can throw
                self->at(i) = val;
            }
        }
    };
}

%typemap(javabase) std::vector<double> "java.util.AbstractList<Double>"
%typemap(javainterface) std::vector<double> "java.util.RandomAccess"
%typemap(javacode) std::vector<double> %{
  public Double get(int idx) {
    return get_impl(idx);
  }

  public int size() {
    return (int)size_impl();
  }

  public Double set(int idx, Double d) {
    Double old = get_impl(idx);
    set_impl(idx, d.doubleValue());
    return old;
  }
%}

//define concrete templates
namespace std {
  %template(Vector) std::vector<double>;
}
