package com.weesnerDevelopment.lavalamp.terminal

fun main(args: Array<String>) {
    println("program running")
    print("whats your name? ")
    val value = readlnOrNull()

    println("entered: $value")
}
