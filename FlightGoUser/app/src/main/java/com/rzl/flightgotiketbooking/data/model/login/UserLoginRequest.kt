package com.rzl.flightgotiketbooking.model.register

data class UserLoginRequest(
val email :String,
val password : String
)

data class LoginGoogle (
    val credential : String
)
