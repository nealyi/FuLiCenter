package com.nealyi.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.CartBean;
import com.nealyi.app.bean.User;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.ConvertUtils;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.ResultUtils;
import com.nealyi.app.view.DisplayUtils;

import java.util.ArrayList;

public class OrderActivity extends BaseActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();

    @BindView(R.id.et_receiver)
    EditText mEtReceiver;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.spin_city)
    Spinner mSpinCity;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;

    String cartIds = "";
    User user = null;
    OrderActivity mContext;
    ArrayList<CartBean> mList = null;
    String[] ids = new String[]{};
    int rankPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getString(R.string.confirm_order));
    }

    @Override
    protected void initData() {
        cartIds = getIntent().getStringExtra(I.Cart.ID);
        L.e(TAG, "cardIds=" + cartIds);
        user = FuLiCenterApplication.getUser();
        if (cartIds == null || cartIds.equals("") || user == null) {
            finish();
        }
        ids = cartIds.split(",");
        getOrderList();
    }

    private void getOrderList() {
        NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CartBean> list = ConvertUtils.array2List(result);
                    if (list != null && list.size() > 0) {
                        mList.addAll(list);
                        L.e(TAG, "list=" + list);
                        sumPrice();
                    } else {
                        finish();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void sumPrice() {
        rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                L.e(TAG, "c.id=" + c.getId());
                for (String id : ids) {
                    L.e(TAG, "id=" + id);
                    if (id.equals(String.valueOf(c.getId()))) {
                        rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        mTvCartSumPrice.setText("合计：¥" + Double.valueOf(rankPrice));
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_cleaning)
    public void onClick() {
        String receiverName = mEtReceiver.getText().toString();
        if (TextUtils.isEmpty(receiverName)) {
            mEtReceiver.setError("收貨人姓名不能為空");
            mEtReceiver.requestFocus();
            return;
        }
        String number = mEtPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            mEtPhoneNumber.setError("手機號碼不能為空");
            mEtPhoneNumber.requestFocus();
            return;
        }
        if (!number.matches("[\\d]{11}")) {
            mEtPhoneNumber.setError("手機號碼格式錯誤");
            mEtPhoneNumber.requestFocus();
            return;
        }
        String area = mSpinCity.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)) {
            Toast.makeText(OrderActivity.this, "收貨地區不能為空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = mEtAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            mEtAddress.setError("街道地址不能為空");
            mEtAddress.requestFocus();
            return;
        }
        gotoStatements();
    }

    private void gotoStatements() {
        L.e(TAG, "rankPrice=" + rankPrice);
    }
}
