package com.fpiardi.monitoringservices.di

import com.fpiardi.monitoringservices.viewmodel.DetailsErrorViewModel
import com.fpiardi.monitoringservices.viewmodel.SourceErrorsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { SourceErrorsViewModel() }
    viewModel { DetailsErrorViewModel() }
}
