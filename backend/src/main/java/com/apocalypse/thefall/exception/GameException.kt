package com.apocalypse.thefall.exception

import org.springframework.http.HttpStatus

class GameException(
    val messageKey: String,
    val status: HttpStatus,
    vararg val args: String
) : RuntimeException("$messageKey ${args.joinToString(prefix = "[", postfix = "]")}")
