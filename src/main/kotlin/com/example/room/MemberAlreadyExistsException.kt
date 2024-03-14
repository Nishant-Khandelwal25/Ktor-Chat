package com.example.room

class MemberAlreadyExistsException : Exception(
    "Username already taken, please try different username"
)