package com.example.budgettrackerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgettrackerapp.data.entities.Income // Import the Income entity
import com.example.budgettrackerapp.repository.IncomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.Result

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    private val _incomeOperationResult = MutableStateFlow<Result<Long>?>(null)
    val incomeOperationResult: StateFlow<Result<Long>?> = _incomeOperationResult

    fun addIncome(
        amount: Double,
        description: String,
        date: Long,
        sourceId: Long,
        userId: Long
    ) {
        viewModelScope.launch {
            try {
                val income = Income( // Now Income should be resolved
                    amount = amount,
                    description = description,
                    source = "", // Provide a default or fetch the source name
                    date = Date(date), // Convert Long to Date
                    notes = null, // Provide a default or get notes if available
                    userId = userId
                )
                val newIncomeId = incomeRepository.addIncome(income)
                _incomeOperationResult.value = Result.success(newIncomeId)
            } catch (e: Exception) {
                _incomeOperationResult.value = Result.failure(e)
            }
        }
    }

}