package com.example.chat.model

data class User(val senderId: String = "SenderID", val receiverId: String = "ReceiverId", var receiverName: String="Receiver Name", val senderName: String = "Sender Name", val txtMessage: String = "", val userImage: String = "")