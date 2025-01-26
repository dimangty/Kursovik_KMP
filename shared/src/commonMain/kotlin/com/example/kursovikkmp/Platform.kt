package com.example.kursovikkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform