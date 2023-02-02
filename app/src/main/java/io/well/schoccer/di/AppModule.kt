package io.well.schoccer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.well.schoccer.repository.Repository
import io.well.schoccer.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindssRepository(
        repositoryImpl: RepositoryImpl
    ): Repository

}
