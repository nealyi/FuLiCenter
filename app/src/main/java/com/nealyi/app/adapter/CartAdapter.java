package com.nealyi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nealyi.app.R;
import com.nealyi.app.bean.CartBean;
import com.nealyi.app.bean.GoodsDetailsBean;
import com.nealyi.app.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/19.
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.mIvGoodsThumb, goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
            holder.mTvCartGoodPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartGoodCount.setText(String.valueOf(cartBean.getCount()));
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CartViewHolder extends ViewHolder {
        @BindView(R.id.iv_checkbox)
        CheckBox mIvCheckbox;
        @BindView(R.id.iv_goods_thumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tv_cart_good_name)
        TextView mTvCartGoodName;
        @BindView(R.id.iv_add_cart)
        ImageView mIvAddCart;
        @BindView(R.id.tv_cart_good_count)
        TextView mTvCartGoodCount;
        @BindView(R.id.iv_del_cart)
        ImageView mIvDelCart;
        @BindView(R.id.tv_cart_good_price)
        TextView mTvCartGoodPrice;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
