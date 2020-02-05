package br.com.aguido.weather.appManagement;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.jira.Alert;
import io.fabric.sdk.android.Fabric;

public class WeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        generateAlerts();
     }

    private void generateAlerts() {
        generateAlertNoConnection();
        generateAlertServiceError();
        generateAlertOpenUrl();
        generateAlertSessionExpired();
        generateAlertErrorGeneric();
    }

    private void generateAlertNoConnection() {
        Alert noConnection = new Alert();
        noConnection.setTitle(getResources().getString(R.string.connection_error_title));
        noConnection.setDescription(getResources().getString(R.string.connection_error_description));
        noConnection.setImage(getResources().getResourceEntryName(R.drawable.ic_launcher));
        noConnection.setPositiveButton(getResources().getString(R.string.connection_error_option_try_again));
    }

    private void generateAlertServiceError() {
        Alert serviceError = new Alert();
        serviceError.setTitle(getResources().getString(R.string.generic_error_title));
        serviceError.setDescription(getResources().getString(R.string.generic_error_description));
        serviceError.setImage(getResources().getResourceEntryName(R.drawable.ic_launcher));
        serviceError.setPositiveButton(getResources().getString(R.string.generic_error_option_try_again));
    }

    private void generateAlertOpenUrl() {
        Alert openUrl = new Alert();
        openUrl.setTitle(getResources().getString(R.string.open_url_title));
        openUrl.setDescription(getResources().getString(R.string.open_url_description));
        openUrl.setImage(getResources().getResourceEntryName(R.drawable.ic_launcher));
        openUrl.setPositiveButton(getResources().getString(R.string.open_url_option_positive));
        openUrl.setNegativeButton(getResources().getString(R.string.open_url_option_negative));
    }

    private void generateAlertErrorGeneric() {
        Alert errorGeneric = new Alert();
        errorGeneric.setTitle(getResources().getString(R.string.generic_error_title));
        errorGeneric.setDescription(getResources().getString(R.string.error_generic_description));
        errorGeneric.setImage(getResources().getResourceEntryName(R.drawable.ic_launcher));
        errorGeneric.setPositiveButton(getResources().getString(R.string.error_generic_option_positive));
        errorGeneric.setNegativeButton(getResources().getString(R.string.error_generic_option_negative));
    }

    private void generateAlertSessionExpired() {
        Alert expiredSession = new Alert();
        expiredSession.setTitle(getResources().getString(R.string.logout_session_expired_title));
        expiredSession.setDescription(getResources().getString(R.string.logout_session_expired_desc));
        expiredSession.setImage(getResources().getResourceEntryName(R.drawable.ic_launcher));
        expiredSession.setPositiveButton(getResources().getString(R.string.logout_session_expired_option));
    }

}
