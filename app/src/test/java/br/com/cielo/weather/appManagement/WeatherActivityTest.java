package br.com.cielo.weather.appManagement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeatherActivityTest {

    private WeatherActivity cra;

    @Before
    public void setUp() throws Exception {
        cra = new WeatherActivity();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isAlive() {
        Assert.assertTrue(cra.isAlive());
    }

    @Test
    public void fullScreenCall() {

    }

    @Test
    public void showLogoutDialog() {
    }

    @Test
    public void showFeedbackDialog() {
    }

    @Test
    public void showRatingDialog() {
    }

    @Test
    public void showThanksDialog() {
    }

    @Test
    public void startGmailService() {
    }
}