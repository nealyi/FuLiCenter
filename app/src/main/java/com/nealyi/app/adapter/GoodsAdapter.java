package com.nealyi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.NewGoodsBean;
import com.nealyi.app.utils.ImageLoader;
import com.nealyi.app.utils.MFGT;
import com.nealyi.app.view.FootViewHolder;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/17.
 * Create GoodsAdapter Frame
 */
public class GoodsAdapter extends Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;//Add data is more or not

    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
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
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            footViewHolder.itemFooterTextView.setText(getFootString());
        } else {
            GoodsViewHolder goodsViewHolder = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            //setImageView
            ImageLoader.downloadImg(mContext, goodsViewHolder.itemGoodsImageView, goods.getGoodsThumb());
            goodsViewHolder.itemGoodsGoodsName.setText(goods.getGoodsName());
            goodsViewHolder.itemGoodsGoodsPrice.setText(goods.getCurrencyPrice());
            goodsViewHolder.itemGoods.setTag(goods.getGoodsId());
        }
    }

    private int getFootString() {
        return isMore?R.string.load_more:R.string.no_more;
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

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.item_goods_imageView)
        ImageView itemGoodsImageView;
        @BindView(R.id.item_goods_goodsName)
        TextView itemGoodsGoodsName;
        @BindView(R.id.item_goods_goodsPrice)
        TextView itemGoodsGoodsPrice;
        @BindView(R.id.item_goods)
        LinearLayout itemGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_goods)
        public void onGoodsItemClick() {
            int goodId = (int) itemGoods.getTag();
            MFGT.gotoGoodsDetailActivity(mContext, goodId);
        }
    }
}
