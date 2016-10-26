package com.nealyi.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.Result;
import com.nealyi.app.bean.User;
import com.nealyi.app.dao.SharedPreferencesUtils;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.*;
import com.nealyi.app.view.DisplayUtils;

import java.io.File;

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
    OnSetAvatarListener mOnSetAvatarListener;


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
        if (user == null) {
            finish();
            return;
        }
        showInfo();
    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.rl_personal_information_avatar, R.id.rl_personal_information_name, R.id.rl_personal_information_nick, R.id.logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_personal_information_avatar:
                mOnSetAvatarListener = new OnSetAvatarListener(mContext, R.id.layout_upload_avatar,
                        user.getMuserName(), I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.rl_personal_information_name:
                CommonUtils.showLongToast(R.string.username_cannot_be_modify);
                break;
            case R.id.rl_personal_information_nick:
                MFGT.gotoUpdateNick(mContext);
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

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (data == null) {
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode, data, mIvPersonalInformationAvatar);
        if (requestCode == I.REQUEST_CODE_NICK) {
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            updateAvatar();
        }
    }

    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext, user.getMavatarPath() + "/" +
                user.getMuserName() + I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(mContext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s=" + s);
                Result result = ResultUtils.getResultFromJson(s, User.class);
                L.e("result=" + result);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                } else {
                    User u = (User) result.getRetData();
                    if (result.isRetMsg()) {
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u), mContext, mIvPersonalInformationAvatar);
                        CommonUtils.showLongToast(R.string.update_user_avatar_success);
                    } else {
                        CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                L.e("error=" + error);
            }
        });
    }

    private void showInfo() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvPersonalInformationAvatar);
            mTvPersonalInformationName.setText(user.getMuserName());
            mTvPersonalInformationNick.setText(user.getMuserNick());
        }
    }
}
