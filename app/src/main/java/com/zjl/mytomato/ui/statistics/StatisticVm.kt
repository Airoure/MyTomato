package com.zjl.mytomato.ui.statistics

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.ui.todolist.TodoListRepo
import com.zjl.mytomato.util.AppUsedUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticVm : BaseViewModel() {
    private val repo by lazy { TodoListRepo(viewModelScope) }
    val finishTodoNum = MutableLiveData<Int>()
    val totalTime = MutableLiveData<Int>()
    val averageTime = MutableLiveData<Int>()
    val dayNum = MutableLiveData<Int>()
    val dayTime = MutableLiveData<Int>()
    val pieChartDate = MutableLiveData<Map<String, Int>>()
    val barChartData = MutableLiveData<Map<String, Long>>()

    fun getFinishTodoNum() {
        repo.getFinishTodoNum(finishTodoNum)
    }

    fun getTotalTime() {
        repo.getTotalTime(totalTime)
    }

    fun getTotalAverageTime() {
        repo.getTotalAverageTime(averageTime)
    }

    fun getNumByDate(date: String) {
        repo.getNumByDate(date, dayNum)
    }

    fun getTimeByDate(date: String) {
        repo.getTimeByDate(date, dayTime)
    }

    fun getPieChartData(date: String) {
        repo.getPieChartData(date, pieChartDate)
    }

    fun getAppUsedTime(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val appMap = AppUsedUtil.getAppUsedTime(context)
                barChartData.postValue(appMap)
            }
        }

    }
}