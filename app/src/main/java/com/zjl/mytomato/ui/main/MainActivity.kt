package com.zjl.mytomato.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.ActivityMainBinding
import com.zjl.mytomato.ui.mine.MineFragment
import com.zjl.mytomato.ui.statistics.StatisticFragment
import com.zjl.mytomato.ui.todo.TodoFragment
import com.zjl.mytomato.ui.todolist.TodoListFragment
import com.zjl.mytomato.view.CommonDialog

class MainActivity : AppCompatActivity() {

    private lateinit var ui:ActivityMainBinding
    private lateinit var fragmentList:List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        initUi()
    }

    private fun initUi() {
        fragmentList = listOf(TodoFragment(),TodoListFragment(),StatisticFragment(),MineFragment())
        ui.apply {
            vpMain.apply {
                adapter = object : FragmentStateAdapter(this@MainActivity) {
                    override fun getItemCount() = fragmentList.size

                    override fun createFragment(position: Int) = fragmentList[position]

                }
                isUserInputEnabled = false
                offscreenPageLimit = 3
            }
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_todo -> vpMain.setCurrentItem(0,false)
                    R.id.menu_todo_list -> vpMain.setCurrentItem(1,false)
                    R.id.menu_statistics -> vpMain.setCurrentItem(2,false)
                    R.id.menu_mine -> vpMain.setCurrentItem(3,false)
                }
                true
            }
        }
    }

    companion object{
        fun open(context: Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}