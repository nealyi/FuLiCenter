package com.nealyi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.BoutiqueBean;
import com.nealyi.app.utils.ImageLoader;
import com.nealyi.app.view.FootViewHolder;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/19.
 */
public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
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
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false));
                break;
            case I.TYPE_ITEM:
                holder = new BoutiqueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).itemFooterTextView.setText(getFootString());
        }
        if (holder instanceof BoutiqueViewHolder) {
            BoutiqueBean boutiqueBean = mList.get(position);
            ImageLoader.downloadImg(mContext,((BoutiqueViewHolder) holder).mIvImageurl,boutiqueBean.getImageurl());
            ((BoutiqueViewHolder) holder).mTvTitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder) holder).mTvDescription.setText(boutiqueBean.getDescription());
            ((BoutiqueViewHolder) holder).mTvName.setText(boutiqueBean.getName());
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
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends ViewHolder{
        @BindView(R.id.iv_imageurl)
        ImageView mIvImageurl;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_description)
        TextView mTvDescription;
        @BindView(R.id.tv_name)
        TextView mTvName;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
