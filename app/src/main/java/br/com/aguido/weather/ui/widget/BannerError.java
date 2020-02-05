package br.com.aguido.weather.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aguido.weather.R;

/**
 * Created by Gabriel Aguido Fraga on 04/09/15.
 */
public class BannerError extends RelativeLayout {

    private RelativeLayout bannerContent;
    private TextView bannerText;
    private Context context;

    public BannerError(Context context) {
        super(context);
    }

    public BannerError(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflate();
    }

    private void inflate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.banner_error, this, true);

        bannerText = findViewById(R.id.banner_error_text);
        bannerContent = findViewById(R.id.banner_error_content);
    }

    public void showAndAnimBannerError() {
        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(VISIBLE);
                Handler handler = new Handler();
                handler.postDelayed(() ->
                        hideAndAnimBannerError(), 2600);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        bannerContent.startAnimation(slideUp);
    }

    public void hideAndAnimBannerError() {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        bannerContent.startAnimation(slideDown);
    }

    public void setBannerText(String bannerText) {
        this.bannerText.setText(bannerText);
    }
}
