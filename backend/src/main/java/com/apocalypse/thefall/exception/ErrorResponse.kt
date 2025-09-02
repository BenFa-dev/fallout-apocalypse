package com.apocalypse.thefall.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val message: String,
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    constructor(status: HttpStatus, message: String) : this(
        status = status.value(),
        message = message
    )
}
