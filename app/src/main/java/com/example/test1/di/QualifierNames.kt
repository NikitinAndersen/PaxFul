package com.example.test1.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named

object QualifierNames {
    const val QUALIFIER_BASE_URL = "BaseUrl"

    fun getNamed(create: QualifierNames.() -> String): Qualifier {
        return named(create(QualifierNames))
    }
}