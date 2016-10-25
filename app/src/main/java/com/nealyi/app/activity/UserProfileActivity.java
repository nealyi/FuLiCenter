package com.nealyi.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.R;
import com.nealyi.app.bean.User;
import com.nealyi.app.dao.SharedPreferencesUtils;
import com.nealyi.app.utils.ImageLoader;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.MFGT;
import com.nealyi.app.view.DisplayUtils;

/**
 * Created by nealyi on 16/10/25.
 */
public class UserProfileActivity extends BaseActivity {

    private static final String TAG = UserProfileActivity.class.getSimpleName();

    @BindView(R.id.iv_personal_information_avatar)
    ImageView mIvPersonalInformationAvatar;
    @BindView(R.id.tv_personal_information_name)
    TextView mTvPersonalInformationName;
    @BindView(R.id.tv_personal_information_nick)
    TextView mTvPersonalInformationNick;

    UserProfileActivity mContext;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.user_profile));
    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvPersonalInformationAvatar);
            mTvPersonalInformationName.setText(user.getMuserName());
            mTvPersonalInformationNick.setText(user.getMuserNick());
        } else {
            finish();
        }
    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.rl_personal_information_avatar, R.id.rl_personal_information_name, R.id.rl_personal_information_nick, R.id.logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_personal_information_avatar:
                break;
            case R.id.rl_personal_information_name:
                break;
            case R.id.rl_personal_information_nick:
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    private void logout() {
        if (user != null) {
            SharedPreferencesUtils.getInstance(mContext).removeUser();
            FuLiCenterApplication.setUser(null);
            L.e(TAG, "user=" + FuLiCenterApplication.getUser());
//            MFGT.gotoLogin(mContext);
        }
        finish();
    }
}
