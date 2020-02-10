package br.com.aguido.weather.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.jira.Alert;
import br.com.aguido.weather.service.HttpHelper;
import br.com.aguido.weather.util.Util;

/**
 * Created by Gabriel Aguido Fraga on 10/11/15.
 */
public class DialogCustomAlert {

    private Alert alert;
    private Dialog dialog;
    private ImageView customDialogAlertsImage;
    private TextView customDIalogAlertsTitle;
    private TextView customDIalogAlertsDescription;
    public TextInputEditText edtFeedback;
    private RatingBar rb;
    private TextView customDialogAlertsPositiveButton;
    private TextView customDialogAlertsNegativeButton;
    private ProgressBar pb;

    public void showCustomDialog(final Context context, int layout, Alert alert, final boolean isCancelable,
                                 final AlertDialogClickListener alertDialogClickListener) {

        this.alert = alert;
        dialog = new Dialog(context, R.style.WeatherReportsTheme_CustomDialog);
        dialog.setContentView(layout);

        pb = dialog.findViewById(R.id.progress_loading_push);
        customDialogAlertsImage = dialog.findViewById(R.id.custom_dialog_alerts_image);
        customDIalogAlertsTitle = dialog.findViewById(R.id.custom_dialog_alerts_title);
        customDIalogAlertsDescription = dialog.findViewById(R.id.custom_dialog_alerts_description);
        customDialogAlertsPositiveButton = dialog.findViewById(R.id.custom_dialog_alerts_positive_button);
        customDialogAlertsNegativeButton = dialog.findViewById(R.id.custom_dialog_alerts_negative_button);
        edtFeedback = dialog.findViewById(R.id.edt_feedback);

        try{
            rb = dialog.findViewById(R.id.rating);
            LayerDrawable stars = (LayerDrawable) rb.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.gold_weather), PorterDuff.Mode.SRC_ATOP);
        } catch (NullPointerException npe){
            //TODO
        }

        showCustomDialog(context, this.alert);

        try{
            customDialogAlertsPositiveButton.setOnClickListener(v -> {
                dialog.dismiss();
                alertDialogClickListener.onPositiveClick();
            });
            customDialogAlertsNegativeButton.setOnClickListener(v -> {
                dialog.dismiss();
                alertDialogClickListener.onNegativeClick();
            });

        } catch (NullPointerException npe){
            //TODO
        }

        dialog.show();
        dialog.setCancelable(isCancelable);
    }

    private void showCustomDialog(Context context, Alert alert) {

        if(!TextUtils.isEmpty(alert.getImage())) {
            customDialogAlertsImage.setImageDrawable(Util.getDrawableByName(context, alert.getImage()));
        } else {
            new GetImageFromUrl().execute(alert, context);
        }

        try{
            customDialogAlertsPositiveButton.setText(this.alert.getPositiveButton());
            customDialogAlertsNegativeButton.setText(this.alert.getNegativeButton());
            customDIalogAlertsDescription.setText(alert.getDescription());
            customDIalogAlertsTitle.setText(alert.getTitle());
            edtFeedback.setText(alert.getData());
        } catch (NullPointerException npe){
            //TODO
        }

    }

    public interface AlertDialogClickListener {
        void onPositiveClick();
        void onNegativeClick();
    }

    public class GetImageFromUrl extends AsyncTask<Object, String, String> {

        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            customDIalogAlertsTitle.setText("Carregando...");
            customDIalogAlertsDescription.setText("Carregando...");
            edtFeedback.setText("Carregando...");
        }

        @Override
        protected String doInBackground(Object... objs) {

            alert = ((Alert) objs[0]);
            context = ((Context) objs[1]);
            Bitmap imageFromUrl = new HttpHelper(context).getImageFromUrl(
                            alert.getRm().getNotification().getImageUrl().toString());

            alert.setImageFromUrl(imageFromUrl);
            return null;

        }

        @Override
        protected void onPostExecute(String context) {
            super.onPostExecute(context);
            pb.setVisibility(View.GONE);
            customDialogAlertsImage.setImageBitmap(alert.getImageFromUrl());
            customDIalogAlertsTitle.setText(alert.getRm().getNotification().getTitle());
            customDIalogAlertsDescription.setText(alert.getRm().getNotification().getBody());
            edtFeedback.setText(alert.getRm().getData().get("mensagem"));
        }
    }
}
