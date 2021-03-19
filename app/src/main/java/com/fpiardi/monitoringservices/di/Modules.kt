package com.fpiardi.monitoringservices.di

import androidx.room.Room
import com.fpiardi.monitoringservices.data.MonitoringDatabase
import com.fpiardi.monitoringservices.viewmodel.DetailsErrorViewModel
import com.fpiardi.monitoringservices.viewmodel.SourceDetailErrorViewModel
import com.fpiardi.monitoringservices.viewmodel.SourceErrorsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    single { Room.databaseBuilder(
        get(),
        MonitoringDatabase::class.java,
        MonitoringDatabase.DATABASE_NAME)
        .build()}

    single { get<MonitoringDatabase>().sourceErrorsDao() }

    viewModel { SourceErrorsViewModel() }
    viewModel { DetailsErrorViewModel() }
    viewModel { SourceDetailErrorViewModel(get()) }

}
