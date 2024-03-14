package com.example.data

import com.example.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class MessageDataSourceImpl(
    db: CoroutineDatabase
) : MessageDataSource {

    private val messageCollection = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messageCollection.find().descendingSort(Message::timestamp).toList()
    }

    override suspend fun insertMessage(message: Message) {
        messageCollection.insertOne(message)
    }
}