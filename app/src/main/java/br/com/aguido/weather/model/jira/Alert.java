package br.com.aguido.weather.model.jira;

import android.graphics.Bitmap;

import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;

/**
 * Created by Gabriel Aguido Fraga on 08/09/15.
 */
public class Alert implements Serializable {
    private RemoteMessage rm;
    private String image;
    private Bitmap imageFromUrl;
    private String title;
    private String data;
    private String description;
    private String positiveButton;
    private String negativeButton;
    private boolean getPassword = false;

    public Alert() {
    }

    public Alert(String image, String title, String description, String positiveButton, String negativeButton) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.getPassword = false;
    }

    public Alert(String image, String title, String description, String positiveButton, String negativeButton, boolean getPassword) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.getPassword = getPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPositiveButton() {
        return positiveButton;
    }

    public void setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
    }

    public String getNegativeButton() {
        return negativeButton;
    }

    public void setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
    }

    public boolean isGetPassword() {
        return getPassword;
    }

    public void setGetPassword(boolean getPassword) {
        this.getPassword = getPassword;
    }

    public Bitmap getImageFromUrl() {
        return imageFromUrl;
    }

    public void setImageFromUrl(Bitmap imageFromUrl) {
        this.imageFromUrl = imageFromUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RemoteMessage getRm() {
        return rm;
    }

    public void setRm(RemoteMessage rm) {
        this.rm = rm;
    }
}