package com.nealyi.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.activity.MainActivity;
import com.nealyi.app.adapter.CartAdapter;
import com.nealyi.app.bean.CartBean;
import com.nealyi.app.bean.User;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.CommonUtils;
import com.nealyi.app.utils.ConvertUtils;
import com.nealyi.app.utils.L;
import com.nealyi.app.view.SpaceItemDecoration;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/19.
 */
public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    MainActivity mContext;
    ArrayList<CartBean> mList;
    CartAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    updateCartReceiver mReceiver;

    @BindView(R.id.tv_cart_empty)
    TextView mTvCartEmpty;
    @BindView(R.id.btn_cleaning)
    Button mBtnCleaning;
    @BindView(R.id.tv_cart_sum_price)
    TextView mTvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView mTvCartSavePrice;
    @BindView(R.id.rl_price_count)
    RelativeLayout mRlPriceCount;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mList);
        super.onCreateView(inflater, container, null);
        return view;
    }

    @Override
    protected void setListener() {
        setPullDownListener();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATE_CART);
        mReceiver = new updateCartReceiver();
        mContext.registerReceiver(mReceiver, filter);
    }


    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setEnabled(true);
                mSwipeRefreshLayout.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    @Override
    protected void initData() {
        downloadCart();
    }

    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e(TAG, "result=" + result.toString());
                    mSwipeRefreshLayout.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    L.e("result" + result.toString());
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        if (list != null && list.size() > 0) {
                            mList.clear();
                            mList.addAll(list);
                            L.e(TAG, "list=" + list);
                            mAdapter.initData(list);
                            setCartLayout(true);
                        } else {
                            setCartLayout(false);
                        }
                    } else {
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    setCartLayout(false);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                    CommonUtils.showShortToast(error);
                    L.e("error:" + error);
                }
            });
        }
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(12));
        setCartLayout(false);
    }

    private void setCartLayout(boolean hasCart) {
        mLinearLayout.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        mTvCartEmpty.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        mRlPriceCount.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        sumPrice();
    }

    @OnClick(R.id.btn_cleaning)
    public void onClean() {

    }

    private void sumPrice() {
        int sumPrice = 0;
        int rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                if (c.isChecked()) {
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            mTvCartSumPrice.setText("合计：¥" + Double.valueOf(rankPrice));
            mTvCartSavePrice.setText("合计：¥" + Double.valueOf(sumPrice - rankPrice));
        } else {
            mTvCartSumPrice.setText("合计：¥0.00");
            mTvCartSavePrice.setText("合计：¥0.00");
        }
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    class updateCartReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            L.e(TAG, "updateCartReceiver……");
            sumPrice();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            mContext.unregisterReceiver(mReceiver);
        }
    }
}
