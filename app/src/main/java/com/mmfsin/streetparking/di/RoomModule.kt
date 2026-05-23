package com.mmfsin.streetparking.di

import android.content.Context
import androidx.room.Room
import com.mmfsin.streetparking.data.bbdd.RoomConfiguration
import com.mmfsin.streetparking.data.bbdd.daos.SpotsDAO
import com.mmfsin.streetparking.presentation.utils.DDBB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomConfiguration = Room.databaseBuilder(
        context,
        RoomConfiguration::class.java,
        DDBB_NAME
    )
        .fallbackToDestructiveMigration(true)
        .build()

    @Provides
    fun provideProductsDAO(db: RoomConfiguration): SpotsDAO = db.productsDAO()
}