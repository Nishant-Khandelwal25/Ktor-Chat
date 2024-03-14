package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(mainModule)
    }
    configureSockets()
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureMonitoring()
    configureRouting()
}
