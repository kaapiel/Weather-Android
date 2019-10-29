package br.com.cielo.weather.appManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.cielo.weather.R;
import br.com.cielo.weather.model.jira.Alert;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.model.mongo.ScoreHistory;
import br.com.cielo.weather.model.mongo.ScoreHistoryUnit;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.ui.dialog.DialogCustomAlert;
import br.com.cielo.weather.ui.login.LoginActivity;
import br.com.cielo.weather.ui.splash.SplashScreen;
import br.com.cielo.weather.util.Constants;

/**
 * Created by Gabriel Aguido Fraga on 02/09/15.
 */
public class WeatherActivity extends AppCompatActivity {

    public boolean isAlive() {
        return !isFinishing();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    public void fullScreenCall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    protected void showLogoutDialog(Context c) {
        Alert alert = new Alert();
        alert.setDescription(getString(R.string.dialog_logout_descrition));
        alert.setPositiveButton(getString(R.string.dialog_positive_option));
        alert.setNegativeButton(getString(R.string.dialog_negative_option));
        alert.setImage(getResources().getResourceEntryName(R.drawable.ic_warn));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_alerts, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {

                            getSharedPreferences(
                                    Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE).edit().remove(Constants.SharedPreferences.JIRA_USER).apply();

                            Intent intent = new Intent(c, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                    });
        }
    }

