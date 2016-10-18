package com.nealyi.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.activity.GoodsDetailActivity;
import com.nealyi.app.activity.MainActivity;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoGoodsDetailActivity(Context context, int pageId) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.Goods.KEY_GOODS_ID, pageId);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((MainActivity)context).overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
    }
}
