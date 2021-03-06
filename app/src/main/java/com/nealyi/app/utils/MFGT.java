package com.nealyi.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.activity.*;
import com.nealyi.app.bean.BoutiqueBean;
import com.nealyi.app.bean.CategoryChildBean;

import java.util.ArrayList;


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
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean) {
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID, bean);
        startActivity(context, intent);
    }

    public static void gotoCategoryChildActivity(Context context, CategoryChildBean bean, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID, bean);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context, intent);
    }

    public static void gotoLogin(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN);
    }

    public static void gotoRegisterActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
    }

    private static void startActivityForResult(Activity context, Intent intent, int requestCode) {
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoSetting(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserProfileActivity.class);
        startActivity(context, intent);
    }


    public static void gotoUpdateNick(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, UpdateNickActivity.class);
        startActivityForResult(context, intent, I.REQUEST_CODE_NICK);
    }

    public static void gotoCollects(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, CollectsActivity.class);
        startActivity(context, intent);
    }

    public static void gotoLoginFromCart(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN_FROM_CART);
    }

    public static void gotoBuy(Activity context, String cardIds) {
        Intent intent = new Intent(context, OrderActivity.class).putExtra(I.Cart.ID, cardIds);
        startActivity(context, intent);
    }
}
