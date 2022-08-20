package com.keygenqt.core.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [NullableNotBlankValidator::class])
annotation class NotNullNotBlank(
    val message: String = "Must not be null and not blank",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class NullableNotBlankValidator : ConstraintValidator<NotNullNotBlank, Any?> {
    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {
        return value != null && (value as? String)?.isNotBlank() ?: true
    }
}