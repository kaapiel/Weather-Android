package br.com.cielo.weather.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherActivity;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.service.HttpHelper;
import br.com.cielo.weather.ui.ranking.RankingFragment;
import br.com.cielo.weather.ui.scores.OverallScoreFragment;
import br.com.cielo.weather.util.Constants;
import br.com.cielo.weather.util.CustomTypefaceSpan;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends WeatherActivity implements OnChartGestureListener {

    @Nullable
    @BindView(R.id.menu_header_name_user)
    TextView menuUserName;

    @Nullable
    @BindView(R.id.menu_header_logo)
    ImageView photo;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigationview_home)
    NavigationView navigationviewHome;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.loading_content)
    RelativeLayout loadingContent;

    TextView menuHello;
    ContentLoadingProgressBar loadingHeader;
    private ActionBarDrawerToggle mDrawerToggle;
    public User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        u = new Gson()
                .fromJson(getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.JIRA_USER, Constants.SharedPreferences.JIRA_USER), User.class);

        setSupportActionBar(toolbar);

        fullScreenCall();
        initViews();
        menuItemFonts();

        View drawerHeader = navigationviewHome.inflateHeaderView(R.layout.menu_header);
        navigationviewHome.setItemIconTintList(null);

        photo = drawerHeader.findViewById(R.id.menu_header_logo);
        menuUserName = drawerHeader.findViewById(R.id.menu_header_name_user);
        menuHello = drawerHeader.findViewById(R.id.menu_header_hello);
        loadingHeader = drawerHeader.findViewById(R.id.loading_header);

        navigationviewHome.inflateMenu(R.menu.itens_menu_logged);

        Menu menu = navigationviewHome.getMenu();
        for(int i = 0; i < menu.size(); i++){
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            int end = spanString.length();
            if(item.getItemId() == R.id.menu_sistemas || item.getItemId() == R.id.menu_configs){
                spanString.setSpan(new RelativeSizeSpan(1.7f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spanString.setSpan(new RelativeSizeSpan(2f), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            item.setTitle(spanString);
        }

        navigationviewHome.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_rank) {
                replaceFragment(getSupportFragmentManager(), new RankingFragment(), "Raniking", true);

            } else if (item.getItemId() == R.id.menu_sistemas_geral) {
                replaceFragment(getSupportFragmentManager(), new OverallScoreFragment(), "SCORE", true);

            } else if(item.getItemId() == R.id.menu_sugestoes) {
                showFeedbackDialog(this, u);

            } else if (item.getItemId() == R.id.menu_sair) {
                showLogoutDialog(this);
            }
            drawerLayout.closeDrawers();
            return false;
        });

        navigationviewHome.getMenu().performIdentifierAction(R.id.menu_rank, 0);

        if(u.getPic() == null){
            new WelcomeRequest().execute();
        } else {
            initHeaderComponents();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerLayout.postDelayed(() ->
                drawerLayout.openDrawer(Gravity.LEFT), 500);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews() {

        navigationviewHome.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            return false;
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.syncState();
                closeKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerToggle.syncState();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
    }

    private void menuItemFonts() {
        Menu m = navigationviewHome.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "font/museosans_300.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());

        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void replaceFragment(FragmentManager fm, Fragment fragment, String label, boolean toBack) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        transaction.replace(R.id.content_frame, fragment, label);

        if (toBack) {
            transaction.addToBackStack(label);
        }

        transaction.commitAllowingStateLoss();
        drawerLayout.closeDrawers();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class WelcomeRequest extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingHeader.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Bitmap bm = null;
            try {
                bm = new HttpHelper(getBaseContext()).getImageFromUrl(u.getAvatarUrls().getAvatar48x48());
                u.setPic(bitMapToString(bm));
            } catch (Exception e) {
                Log.e("ERROR ON BITMAP", e.getStackTrace()[0].toString());
            }

            SharedPreferences.Editor editor = HomeActivity.this.getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE).edit();
            editor.putString(Constants.SharedPreferences.JIRA_USER, new Gson().toJson(u));
            editor.apply();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initHeaderComponents();
        }
    }

    public String bitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (NullPointerException npe){
            return null;
        }
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void initHeaderComponents() {

        menuUserName.setVisibility(View.VISIBLE);

        if(u.getDisplayName().length() > 24){
            String[] nameSplitted = u.getDisplayName().split(" ");
            String name = nameSplitted[0].concat(" ").concat(nameSplitted[nameSplitted.length-1]);
            if(name.length() > 24){
                menuUserName.setText(nameSplitted[0]);
            } else {
                menuUserName.setText(name);
            }
        } else {
            menuUserName.setText(u.getDisplayName());
        }

        photo.setImageBitmap(u.getPic() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.ic_happy)
                : stringToBitMap(u.getPic()));

        menuHello.setVisibility(View.VISIBLE);
        menuHello.setText(getResources().getString(R.string.menu_header_greet));

        loadingHeader.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    public void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        openDrawer();
    }

}
