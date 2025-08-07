cmake_minimum_required(VERSION 3.18.1)

find_library(log-lib log)

target_link_libraries(${log-lib})