    protected void showFeedbackDialog(Context c, User ju) {
        Alert alert = new Alert();
        alert.setTitle(getString(R.string.dialog_feedback_descrition));
        alert.setDescription(getString(R.string.dialog_feedback_subdescrition));
        alert.setPositiveButton(getString(R.string.dialog_send_option));
        alert.setNegativeButton(getString(R.string.dialog_cancel_option));
        alert.setImage(getResources().getResourceEntryName(R.drawable.ic_happy));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_feedback_alert, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
//                            new SendFeedback().execute(
//                                    dialogCustom.edtFeedback.getText().toString(),
//                                    ju.getEmailAddress(),
//                                    c);
                            showRatingDialog(c);
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                    });
        }
    }

    protected void showPushNotificationsDialog(Context c, RemoteMessage message) {
        Alert alert = new Alert();
        alert.setRm(message);
        alert.setPositiveButton(getString(R.string.dialog_push_go_to_app));
        alert.setNegativeButton(getString(R.string.dialog_push_ignore));
        //alert.setImage(getResources().getResourceEntryName(R.drawable.ic_cloud_extract));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_feedback_alert, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Intent i = new Intent(WeatherActivity.this, SplashScreen.class);
                            startActivity(i);
                        }

                        @Override
                        public void onNegativeClick() {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }

                    });
        }
    }

    protected void showPushNotificationsDialog(Context c, String message) {
        Alert alert = new Alert();
        alert.setTitle(getResources().getString(R.string.dialog_push_warning));
        alert.setDescription(getResources().getString(R.string.dialog_push_warning_description));
        alert.setData(message);
        alert.setImage(getResources().getResourceEntryName(R.drawable.ic_cloud_extract));
        alert.setPositiveButton(getString(R.string.dialog_push_go_to_app));
        alert.setNegativeButton(getString(R.string.dialog_push_ignore));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_feedback_alert, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Intent i = new Intent(WeatherActivity.this, SplashScreen.class);
                            startActivity(i);
                        }

                        @Override
                        public void onNegativeClick() {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }

                    });
        }
    }

    protected void showRatingDialog(Context c) {
        Alert alert = new Alert();
        alert.setDescription(getString(R.string.dialog_rating_descrition));
        alert.setPositiveButton(getString(R.string.dialog_send_option));
        alert.setImage(getResources().getResourceEntryName(R.drawable.ic_right));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_rating_alert, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {

                            //consultar colaction

                            //validar se ja avaliou
                            //se ja avaliou, substituir
                            //inserir document

                            showThanksDialog(c);

                        }

                        @Override
                        public void onNegativeClick() {
                        }

                    });
        }
    }

    protected void showThanksDialog(Context c) {
        Alert alert = new Alert();
        alert.setDescription(getString(R.string.dialog_rating_descrition));
        alert.setPositiveButton(getString(R.string.dialog_send_option));
        alert.setImage(getResources().getResourceEntryName(R.drawable.ic_heart));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(c, R.layout.custom_dialog_thanks_alerts, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                    });
        }
    }

    protected void startGmailService(String body, String emailFrom) {


    }

    public class SendFeedback extends AsyncTask<Object, User, Context> {

        private Context c;

        @Override
        protected Context doInBackground(Object... objs) {
            this.c = (Context) objs[2];
            startGmailService(objs[0].toString(), objs[1].toString());
            return null;
        }

        @Override
        protected void onPostExecute(Context context) {
            super.onPostExecute(context);
            showRatingDialog(c);
        }
    }

    protected void configSharedPreferences(List<ScoreResults> appList, SharedPreferences preferences) {

        //APP - APP CIELO
        ScoreHistory appScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresApp = new ArrayList<>();

        //ACC RANGE - CENTRALIZADOR
        ScoreHistory accScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresAcc = new ArrayList<>();

        //CIELO PAY - PTO CORE
        ScoreHistory cieloPayScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresCieloPay = new ArrayList<>();

        //CRD - CREDENCIAMENTO
        ScoreHistory crdScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresCrd = new ArrayList<>();

        //FAC - F DE ATENDIMENTO AO CLIENTE
        ScoreHistory facScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresFac = new ArrayList<>();

        //FAROL - MONETIZAÇÃO DE DADOS
        ScoreHistory farolScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresFarol = new ArrayList<>();

        //PFI - PLATAFORMA DE FINANCIAMENTO I...
        ScoreHistory pfiScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresPfi = new ArrayList<>();

        //RECEBA MAIS
        ScoreHistory recebaMaisScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresRecebaMais = new ArrayList<>();

        //SIGO
        ScoreHistory sigoScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresSigo = new ArrayList<>();

        //SITE - SITE CIELO
        ScoreHistory siteScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresSite = new ArrayList<>();

        //SKY - SKYLINE
        ScoreHistory skyScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresSky = new ArrayList<>();

        //STAR -
        ScoreHistory starScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresStar = new ArrayList<>();

        //STC - SISTEMA DE CONTESTAÇÃO DE CRÉDITO
        ScoreHistory stcScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresStc = new ArrayList<>();

        //VSC
        ScoreHistory vscScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresVsc = new ArrayList<>();

        //NGC - negociacao de gestao comercial
        ScoreHistory ngcScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresNgc = new ArrayList<>();

        //POS
        ScoreHistory posScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresPos = new ArrayList<>();

        //CANCELAMENTO
        ScoreHistory canScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresCan = new ArrayList<>();

        //HYAKU
        ScoreHistory hyakuScoreHistory = new ScoreHistory();
        List<ScoreHistoryUnit> scoresHyaku = new ArrayList<>();


        for (ScoreResults sr : appList) {

            ScoreHistoryUnit shu;

            switch (sr.getApplicationName()){
                case Constants.Systems.APP_CIELO:
                    sr.setSysIcon(R.drawable.ic_smartmachine);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresApp.add(shu);
                    appScoreHistory.setSysName(Constants.Systems.APP_CIELO);
                    break;
                case Constants.Systems.CENTRALIZADOR_ACC_RANGE:
                    sr.setSysIcon(R.drawable.ic_card);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresAcc.add(shu);
                    accScoreHistory.setSysName(Constants.Systems.CENTRALIZADOR_ACC_RANGE);
                    break;
                case Constants.Systems.CIELO_PAY_PTO_CORE:
                    sr.setSysIcon(R.drawable.ic_cards);
                    sr.setSysIcon(R.drawable.ic_receive_cash);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresCieloPay.add(shu);
                    cieloPayScoreHistory.setSysName(Constants.Systems.CIELO_PAY_PTO_CORE);
                    break;
                case Constants.Systems.CREDENCIAMENTO:
                    sr.setSysIcon(R.drawable.ic_check);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresCrd.add(shu);
                    crdScoreHistory.setSysName(Constants.Systems.CREDENCIAMENTO);
                    break;
                case Constants.Systems.FAC:
                    sr.setSysIcon(R.drawable.ic_telephone);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresFac.add(shu);
                    facScoreHistory.setSysName(Constants.Systems.FAC);
                    break;
                case Constants.Systems.FAROL:
                    sr.setSysIcon(R.drawable.ic_lighthouse);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresFarol.add(shu);
                    farolScoreHistory.setSysName(Constants.Systems.FAROL);
                    break;
                case Constants.Systems.PFI:
                    sr.setSysIcon(R.drawable.ic_48x);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresPfi.add(shu);
                    pfiScoreHistory.setSysName(Constants.Systems.PFI);
                    break;
                case Constants.Systems.RECEBA_MAIS:
                    sr.setSysIcon(R.drawable.ic_cash);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresRecebaMais.add(shu);
                    recebaMaisScoreHistory.setSysName(Constants.Systems.RECEBA_MAIS);
                    break;
                case Constants.Systems.SIGO:
                    sr.setSysIcon(R.drawable.ic_delivery);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresSigo.add(shu);
                    sigoScoreHistory.setSysName(Constants.Systems.SIGO);
                    break;
                case Constants.Systems.SITE:
                    sr.setSysIcon(R.drawable.ic_monitor);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresSite.add(shu);
                    siteScoreHistory.setSysName(Constants.Systems.SITE);
                    break;
                case Constants.Systems.SKYLINE:
                    sr.setSysIcon(R.drawable.ic_cloud_extract);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresSky.add(shu);
                    skyScoreHistory.setSysName(Constants.Systems.SKYLINE);
                    break;
                case Constants.Systems.STAR:
                    sr.setSysIcon(R.drawable.ic_ecommerce);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresStar.add(shu);
                    starScoreHistory.setSysName(Constants.Systems.STAR);
                    break;
                case Constants.Systems.STC:
                    sr.setSysIcon(R.drawable.ic_no_cash);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresStc.add(shu);
                    stcScoreHistory.setSysName(Constants.Systems.STC);
                    break;
                case Constants.Systems.VSC:
                    sr.setSysIcon(R.drawable.icon_lio_v2);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresVsc.add(shu);
                    vscScoreHistory.setSysName(Constants.Systems.VSC);
                    break;
                case Constants.Systems.POS:
                    sr.setSysIcon(R.drawable.ic_pos);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresPos.add(shu);
                    posScoreHistory.setSysName(Constants.Systems.POS);
                    break;
                case Constants.Systems.CANCELAMENTO:
                    sr.setSysIcon(R.drawable.ic_monitor);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresCan.add(shu);
                    canScoreHistory.setSysName(Constants.Systems.CANCELAMENTO);
                    break;
                case Constants.Systems.NGC:
                    sr.setSysIcon(R.drawable.ic_quotes);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresNgc.add(shu);
                    ngcScoreHistory.setSysName(Constants.Systems.NGC);
                    break;
                case Constants.Systems.HYAKU:
                    sr.setSysIcon(R.drawable.ic_bulb);
                    shu = new ScoreHistoryUnit();
                    shu.setDate(sr.get_id().getDate());
                    shu.setScore(sr.getScore());
                    scoresHyaku.add(shu);
                    hyakuScoreHistory.setSysName(Constants.Systems.HYAKU);
                    break;
            }
        }

        Collections.sort(scoresApp);
        Collections.sort(scoresAcc);
        Collections.sort(scoresCieloPay);
        Collections.sort(scoresCrd);
        Collections.sort(scoresFac);
        Collections.sort(scoresFarol);
        Collections.sort(scoresPfi);
        Collections.sort(scoresRecebaMais);
        Collections.sort(scoresSigo);
        Collections.sort(scoresSite);
        Collections.sort(scoresSky);
        Collections.sort(scoresStar);
        Collections.sort(scoresStc);
        Collections.sort(scoresVsc);
        Collections.sort(scoresPos);
        Collections.sort(scoresCan);
        Collections.sort(scoresNgc);
        Collections.sort(scoresHyaku);

        appScoreHistory.setScoreHistoryList(scoresApp);
        accScoreHistory.setScoreHistoryList(scoresAcc);
        cieloPayScoreHistory.setScoreHistoryList(scoresCieloPay);
        crdScoreHistory.setScoreHistoryList(scoresCrd);
        facScoreHistory.setScoreHistoryList(scoresFac);
        farolScoreHistory.setScoreHistoryList(scoresFarol);
        pfiScoreHistory.setScoreHistoryList(scoresPfi);
        recebaMaisScoreHistory.setScoreHistoryList(scoresRecebaMais);
        sigoScoreHistory.setScoreHistoryList(scoresSigo);
        siteScoreHistory.setScoreHistoryList(scoresSite);
        skyScoreHistory.setScoreHistoryList(scoresSky);
        starScoreHistory.setScoreHistoryList(scoresStar);
        stcScoreHistory.setScoreHistoryList(scoresStc);
        vscScoreHistory.setScoreHistoryList(scoresVsc);
        canScoreHistory.setScoreHistoryList(scoresCan);
        ngcScoreHistory.setScoreHistoryList(scoresNgc);
        posScoreHistory.setScoreHistoryList(scoresPos);
        hyakuScoreHistory.setScoreHistoryList(scoresHyaku);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.SharedPreferences.APP_HISTORY, new Gson().toJson(appScoreHistory));
        editor.putString(Constants.SharedPreferences.CENTRALIZADOR_HISTORY, new Gson().toJson(accScoreHistory));
        editor.putString(Constants.SharedPreferences.CIELO_PAY_PTO_CORE_HISTORY, new Gson().toJson(cieloPayScoreHistory));
        editor.putString(Constants.SharedPreferences.CREDENCIAMENTO_HISTORY, new Gson().toJson(crdScoreHistory));
        editor.putString(Constants.SharedPreferences.FAC_HISTORY, new Gson().toJson(facScoreHistory));
        editor.putString(Constants.SharedPreferences.FAROL_HISTORY, new Gson().toJson(farolScoreHistory));
        editor.putString(Constants.SharedPreferences.PFI_HISTORY, new Gson().toJson(pfiScoreHistory));
        editor.putString(Constants.SharedPreferences.RECEBA_MAIS_HISTORY, new Gson().toJson(recebaMaisScoreHistory));
        editor.putString(Constants.SharedPreferences.SIGO_HISTORY, new Gson().toJson(sigoScoreHistory));
        editor.putString(Constants.SharedPreferences.SITE_HISTORY, new Gson().toJson(siteScoreHistory));
        editor.putString(Constants.SharedPreferences.SKYLINE_HISTORY, new Gson().toJson(skyScoreHistory));
        editor.putString(Constants.SharedPreferences.STAR_HISTORY, new Gson().toJson(starScoreHistory));
        editor.putString(Constants.SharedPreferences.STC_HISTORY, new Gson().toJson(stcScoreHistory));
        editor.putString(Constants.SharedPreferences.VSC_HISTORY, new Gson().toJson(vscScoreHistory));
        editor.putString(Constants.SharedPreferences.POS_HISTORY, new Gson().toJson(posScoreHistory));
        editor.putString(Constants.SharedPreferences.NGC_HISTORY, new Gson().toJson(ngcScoreHistory));
        editor.putString(Constants.SharedPreferences.CAN_HISTORY, new Gson().toJson(canScoreHistory));
        editor.putString(Constants.SharedPreferences.HYAKU_HISTORY, new Gson().toJson(hyakuScoreHistory));

        Log.i(Constants.SharedPreferences.SHARED_PREFERENCES, Constants.SharedPreferences.SAVE_SCORE_HISTORY);

        Collections.sort(appList, (Comparator<Object>) (o1, o2) ->
                Float.compare(((ScoreResults) o2).get_id().getDate(),
                        ((ScoreResults) o1).get_id().getDate()));

        editor.putString(Constants.SharedPreferences.LAST_OK_APPLICATIONS, new Gson().toJson(appList.subList(0, Constants.Mongo.systems)));
        editor.apply();

        Log.i(Constants.SharedPreferences.SHARED_PREFERENCES, Constants.SharedPreferences.SAVE_LAST_OK_APPS);

    }

}
