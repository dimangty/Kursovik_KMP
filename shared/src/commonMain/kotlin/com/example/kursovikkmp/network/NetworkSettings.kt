package com.example.kursovikkmp.network

class NetworkSettings {
    val serverUrl = "https://newsapi.org/v2/"
    val apiKey = "34f4c8a4cfe647f5acd1e7d709464b34"
    val newsApiUrl = "${serverUrl}/everything?q=russia&language=ru&sortBy=publishedAt&apiKey=${apiKey}"
}