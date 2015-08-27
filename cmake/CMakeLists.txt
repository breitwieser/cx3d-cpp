cmake_minimum_required(VERSION 2.8.9)
project(directory_test)
#set(CMAKE_BUILD_TYPE Release)
SET(CPP_PROJECT_PATH ../src/main/cpp)
SET(JAVA_PROJECT_RESOURCE_PATH ../src/main/resources)
SET(JAVA_PROJECT_SRC_PATH ../src/main/java/ini/cx3d/swig)

#set output directory
set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/${JAVA_PROJECT_RESOURCE_PATH})
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/${JAVA_PROJECT_RESOURCE_PATH})
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/${JAVA_PROJECT_RESOURCE_PATH})

#--------------------- add source files to project -------------
#add the headers into the project
include_directories(${CPP_PROJECT_PATH}/include)
include_directories(${CPP_PROJECT_PATH}/swig)

#add the sources into the project
file(GLOB SOURCES "${CPP_PROJECT_PATH}/src/*.cpp")

#--------------------- setup swig ------------------------------
#swig Java wrappers
FIND_PACKAGE(SWIG REQUIRED)
INCLUDE(${SWIG_USE_FILE})

FIND_PACKAGE(JNI REQUIRED)
INCLUDE_DIRECTORIES(${JAVA_INCLUDE_PATH})       #path to jni.h
INCLUDE_DIRECTORIES(${JAVA_INCLUDE_PATH2})      #path to jni_md.h

#--------------------- build module example  ----------------
#SET(CMAKE_SWIG_FLAGS "")
SET(CMAKE_SWIG_FLAGS -package ini.cx3d.swig.example -c++)
set(CMAKE_SWIG_OUTDIR ${JAVA_PROJECT_SRC_PATH}/example)

SET_SOURCE_FILES_PROPERTIES(${CPP_PROJECT_PATH}/swig/example.i PROPERTIES CPLUSPLUS ON)
#SET_SOURCE_FILES_PROPERTIES(${CPP_PROJECT_PATH}/swig/example.i PROPERTIES SWIG_FLAGS "-includeall")
#set_property(SOURCE ${CPP_PROJECT_PATH}/swig/example.i PROPERTY SWIG_FLAGS "-includeall")

SWIG_ADD_MODULE(cx3d_example java ${CPP_PROJECT_PATH}/swig/example.i ${SOURCES})
#SWIG_LINK_LIBRARIES(cx3d_example ${JAVA_LIBRARIES})

#--------------------- build module module2  ----------------
SET(CMAKE_SWIG_FLAGS -package ini.cx3d.swig.module2 -c++)
set(CMAKE_SWIG_OUTDIR ${JAVA_PROJECT_SRC_PATH}/module2)

SET_SOURCE_FILES_PROPERTIES(${CPP_PROJECT_PATH}/swig/module2.i PROPERTIES CPLUSPLUS ON)
SWIG_ADD_MODULE(cx3d_module java ${CPP_PROJECT_PATH}/swig/module2.i ${SOURCES})



#set(CMAKE_SWIG_OUTDIR ${JAVA_PROJECT_SRC_PATH}2)
#SET(CMAKE_SWIG_FLAGS -package ini.cx3d.swig2 -c++)
#SWIG_ADD_MODULE(cx3d2 java ${CPP_PROJECT_PATH}/swig/example.i ${SOURCES})

#Generate the shared library from the sources
#add_library(cx3d SHARED ${SOURCES})
