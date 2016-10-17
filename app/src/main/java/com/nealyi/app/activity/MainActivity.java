package com.nealyi.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
    RadioButton[] rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
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
        setRadioButtonStatus();
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


}
