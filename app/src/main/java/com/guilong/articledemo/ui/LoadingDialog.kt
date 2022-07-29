package com.guilong.articledemo.ui

import android.content.Context
import android.view.WindowManager
import com.guilong.articledemo.BaseDialog
import com.guilong.articledemo.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : BaseDialog<DialogLoadingBinding>(context) {

    var message: String? = null

    init {
        //设置点击外部不可关闭
        setCancelable(false)
    }

    override fun getSubView() = DialogLoadingBinding.inflate(layoutInflater)

    override fun setDialogSize(attributes: WindowManager.LayoutParams?) {
        attributes?.width = WindowManager.LayoutParams.WRAP_CONTENT
        attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun updContent() {
        super.updContent()
        binding.tvLoadingDialogMsg.text = if (message != null) {
            message
        } else {
            "正在加载中"
        }
    }
}