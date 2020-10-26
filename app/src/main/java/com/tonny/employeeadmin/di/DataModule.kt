package com.tonny.employeeadmin.di

import android.content.Context
import com.tonny.employeeadmin.data.AdminDatabase
import com.tonny.employeeadmin.data.EmployeeRepo
import com.tonny.employeeadmin.data.EmployeeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AdminDatabase =
        AdminDatabase.getInstance(context)

    @Provides
    @Singleton
    fun getEmployeeRepo(adminDb: AdminDatabase): EmployeeRepo = EmployeeRepoImpl(adminDb)
}