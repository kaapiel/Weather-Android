package br.com.cielo.weather.ui.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.cielo.weather.R;

@EViewGroup(R.layout.view_login_footer)
public class FooterView extends RelativeLayout {

    @Bean
    FooterController mFooterController;

    public FooterView(Context context) {
        super(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
