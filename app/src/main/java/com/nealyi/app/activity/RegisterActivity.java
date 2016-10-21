package com.nealyi.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nealyi.app.I;
import com.nealyi.app.R;
import com.nealyi.app.bean.Result;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.CommonUtils;
import com.nealyi.app.utils.L;
import com.nealyi.app.utils.MFGT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_user_name)
    EditText mEtRegisterUserName;
    @BindView(R.id.et_register_nickname)
    EditText mEtRegisterNickname;
    @BindView(R.id.et_register_password)
    EditText mEtRegisterPassword;
    @BindView(R.id.et_register_confirmpassword)
    EditText mEtRegisterConfirmpassword;

    String username;
    String nickname;
    String password;

    RegisterActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    @OnClick(R.id.btn_register)
    public void onClick() {
        username = mEtRegisterUserName.getText().toString().trim();
        nickname = mEtRegisterNickname.getText().toString().trim();
        password = mEtRegisterPassword.getText().toString().trim();
        String confirmpassword = mEtRegisterConfirmpassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            mEtRegisterUserName.requestFocus();
            return;
        } else if (!username.matches("^[a-zA-z][a-z0-9A-Z]{5,15}$")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            mEtRegisterUserName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(nickname)) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            mEtRegisterNickname.requestFocus();
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            mEtRegisterPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirmpassword)) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            mEtRegisterConfirmpassword.requestFocus();
            return;
        } else if (!password.equals(confirmpassword)) {
            CommonUtils.showShortToast(R.string.two_input_password);
            mEtRegisterPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null) {
                    CommonUtils.showShortToast(R.string.register_fail);
                } else {
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                    } else {
                        CommonUtils.showShortToast(R.string.register_fail_exists);
                        mEtRegisterUserName.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e("error=" + error);
            }
        });
    }


}
