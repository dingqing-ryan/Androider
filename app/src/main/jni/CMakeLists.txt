# 设置构建native library所需的CMake最低版本。
cmake_minimum_required(VERSION 3.4.1)

#创建一个库（多次调用add_library即可创建多个库）
add_library( # 设置库的名称
        libTest-lib

        # 将库设置为共享库（即so文件）
        SHARED

        # 指定源文件的相对路径
        Test.cpp)