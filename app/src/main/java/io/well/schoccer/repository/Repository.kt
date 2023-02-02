package io.well.schoccer.repository

import io.well.schoccer.domain.Schedule
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getSchedules(): Flow<List<Schedule>>
}