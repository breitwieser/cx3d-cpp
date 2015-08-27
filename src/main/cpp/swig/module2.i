/* File : example.i */
%module module2

%{
#include "module2.h"
using namespace cx3d::module2;
%}

/* Let's just grab the original header file here */
%include "module2.h"
