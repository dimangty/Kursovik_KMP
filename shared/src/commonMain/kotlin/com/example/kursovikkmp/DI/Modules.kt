package com.example.kursovikkmp.DI

import com.example.kursovikkmp.extensions.appLog
import com.example.kursovikkmp.feature.news.list.NewsListViewModel
import com.example.kursovikkmp.feature.news.list.model.NewsService
import com.example.kursovikkmp.network.DateSerializer
import com.example.kursovikkmp.network.DateTimeSerializer
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
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
            HttpClient {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }
                install(Logging) {
                    logger =  Logger.SIMPLE
                    level = LogLevel.BODY
                }
            }
        }
    }

    val api = module { single { NewsService(get(), get()) } }

}


object ViewModelsModule {
    val viewModels = module {
        factory { NewsListViewModel(get()) }
    }
}