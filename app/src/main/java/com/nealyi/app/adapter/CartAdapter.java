package com.nealyi.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.CartBean;
import com.nealyi.app.bean.GoodsDetailsBean;
import com.nealyi.app.bean.MessageBean;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.ImageLoader;
import com.nealyi.app.utils.MFGT;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/19.
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.mIvGoodsThumb, goods.getGoodsThumb());
            holder.mTvCartGoodName.setText(goods.getGoodsName());
            holder.mTvCartGoodPrice.setText(goods.getCurrencyPrice());
        }
        holder.mTvCartGoodCount.setText(String.valueOf(cartBean.getCount()));
        holder.mIvCheckbox.setChecked(cartBean.isChecked());
        holder.mIvCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartBean.setChecked(isChecked);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
            }
        });
        holder.mIvAddCart.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }


    public void initData(ArrayList<CartBean> list) {
        mList = list;
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

        @OnClick(R.id.iv_add_cart)
        public void onAddCart() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cartBean = mList.get(position);

            NetDao.updateCart(mContext, cartBean.getId(), cartBean.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.get(position).setCount(mList.get(position).getCount() + 1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                        mTvCartGoodCount.setText(String.valueOf(mList.get(position).getCount()));
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }

        @OnClick(R.id.iv_del_cart)
        public void onDelCart() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cartBean = mList.get(position);

            if (cartBean.getCount() > 1) {
                NetDao.updateCart(mContext, cartBean.getId(), cartBean.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.get(position).setCount(mList.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            mTvCartGoodCount.setText(String.valueOf(mList.get(position).getCount()));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            } else {
                NetDao.deleteCart(mContext, cartBean.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.remove(position);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }

        @OnClick({R.id.iv_goods_thumb,R.id.tv_cart_good_name,R.id.tv_cart_good_price})
        public void onDetail() {
            final int position = (int) mIvAddCart.getTag();
            CartBean cartBean = mList.get(position);
            MFGT.gotoGoodsDetailActivity(mContext,cartBean.getGoodsId());
        }
    }
}
