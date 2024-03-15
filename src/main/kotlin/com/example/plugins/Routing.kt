package com.example.plugins

import com.example.room.RoomController
import com.example.route.chatSocketRoute
import com.example.route.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing) {
        chatSocketRoute(roomController)
        getAllMessages(roomController)
    }
}
