package com.nealyi.app.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nealyi.app.R;

/**
 * Created by nealyi on 16/10/19.
 */
public class FootViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_footer_textView)
    public TextView itemFooterTextView;

    public FootViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
