package com.example.kursovikkmp.DI

import com.example.kursovikkmp.DB.ArticleDao
import com.example.kursovikkmp.DB.DatabaseDriverFactory
import com.example.kursovikkmp.Database
import com.example.kursovikkmp.feature.favorites.list.FavoritesRepository
import com.example.kursovikkmp.feature.news.NewsService
import com.example.kursovikkmp.network.DateSerializer
import com.example.kursovikkmp.network.DateTimeSerializer
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
                install(HttpTimeout) {
                    requestTimeoutMillis = 5_000 // 15 seconds
                    connectTimeoutMillis = 5_000 // 15 seconds
                    socketTimeoutMillis = 5_000 // 15 seconds
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

object StorageModule {
    val dbModule = module {
        single<Database> {
            Database(get<DatabaseDriverFactory>().create())
        }
    }

    val daoModule = module {
        single<ArticleDao> { ArticleDao(get<Database>(), get()) }
    }

    val repositoryModule = module {
        single { FavoritesRepository(get<ArticleDao>())}
    }
}