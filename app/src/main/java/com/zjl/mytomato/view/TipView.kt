package com.zjl.mytomato.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zjl.mytomato.databinding.ViewTipBinding

class TipView(
    context: Context,
    content: String,
    cancelClick: (TipView)->Unit
) : FrameLayout(context) {
    private val ui:ViewTipBinding
    init {
        ui = ViewTipBinding.inflate(LayoutInflater.from(context),this,true).apply {
            tvContent.text = content
        }
        ui.tvCancel.setOnClickListener {
            cancelClick.invoke(this)
        }

    }

    fun setOnConfirmClickListener(l: OnClickListener?) {
        ui.tvConfirm.setOnClickListener {
            l?.onClick(ui.root)
        }
    }
}