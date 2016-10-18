package com.nealyi.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.nealyi.app.R;
import com.nealyi.app.utils.MFGT;

/**
 * Created by nealyi on 16/10/14.
 */
public class SplashActivity extends AppCompatActivity {
    private final long sleepTime = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long startTime = System.currentTimeMillis();
//                //加载数据时间
//                long costTime = System.currentTimeMillis() - startTime;
//                if (sleepTime - costTime > 0) {
//                    SystemClock.sleep(sleepTime - costTime);
//                }
//                MFGT.gotoMainActivity(SplashActivity.this);
//                finish();
//            }
//        }).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        }, sleepTime);
    }
}
