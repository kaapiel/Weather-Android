package br.com.aguido.weather.ui.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.firebase.messaging.RemoteMessage;

import br.com.aguido.weather.R;
import br.com.aguido.weather.appManagement.WeatherActivity;
import butterknife.ButterKnife;

public class Notification extends WeatherActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        ButterKnife.bind(this);
        try{
            RemoteMessage data = (RemoteMessage) getIntent().getExtras().get("data");
            showPushNotificationsDialog(this, data);
        } catch (Exception e){
            String details = (String) getIntent().getExtras().get("data");
            showPushNotificationsDialog(this, details);
        }

    }
}
