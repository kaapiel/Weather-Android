package br.com.cielo.weather.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import br.com.cielo.weather.R;

/**
 * Created by Gabriel Aguido Fraga on 5/11/16.
 */
public class DialogLoading {

    private Context context;
    private Dialog dialog;

    public DialogLoading(Context c){
        context = c;
    }

    public void show(){
        dialog = new Dialog(context, R.style.CieloReportsTheme_Dialog_Loading);

        RelativeLayout contentView = (RelativeLayout) ((Activity) context).getLayoutInflater().inflate(R.layout.loading, null) ;

        dialog.setContentView(contentView);

        dialog.setCancelable(false);

        dialog.findViewById(R.id.loading_content).setVisibility(View.VISIBLE);

        if (!((Activity) context).isFinishing())
            dialog.show();
    }

    public void hide(){
        if (dialog != null)
            dialog.dismiss();
    }

}
