cmake_minimum_required(VERSION 3.10)
project("flam_native")

add_library(native-lib SHARED native_lib.cpp)

find_library(log-lib log)

target_link_libraries(native-lib ${log-lib})
