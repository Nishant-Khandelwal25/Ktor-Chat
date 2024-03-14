package com.example.room

import com.example.data.MessageDataSource
import com.example.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun join(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw MemberAlreadyExistsException()
        }
        members[username] = Member(
            username = username,
            sessionId = sessionId,
            webSockets = socket
        )
    }

    suspend fun sendMessage(
        senderUsername: String,
        message: String
    ) {
        members.values.forEach { member ->
            val messageEntity = Message(
                userName = senderUsername,
                text = message,
                timestamp = System.currentTimeMillis()
            )

            messageDataSource.insertMessage(messageEntity)

            val parseMessage = Json.encodeToString(messageEntity)

            member.webSockets.send(Frame.Text(parseMessage))

        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.webSockets?.close()
        if (members.containsKey(username)) {
            members.remove(username)
        }
    }
}