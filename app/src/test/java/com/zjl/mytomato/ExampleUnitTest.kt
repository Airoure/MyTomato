package com.zjl.mytomato

import com.zjl.mytomato.util.CalendarUtil
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testCalendarUtil() {
//        //当日时间测试
//        println(CalendarUtil.getTodayEndTime())
//        println(CalendarUtil.getTodayStartTime())
//        //本周时间测试
//        println(CalendarUtil.getWeekStartTime())
//        println(CalendarUtil.getWeekEndTime())
//        //本月时间测试
//        println(CalendarUtil.getMonthStartTime())
//        println(CalendarUtil.getMonthEndTime())

        println(CalendarUtil.getAllThisWeekDay())
        val map = mutableMapOf<String, Int>("123" to 1, "123" to 1, "123" to 1, "123" to 1)
        println(System.currentTimeMillis().toInt())
    }
}