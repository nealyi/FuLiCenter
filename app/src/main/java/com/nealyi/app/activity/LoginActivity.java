package com.nealyi.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.Result;
import com.nealyi.app.bean.UserBean;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.CommonUtils;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.MFGT;
import com.nealyi.app.utils.ResultUtils;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.et_login_user_name)
    EditText mEtLoginUserName;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;

    LoginActivity mContext;
    String username;
    String password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                username = mEtLoginUserName.getText().toString().trim();
                password = mEtLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
                    mEtLoginUserName.requestFocus();
                    return;
                } else if (!username.matches("^[a-zA-z][a-z0-9A-Z]{5,15}$")) {
                    CommonUtils.showShortToast(R.string.illegal_user_name);
                    mEtLoginUserName.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    CommonUtils.showShortToast(R.string.password_connot_be_empty);
                    mEtLoginPassword.requestFocus();
                    return;
                }
                Login();
                break;
            case R.id.btn_register:
                MFGT.gotoRegisterActivity(mContext);
                break;
        }
    }

    private void Login() {
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        NetDao.login(mContext, username, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                L.e(TAG, "result=" + result);
                if (result == null) {
                    CommonUtils.showShortToast(R.string.login_fail);
                } else {
                    if (result.isRetMsg()) {
                        L.e(TAG, "result.getRetData=" + result.getRetCode());
//                        ResultUtils.getResultFromJson((String) result.getRetData(), UserBean.class);
                    } else {
                        if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                            CommonUtils.showShortToast(R.string.login_fail_unknow_user);
                        } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                            CommonUtils.showShortToast(R.string.login_fail_error_password);
                        } else {
                            CommonUtils.showShortToast(R.string.login_fail);
                        }
                    }
                }

            }

            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(error);
                Log.e(TAG, "error=" + error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String username = data.getStringExtra(I.User.USER_NAME);
            mEtLoginUserName.setText(username);
        }
    }
}
