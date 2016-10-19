package com.nealyi.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.nealyi.app.utils.MFGT;

/**
 * Created by nealyi on 16/10/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    protected abstract void setListener();

    protected abstract void initData();

    protected abstract void initView();

    @Override
    public void onBackPressed() {
        MFGT.finish(this);
    }
}
