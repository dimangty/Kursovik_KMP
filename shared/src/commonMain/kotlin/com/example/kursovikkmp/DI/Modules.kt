package com.example.kursovikkmp.DI

import com.example.kursovikkmp.feature.news.list.model.NewsService
import com.example.kursovikkmp.network.DateSerializer
import com.example.kursovikkmp.network.DateTimeSerializer
import info.javaway.spend_sense.extensions.appLog
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.dsl.module

object NetworkModule {
    val json = module {
        single {
            Json {
                encodeDefaults = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, DateTimeSerializer)
                    contextual(LocalDate::class, DateSerializer)
                }
            }
        }
    }

    val httpClient = module {
        single {
            HttpClient(CIO) {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLog(message)
                        }
                    }
                }
            }
        }
    }

    val api = module { single { NewsService(get(), get()) } }

}