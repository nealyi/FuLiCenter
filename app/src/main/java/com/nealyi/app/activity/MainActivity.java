package com.nealyi.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.R;
import com.nealyi.app.fragment.BoutiqueFragment;
import com.nealyi.app.fragment.NewGoodsFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    @BindView(R.id.new_good)
    RadioButton mLayoutNewGood;
    @BindView(R.id.boutique)
    RadioButton mLayoutBoutique;
    @BindView(R.id.category)
    RadioButton mLayoutCategory;
    @BindView(R.id.cart)
    RadioButton mLayoutCart;
    @BindView(R.id.personal_center)
    RadioButton mLayoutPersonalCenter;

    int index;
    int currentIndex;
    RadioButton[] rbs;
    Fragment[] mFragment;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    private void initFragment() {
        mFragment = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueFragment)
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = mLayoutNewGood;
        rbs[1] = mLayoutBoutique;
        rbs[2] = mLayoutCategory;
        rbs[3] = mLayoutCart;
        rbs[4] = mLayoutPersonalCenter;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.cart:
                index = 3;
                break;
            case R.id.personal_center:
                index = 4;
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(mFragment[currentIndex]);
//            if (!mFragment[index].isAdded()) {
//                transaction.add(R.id.fragment_container, mFragment[index]);
//            }
            transaction.show(mFragment[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
