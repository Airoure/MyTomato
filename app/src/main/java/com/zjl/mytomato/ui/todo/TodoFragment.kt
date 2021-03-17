package com.zjl.mytomato.ui.todo

import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.adapter.TodoRvAdapter
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.view.CommonDialog
import com.zjl.mytomato.view.SetTodoDialog
import com.zjl.mytomato.view.SpacingDecoration

class TodoFragment : BaseFragment<FragmentTodoBinding, TodoVm>() {

    private lateinit var adapter: TodoRvAdapter
    override fun initUi(): FragmentTodoBinding {
        adapter = TodoRvAdapter(object :TodoRvAdapter.OnAdapterClickListener{
            override fun onDeleteClick(todoEntity: TodoEntity) {
                vm.deleteTodo(todoEntity)
            }

            override fun onSaveClick(todoEntity: TodoEntity) {
                vm.saveTodo(todoEntity)
            }
        })
        return FragmentTodoBinding.inflate(layoutInflater).apply {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_add -> {
                        SetTodoDialog(context!!, object : SetTodoDialog.ButtonClickListener {
                            override fun onConfirmClick(todoEntity: TodoEntity) {
                                vm.addTodo(todoEntity)
                            }

                            override fun onCancelClick() {

                            }

                        }).show()
                    }
                }
                true
            }
            rvTodo.layoutManager = LinearLayoutManager(context)
            rvTodo.addItemDecoration(SpacingDecoration(10f, 20f, includeVEdge = true))
            rvTodo.adapter = adapter
        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }

    override fun init() {
        vm.messageLiveData.observe(this, Observer {
            when (it) {
                Constant.ADD_TODO_SUCCESS -> {
                    Toast.makeText(context, "待办添加成功", Toast.LENGTH_SHORT).show()
                }
                Constant.ADD_TODO_FAIL , Constant.UPDATE_TODO_FAIL -> {
                    CommonDialog(context!!, content = "已经存在同名待办啦！").show()
                }
            }
        })

        vm.firstLoadLiveData.observe(this, Observer {
            adapter.setTodoEntityList(it)
        })

        vm.todoLiveData.observe(this, Observer {
            adapter.addTodoEntity(it)
        })
        vm.removeLiveData.observe(this, Observer {
            adapter.remoeveItem(it)
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
        })
    }


}