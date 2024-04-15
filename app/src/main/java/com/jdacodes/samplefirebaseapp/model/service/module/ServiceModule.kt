package com.jdacodes.samplefirebaseapp.model.service.module

import com.jdacodes.samplefirebaseapp.model.service.AccountService
import com.jdacodes.samplefirebaseapp.model.service.ConfigurationService
import com.jdacodes.samplefirebaseapp.model.service.LogService
import com.jdacodes.samplefirebaseapp.model.service.StorageService
import com.jdacodes.samplefirebaseapp.model.service.impl.AccountServiceImpl
import com.jdacodes.samplefirebaseapp.model.service.impl.ConfigurationServiceImpl
import com.jdacodes.samplefirebaseapp.model.service.impl.LogServiceImpl
import com.jdacodes.samplefirebaseapp.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}