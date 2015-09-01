/* File : example.i */
%module example

%{
#include "example.h"
using namespace cx3d::example;
%}

%include "std_vector_typemap.i"


/* Let's just grab the original header file here */
%include "example.h"
