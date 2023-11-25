package com.yongchul.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackageClasses = [ApiExceptionController::class])
class ApiExceptionController {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleConnectionNotFoundException(e: IllegalArgumentException): ErrorRes {
        return ErrorRes(400000, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorRes {
        val bindingResult: BindingResult = e.bindingResult

        val builder = StringBuilder()
        for (fieldError in bindingResult.fieldErrors) {
            builder.append("[")
            builder.append(fieldError.field)
            builder.append("](은)는 ")
            builder.append(fieldError.defaultMessage)
            builder.append(", 입력된 값: [")
            builder.append(fieldError.rejectedValue)
            builder.append("].")
        }

        return ErrorRes(4000000, builder.toString())
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [RuntimeException::class])
    fun featuredDeleteFailedExceptionHandler(e: RuntimeException): ErrorRes {
        logger.error("Internal Server Error", e)
        return ErrorRes(5000000, e.message)
    }
}
