package com.nealyi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.NewGoodsBean;
import com.nealyi.app.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/17.
 * Create GoodsAdapter Frame
 */
public class GoodsAdapter extends Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;

    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FootViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {

        } else {
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            //setImageView
            ImageLoader.downloadImg(mContext, goodsViewHolder.itemGoodsImageView, goods.getGoodsThumb());
            goodsViewHolder.itemGoodsGoodsName.setText(goods.getGoodsName());
            goodsViewHolder.itemGoodsGoodsPrice.setText(goods.getCurrencyPrice());
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class FootViewHolder extends ViewHolder {
        @BindView(R.id.item_footer_textView)
        TextView itemFooterTextView;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class GoodsViewHolder extends ViewHolder {
        @BindView(R.id.item_goods_imageView)
        ImageView itemGoodsImageView;
        @BindView(R.id.item_goods_goodsName)
        TextView itemGoodsGoodsName;
        @BindView(R.id.item_goods_goodsPrice)
        TextView itemGoodsGoodsPrice;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
