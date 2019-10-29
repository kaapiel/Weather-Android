package br.com.cielo.weather.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import br.com.cielo.weather.R;

/**
 * Created by Gabriel Aguido Fraga on 1/5/16.
 */
public class WeatherLoading extends AppCompatImageView {

    public WeatherLoading(Context context) {
        super(context);
    }

    public WeatherLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLoading();
    }

    public WeatherLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupLoading();
    }

    private void setupLoading(){
        setBackgroundResource(R.drawable.loading_animated);

        post(() -> {
            AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
            frameAnimation.start();
        });
    }
}
