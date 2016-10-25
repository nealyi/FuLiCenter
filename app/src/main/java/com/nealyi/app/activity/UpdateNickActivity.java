package com.nealyi.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.FuLiCenterApplication;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.Result;
import com.nealyi.app.bean.User;
import com.nealyi.app.dao.UserDao;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.CommonUtils;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.MFGT;
import com.nealyi.app.utils.ResultUtils;
import com.nealyi.app.view.DisplayUtils;

public class UpdateNickActivity extends BaseActivity {
    private static final String TAG = UpdateNickActivity.class.getSimpleName();

    @BindView(R.id.et_new_user_ncik)
    EditText mEtNewUserNcik;

    User user;
    UpdateNickActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_update_nick);
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
            mEtNewUserNcik.setText(user.getMuserNick());
            mEtNewUserNcik.setSelectAllOnFocus(true);
        } else {
            finish();
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_user_nick_save)
    public void onClick() {
        if (user != null) {
            String nick = mEtNewUserNcik.getText().toString();
            if (TextUtils.isEmpty(nick)) {
                CommonUtils.showLongToast(R.string.nick_name_cannot_be_empty);
            } else if (nick.equals(user.getMuserNick())) {
                CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
            } else {
                updateNick(nick);
            }
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        NetDao.updateNick(mContext, user.getMuserName(), nick, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                L.e(TAG, "result=" + result);
                if (result == null) {
                    CommonUtils.showLongToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        User u = (User) result.getRetData();
                        L.e(TAG, "user=" + u);
                        UserDao dao = new UserDao(mContext);
                        boolean isSuccess = dao.updateUser(u);
                        if (isSuccess) {
                            FuLiCenterApplication.setUser(u);
                            setResult(RESULT_OK);
                            MFGT.finish(mContext);
                        } else {
                            CommonUtils.showLongToast(R.string.user_database_error);
                        }
                    } else {
                        if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                        } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                            CommonUtils.showLongToast(R.string.update_fail);
                        } else {
                            CommonUtils.showLongToast(R.string.update_fail);
                        }

                    }
                }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG, "error=" + error);
            }
        });
    }
}
