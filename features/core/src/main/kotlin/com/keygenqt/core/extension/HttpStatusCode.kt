package com.keygenqt.core.extension

import com.keygenqt.core.exceptions.IDataException
import com.keygenqt.core.exceptions.ResponseException
import com.keygenqt.core.exceptions.ValidateException
import com.keygenqt.core.exceptions.ValidateViolation
import io.ktor.http.*
import jakarta.validation.ConstraintViolation

/**
 * Create exception
 */
fun HttpStatusCode.genException(
    message: String? = null,
    violation: Set<ConstraintViolation<*>>? = null,
): IDataException {
    return if (violation !== null) {
        ValidateException(
            code = value,
            message = message ?: description,
            validate = mutableListOf<ValidateViolation>().apply {
                violation.forEach {

                    val field = it.propertyPath.toString()
                    val error = it.message

                    val model = this
                        .find { item -> item.filed == field }
                        ?.let { er -> er.copy(errors = er.errors + listOf(error)) }
                        ?: ValidateViolation(filed = field, errors = listOf(error))

                    this.removeIf { er -> er.filed == field }
                    this.add(model)
                }
            }
        )
    } else {
        ResponseException(
            code = value,
            message = message ?: description
        )
    }

}