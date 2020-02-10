package br.com.aguido.weather.ui.scores;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import br.com.aguido.weather.R;
import br.com.aguido.weather.appManagement.WeatherFragment;
import br.com.aguido.weather.model.jira.User;
import br.com.aguido.weather.model.mongo.ScoreResults;
import br.com.aguido.weather.model.numbers.OverallNumbers;
import br.com.aguido.weather.ui.home.HomeActivity;
import br.com.aguido.weather.util.Constants;
import br.com.aguido.weather.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class SystemScoreFragment extends WeatherFragment {

    @BindView(R.id.code_lines_number_label)
    TextView codeLinesLabel;

    @BindView(R.id.issue_number_label)
    TextView issueLabel;

    @BindView(R.id.system_name_title)
    TextView sysTitle;

    @BindView(R.id.last_updated_title)
    TextView lastUpdt;

    @BindView(R.id.main_content)
    CoordinatorLayout cl;

    @BindView(R.id.appbar)
    AppBarLayout abl;

    @BindView(R.id.frame_system)
    FrameLayout fl;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout ctbl;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.system_score_fragment, container, false);
        ButterKnife.bind(this, view);
        this.view = view;

        fullScreenCall();
        replaceFragment(getFragmentManager(), new SystemGraphsFragment(), "teste");

        return view;
    }

    @OnClick(R.id.open_drawer)
    void onMenuClicked() {
        ((HomeActivity) getActivity()).openDrawer();
    }

    @OnClick(R.id.menu_share)
    void onShareClick(){

        User u = new Gson()
                .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.JIRA_USER, Constants.SharedPreferences.JIRA_USER), User.class);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            //Permission already granted
            new Util().shareImages(getActivity(), u, abl, fl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 1){
            //User allowed the location and you can read it now
            onShareClick();
        } else {
            Toast.makeText(getContext(), this.getString(R.string.splash_wifi_denied), Toast.LENGTH_LONG).show();
        }
    }

    private void replaceFragment(FragmentManager fm, Fragment fragment, String label) {

        ScoreResults[] apps;

        try {
            apps = new Gson()
                    .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                            .getString(Constants.SharedPreferences.LAST_OK_APPLICATIONS, Constants.SharedPreferences.LAST_OK_APPLICATIONS), ScoreResults[].class);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            apps = generateMockScoreResults();
        }

        OverallNumbers on = getNumbers(apps, lastUpdt);

        startCountAnimation(codeLinesLabel,
                1000,
                Float.valueOf(removeLastChar(on.getOverallLines())),
                validateUnit(on.getOverallLines()),
                true);

        startCountAnimation(issueLabel,
                1800,
                Float.valueOf(removeLastChar(on.getOverallIssues())),
                validateUnit(on.getOverallIssues()),
                true);

        String sysName = getArguments().get(Constants.SharedPreferences.SYSTEM_NAME).toString();

        if(sysName.length() > 16){
            sysTitle.setText(sysName.substring(0, 17));
        } else {
            sysTitle.setText(sysName);
        }

        Bundle b = new Bundle();
        b.putSerializable(Constants.SharedPreferences.SYSTEM_NUMBERS, on);
        b.putString(Constants.SharedPreferences.SYSTEM_NAME, getArguments().get(Constants.SharedPreferences.SYSTEM_NAME).toString());
        fragment.setArguments(b);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.replace(R.id.frame_system, fragment, label);
        transaction.commitAllowingStateLoss();
    }

}
