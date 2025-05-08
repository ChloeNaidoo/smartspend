package com.example.budgettrackerapp.data.dao

import androidx.room.*
import com.example.budgettrackerapp.data.entities.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert
    suspend fun insertIncome(income: Income): Long

    @Update
    suspend fun updateIncome(income: Income)

    @Query("SELECT * FROM incomes WHERE userId = :userId")
    fun getIncomesByUser(userId: Long): Flow<List<Income>>

    @Query("SELECT * FROM incomes WHERE incomeId = :incomeId")
    fun getIncomeById(incomeId: Long): Flow<Income?>

    @Delete
    suspend fun deleteIncome(income: Income): Int
}