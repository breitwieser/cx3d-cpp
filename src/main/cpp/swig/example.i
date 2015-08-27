/* File : example.i */
%module example

%{
#include "example.h"
using namespace cx3d::example;
%}

/* Let's just grab the original header file here */
%include "example.h"
