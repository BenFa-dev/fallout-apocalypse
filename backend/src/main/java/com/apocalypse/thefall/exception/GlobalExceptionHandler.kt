package com.apocalypse.thefall.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(GameException::class)
    fun handleGameException(ex: GameException): ErrorResponse {
        val message = messageSource.getMessage(
            ex.messageKey,
            ex.args,
            LocaleContextHolder.getLocale()
        )
        return ErrorResponse(ex.status, message)
    }

    @ExceptionHandler(ConstraintViolationException::class, IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: Exception): ErrorResponse {
        val message = messageSource.getMessage(
            "error.validation",
            arrayOf(ex.message ?: ""),
            LocaleContextHolder.getLocale()
        )
        return ErrorResponse(HttpStatus.BAD_REQUEST, message)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGlobalException(ex: Exception): ErrorResponse {
        val message = messageSource.getMessage(
            "error.unexpected",
            arrayOf(ex.message ?: ""),
            LocaleContextHolder.getLocale()
        )
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}
