package com.keygenqt.features.ps.di

import com.keygenqt.features.ps.service.ArticlesService
import com.keygenqt.features.ps.service.ProjectsService
import org.koin.dsl.module

val moduleServices = module {
    single { ArticlesService() }
    single { ProjectsService() }
}