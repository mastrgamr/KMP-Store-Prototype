package com.example.kmptest

interface PlatformLocalIO {
    val name: String
    fun readFile(path : String, filename: String): Boolean
    fun saveFile(path : String, filename : String, nbLine : Int)
}

expect fun getPlatformIO(): PlatformLocalIO

