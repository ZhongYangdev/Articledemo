package com.guilong.articledemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

abstract public class BaseDialog<T extends ViewBinding> extends Dialog {

    protected Context mContext;
    protected T binding;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.style_dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*设置布局内容*/
        binding = getSubView();
        setContentView(binding.getRoot());
        /*设置宽高*/
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        setDialogSize(attributes);
        /*设置背景*/
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        attributes.dimAmount = 0.6f;
        /*初始化适配器*/
        initAdapter();
        /*更新显示内容*/
        updContent();
        /*初始化控件*/
        initView();
        /*初始化事件*/
        initEvent();
    }

    @Override
    public void show() {
        super.show();
        /*更新显示内容*/
        updContent();
        /*显示前要初始化的操作*/
        showInit();
    }

    protected abstract T getSubView();

    protected abstract void setDialogSize(WindowManager.LayoutParams attributes);

    protected void showInit() {

    }

    protected void initView() {
    }

    protected void initEvent() {
    }

    protected void updContent() {
    }

    protected void initAdapter() {
    }
}
