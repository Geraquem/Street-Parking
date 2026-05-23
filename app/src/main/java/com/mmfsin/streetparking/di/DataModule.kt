package com.mmfsin.streetparking.di

import com.mmfsin.streetparking.data.repositories.ConfigRepository
import com.mmfsin.streetparking.data.repositories.SpotsRepository
import com.mmfsin.streetparking.domain.interfaces.IConfigRepository
import com.mmfsin.streetparking.domain.interfaces.ISpotsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindDataRepository(repository: SpotsRepository): ISpotsRepository

    @Binds
    fun bindConfigRepository(repository: ConfigRepository): IConfigRepository
}