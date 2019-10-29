package br.com.cielo.weather.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoTimeoutException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherActivity;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.service.WeatherManager;
import br.com.cielo.weather.ui.home.HomeActivity;
import br.com.cielo.weather.ui.login.LoginActivity;
import br.com.cielo.weather.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends WeatherActivity {

    @BindView(R.id.loading_content)
    RelativeLayout loading;

    @BindView(R.id.loading_progress_text)
    TextView ts;

    private static final int LOCATION = 1;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        if(getIntent().hasExtra("mensagem")){
            Intent intent = new Intent(this, Notification.class);
            intent.putExtra("data", String.valueOf(getIntent().getExtras().get("mensagem")));
            startActivity(intent);
        } else {
            preferences = getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE);
            fullScreenCall();
            tryToReadSSID();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LOCATION){
            //User allowed the location and you can read it now
            tryToReadSSID();
        } else {
            Toast.makeText(this, this.getString(R.string.splash_wifi_denied), Toast.LENGTH_LONG).show();
            Handler h = new Handler();
            h.postDelayed(() -> System.exit(0), 4500);
        }
    }

    private void tryToReadSSID() {
        //If requested permission isn't Granted yet
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
        }else{//Permission already granted
            validateWifiNetwork();
        }
    }

    private void validateWifiNetwork() {

        if(!new WeatherManager(this).isEmulator()){
            WifiInfo wifiInfo = new WeatherManager(this).getWifiInfo();
            if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED ||
                    WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.OBTAINING_IPADDR ||
                    wifiInfo.getSupplicantState().name().equalsIgnoreCase("COMPLETED")) {
                if(wifiInfo.getSSID().contains(Constants.Wifi.SIGMA) || wifiInfo.getSSID().contains(Constants.Wifi.VISANET_CORP)){
                    new MongoDB().execute();
                } else {
                    exitApp();
                }
            } else {
                exitApp();
            }
        } else {
            new MongoDB().execute();
        }
    }

    public void exitApp(){

        Toast.makeText(this, this.getString(R.string.splash_wifi_connect), Toast.LENGTH_LONG).show();

        Handler h = new Handler();
        h.postDelayed(() -> {
            System.exit(0);
        }, 4000);
    }

    class MongoDB extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
            ts.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            MongoCredential mc = MongoCredential.createCredential(
                    Constants.Mongo.MONGO_USERNAME,
                    Constants.Mongo.MONGO_DATABASE_NAME,
                    Constants.Mongo.MONGO_PASSWORD.toCharArray());

            try (MongoClient mongoClient = new MongoClient(
                    new ServerAddress(
                            Constants.Mongo.MONGO_IP,
                            Constants.Mongo.MONGO_PORT),
                    Collections.singletonList(mc))) {

                MongoDatabase db = mongoClient.getDatabase(Constants.Mongo.MONGO_DATABASE_NAME);
                MongoCollection<Document> applications = db.getCollection(Constants.Mongo.MONGO_APPLICATIONS_COLLECTION);

                SimpleDateFormat sdf = new SimpleDateFormat(
                        Constants.Mongo.MONGO_ID_DATE_PATTERN,
                        Locale.ENGLISH);

                List<ScoreResults> appList = new ArrayList<>();
                int dias = -1;

                while(appList.isEmpty() || appList.size() !=
                        (Constants.Mongo.systems*Constants.Mongo.systemsHistory)){

                    dias++;
                    Date d = new Date();
                    d.setDate(new Date().getDate() - dias);
                    String format = sdf.format(d);

                    MongoCursor<Document> i = getMongoCursor(applications, getQueryDocument(sdf, format));

                    while (i.hasNext()) {
                        Document obj = i.next();
                        ScoreResults sr = new Gson().fromJson(obj.toJson(), ScoreResults.class);
                        appList.add(sr);
                        SplashScreen.this.runOnUiThread(() -> ts.setText(sr.getApplicationName()));
                    }
                }

                configSharedPreferences(appList, preferences);

                mongoClient.close();

            } catch (Exception e) {
                e.printStackTrace();
                SplashScreen.this.runOnUiThread(() -> Toast.makeText(getBaseContext(), Constants.Mongo.IMPOSSIBLE_CONNECT_MONGO, Toast.LENGTH_LONG).show());
                SplashScreen.this.runOnUiThread(() -> new Handler().postDelayed(() -> System.exit(0), 4500));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            loading.setVisibility(View.GONE);
            ts.setVisibility(View.GONE);

            try{
                new Gson()
                        .fromJson(getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                                .getString(Constants.SharedPreferences.JIRA_USER, Constants.SharedPreferences.JIRA_USER), User.class);

                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }catch (Exception e){
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public Document getQueryDocument(SimpleDateFormat sdf, String format){

        Document doc = null;
        try {
            doc = new Document(Constants.Mongo.GTE, sdf.parse(format));
        } catch (ParseException e) {
            Log.e(Constants.Mongo.ERROR_MONGO_DOCUMENT, e.getStackTrace()[0].toString());
        }
        return doc;
    }

    public MongoCursor<Document> getMongoCursor(MongoCollection<Document> applications, Document doc){
        MongoCursor<Document> i;
        try{
            i = applications.find(new Document(Constants.Mongo._ID, doc)).limit(Constants.Mongo.systems).iterator();
        } catch (MongoTimeoutException mte){
            return null;
        }
        return i;
    }

}
