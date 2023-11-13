package com.example.kmptest.di

import com.example.kmptest.getPlatform
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::getPlatform)
}