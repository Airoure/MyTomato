package com.zjl.mytomato.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity

class MineVm : BaseViewModel() {

    private val repo by lazy { MineRepo(viewModelScope) }

    val todoEntities = MutableLiveData<List<TodoEntity>>()
    val addedTimedTaskEntity = MutableLiveData<TimedTaskEntity>()
    val messageLiveData = MutableLiveData<Int>()

    val initTimedTaskEntities = MutableLiveData<List<TimedTaskEntity>>()
    override fun load() {
        repo.getAllTodoName(todoEntities)
        repo.getAllTimedTask(initTimedTaskEntities)
    }


    fun addTimedTask(timedTaskEntity: TimedTaskEntity) {
        repo.addTimedTask(timedTaskEntity, messageLiveData, addedTimedTaskEntity)
    }

    fun turnTimeTaskEntity(timedTaskEntity: TimedTaskEntity) {
        repo.changeTimedTaskEnable(timedTaskEntity)
    }

    fun deleteTimeTaskEntity(timedTaskEntity: TimedTaskEntity) {
        repo.deleteTimeTaskEntity(timedTaskEntity)
    }
}