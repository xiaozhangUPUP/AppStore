package com.appstore.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appstore.android.R;
import com.appstore.android.bean.LoginBean;
import com.appstore.android.di.component.AppComponent;
import com.appstore.android.di.component.DaggerLoginComponent;
import com.appstore.android.di.module.LoginModule;
import com.appstore.android.presenter.LoginPresenter;
import com.appstore.android.presenter.contract.LoginContract;
import com.appstore.android.ui.adapter.AppInfoAdapter;
import com.appstore.android.ui.widget.LoadingButton;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapter;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.txt_mobi)
    EditText txtMobi;
    @BindView(R.id.view_mobi_wrapper)
    TextInputLayout viewMobiWrapper;
    @BindView(R.id.txt_password)
    EditText txtPassword;
    @BindView(R.id.view_password_wrapper)
    TextInputLayout viewPasswordWrapper;
    @BindView(R.id.btn_login)
    LoadingButton btnLogin;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void init() {
        initView();
    }

    private void initView() {
        Observable<CharSequence> obMobi = RxTextView.textChanges(txtMobi);
        Observable<CharSequence> obPwd = RxTextView.textChanges(txtPassword);
        Observable.combineLatest(obMobi, obPwd, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence mobi, CharSequence pwd) {
                return isPhoneValid(mobi.toString()) && isPasswordValid(pwd.toString());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(btnLogin).call(aBoolean);
            }
        });

        RxView.clicks(btnLogin).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.login(txtMobi.getText().toString().trim(), txtPassword.getText().toString().trim());
            }
        });

    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    public void checkPhoneError() {
        viewMobiWrapper.setError("手机号格式不正确");
    }

    @Override
    public void checkPhoneSuccess() {
        viewMobiWrapper.setError("");
        viewMobiWrapper.setErrorEnabled(false);
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        finish();
    }

    @Override
    public void showLoading() {
        btnLogin.showLoading();
    }

    @Override
    public void dismissLoading() {
        btnLogin.showButtonText();
    }

    @Override
    public void showError(String msg) {
        btnLogin.showButtonText();
    }
}
