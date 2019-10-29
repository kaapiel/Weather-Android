package br.com.cielo.weather.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import org.androidannotations.annotations.EActivity;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherActivity;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.service.HttpHelper;
import br.com.cielo.weather.ui.home.HomeActivity;
import br.com.cielo.weather.ui.widget.BannerError;
import br.com.cielo.weather.util.Constants;
import br.com.cielo.weather.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gabriel Aguido Fraga on 03/09/15.
 */
@EActivity(R.layout.fragment_login)
public class LoginActivity extends WeatherActivity {

    @BindView(R.id.txt_user)
    TextView loginUser;

    @BindView(R.id.login_banner_error)
    BannerError loginBannerError;

    @BindView(R.id.progress_loading)
    ProgressBar loadingContent;

    @BindView(R.id.btn_login)
    TextView textBtn;

    SharedPreferences preferences;
    Exception exception;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        ButterKnife.bind(this);
        fullScreenCall();

        preferences = getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE);
        textChangedListeners();
        animateLogin();
        fullScreenCall();
    }

    @Override
    public void fullScreenCall() {
        if(Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void animateLogin() {
        YoYo.with(Techniques.SlideInUp)
                .repeat(0)
                .duration(700)
                .playOn(findViewById(R.id.box_login));
    }

    private class LoginRequest extends AsyncTask<String, String, String> {

        User ju;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textBtn.setVisibility(View.GONE);
            loadingContent.setVisibility(View.VISIBLE);
            Util.hideKeyboard(getCurrentFocus(), getBaseContext());
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                ju = new HttpHelper(LoginActivity.this).getJiraUser(loginUser.getText().toString());
                //String tableauAuth = new HttpHelper(LoginActivity.this).tableauAuth(loginUser.getText().toString(), loginSenha.getText().toString());
                //String csvViewData = new HttpHelper(LoginActivity.this).getViewData(tableauAuth);

            } catch (Exception e){
                exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (ju != null) {

                if(ju.getDisplayName().equalsIgnoreCase("former user")){
                    throwLoginErrorMessage(getBaseContext().getString(R.string.login_wrong_email));
                    return;
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.SharedPreferences.JIRA_USER, new Gson().toJson(ju));
                editor.apply();

                goToHome();
            } else {
                try{
                    throwLoginErrorMessage(exception.getMessage());
                } catch (Exception e){
                    throwLoginErrorMessage(getResources().getString(R.string.login_wrong_user));
                }
            }
            loadingContent.setVisibility(View.GONE);
            textBtn.setVisibility(View.VISIBLE);
        }

        private void goToHome() {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @OnClick(R.id.relative_btn_login)
    public void onLoginEntrarClick() {
        if (loginUser.getText().toString().equals("")) {
            throwLoginErrorMessage(getResources().getString(R.string.login_empty_credentials));
        } else if(!loginUser.getText().toString().contains("@")){
            throwLoginErrorMessage(getResources().getString(R.string.login_wrong_user));
            loginUser.setText("");
        } else if(!loginUser.getText().toString().endsWith("@cielo.com.br")){
            throwLoginErrorMessage(getResources().getString(R.string.login_wrong_user));
            loginUser.setText(loginUser.getText().toString().split("@")[0]);
        } else {
            new LoginRequest().execute();
        }
    }

    private void throwLoginErrorMessage(String message) {

        textBtn.setVisibility(View.GONE);
        loadingContent.setVisibility(View.VISIBLE);
        loginBannerError.setBannerText(message);
        loginBannerError.setVisibility(View.VISIBLE);
        loginBannerError.showAndAnimBannerError();
        loadingContent.setVisibility(View.GONE);
        textBtn.setVisibility(View.VISIBLE);
    }

    private void textChangedListeners() {

        loadingContent.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.cielo_blue), android.graphics.PorterDuff.Mode.MULTIPLY);

        loginUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches(".*\\d.*") || s.toString().startsWith("eyv")) {
                    throwLoginErrorMessage(getResources().getString(R.string.login_prestador_error));
                    Util.hideKeyboard(getCurrentFocus(), getBaseContext());
                    loginUser.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
