package br.com.aguido.weather.util;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.jira.User;

public class Util {

    public int getRandomIcon(){

        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_ecommerce);icons.add(R.drawable.ic_handshake);icons.add(R.drawable.ic_calendar);
        icons.add(R.drawable.ic_store);icons.add(R.drawable.ic_lighthouse);icons.add(R.drawable.ic_card);
        icons.add(R.drawable.ic_lio);icons.add(R.drawable.ic_smartmachine);icons.add(R.drawable.ic_cards);
        icons.add(R.drawable.icon_lio_v2);icons.add(R.drawable.ic_telephone);icons.add(R.drawable.ic_cash);
        icons.add(R.drawable.ic_user);icons.add(R.drawable.ic_3g);icons.add(R.drawable.ic_check);
        icons.add(R.drawable.ic_pos);icons.add(R.drawable.ic_bulb);icons.add(R.drawable.ic_cloud_extract);
        icons.add(R.drawable.ic_delivery);icons.add(R.drawable.ic_facebook);icons.add(R.drawable.ic_flag);
        icons.add(R.drawable.ic_geo);icons.add(R.drawable.ic_gift);icons.add(R.drawable.ic_happy);
        icons.add(R.drawable.ic_heart);icons.add(R.drawable.ic_infinity);icons.add(R.drawable.ic_law);
        icons.add(R.drawable.ic_lock);icons.add(R.drawable.ic_monitor);icons.add(R.drawable.ic_no_cash);
        icons.add(R.drawable.ic_percent);icons.add(R.drawable.ic_play);icons.add(R.drawable.ic_push);
        icons.add(R.drawable.ic_quotes);icons.add(R.drawable.ic_receive_cash);icons.add(R.drawable.ic_return);
        icons.add(R.drawable.ic_right);icons.add(R.drawable.ic_search);icons.add(R.drawable.ic_telephone);
        icons.add(R.drawable.ic_talk);icons.add(R.drawable.ic_warn);icons.add(R.drawable.ic_wifi);

        return icons.get(new Random().nextInt(5));
    }

    public static Drawable getDrawableByName(Context context, String imageName) {
        if (imageName == null) {
            return null;
        }

        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(imageName, "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    public static void hideKeyboard(View view, Context context) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private ArrayList<Bitmap> takeScreenshot(View... views) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        for (View v : views) {
            try {

                Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                Drawable bgDrawable = v.getBackground();
                if (bgDrawable != null)
                    bgDrawable.draw(canvas);
                else
                    canvas.drawColor(Color.WHITE);
                v.draw(canvas);

                bitmaps.add(bitmap);

            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
        return bitmaps;
    }

    public void shareImages(Context c, User u, View... views){

        Intent shareIntent = new Intent();
        ArrayList<Bitmap> bitmaps = takeScreenshot(views);
        ArrayList<Uri> uris = new ArrayList<>();

        for (Bitmap bitmap : bitmaps) {
            String bitmapPath = MediaStore.Images.Media.insertImage(c.getContentResolver(), bitmap,"title", null);
            Uri bitmapUri = Uri.parse(bitmapPath);
            uris.add(bitmapUri);
        }

        shareIntent.putExtra(Intent.EXTRA_STREAM, uris);
        shareIntent.setType("image/*");
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"gabriel.fraga@weather.com.br"});
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Graficos gerados do Weather App");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Segue em anexo os resultados extraidos do Weather Dashboard.\n\n"+u.getDisplayName());
        c.startActivity(Intent.createChooser(shareIntent, "Exportar para..."));

    }
}
