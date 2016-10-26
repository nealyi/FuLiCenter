package com.nealyi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.CollectBean;
import com.nealyi.app.bean.MessageBean;
import com.nealyi.app.bean.NewGoodsBean;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.CommonUtils;
import com.nealyi.app.utils.ImageLoader;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.MFGT;
import com.nealyi.app.view.FootViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by nealyi on 16/10/17.
 * Create GoodsAdapter Frame
 */
public class CollectsAdapter extends Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;//Add data is more or not
    int sortBy = I.SORT_BY_ADDTIME_DESC;

    public CollectsAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        notifyDataSetChanged();
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
            holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            footViewHolder.itemFooterTextView.setText(getFootString());
        } else {
            CollectsViewHolder collectsViewHolder = (CollectsViewHolder) holder;
            CollectBean goods = mList.get(position);
            //setImageView
            ImageLoader.downloadImg(mContext, collectsViewHolder.itemGoodsImageView, goods.getGoodsThumb());
            collectsViewHolder.itemGoodsGoodsName.setText(goods.getGoodsName());
            collectsViewHolder.itemGoods.setTag(goods);
        }
    }

    private int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
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

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CollectsViewHolder extends ViewHolder {
        @BindView(R.id.item_goods_imageView)
        ImageView itemGoodsImageView;
        @BindView(R.id.item_goods_goodsName)
        TextView itemGoodsGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.item_goods)
        RelativeLayout itemGoods;

        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.item_goods)
        public void onGoodsItemClick() {
            CollectBean goods = (CollectBean) itemGoods.getTag();
            MFGT.gotoGoodsDetailActivity(mContext, goods.getGoodsId());
        }

        @OnClick(R.id.iv_collect_del)
        public void deleteCollect() {
            final CollectBean goods = (CollectBean) itemGoods.getTag();
            String username = FuLiCenterApplication.getUser().getMuserName();
            NetDao.deleteCollect(mContext, username, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.remove(goods);
                        notifyDataSetChanged();
                    } else {
                        CommonUtils.showLongToast(result != null ? result.getMsg() :
                                mContext.getResources().getString(R.string.delete_collect_fail));
                    }
                }

                @Override
                public void onError(String error) {
                    L.e("error= " + error);
                    CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));
                }
            });
        }
    }

}
