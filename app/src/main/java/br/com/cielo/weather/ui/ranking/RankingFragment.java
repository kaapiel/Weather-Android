package br.com.cielo.weather.ui.ranking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Comparator;

import br.com.cielo.weather.R;
import br.com.cielo.weather.appManagement.WeatherFragment;
import br.com.cielo.weather.model.jira.User;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.ui.customs.RankingRecyclerViewAdapter;
import br.com.cielo.weather.ui.home.HomeActivity;
import br.com.cielo.weather.ui.scores.SystemScoreFragment;
import br.com.cielo.weather.util.Constants;
import br.com.cielo.weather.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class RankingFragment extends WeatherFragment {

    @BindView(R.id.list_ranking)
    RecyclerView list;

    @BindView(R.id.fab_gamefication)
    FloatingActionButton fabGame;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ranking_parallax, container, false);
        ButterKnife.bind(this, view);

        fullScreenCall();

        RankingRecyclerViewAdapter rrva = new RankingRecyclerViewAdapter(this, getApplicationList(getApplicationsFromBundle()));
        list.setAdapter(rrva);
        list.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        fabGame.setOnClickListener(v -> {

            Toast.makeText(getContext(), "Em desenvolvimento...", Toast.LENGTH_SHORT).show();
        });


        rrva.setOnItemClickListener(position -> {
            FragmentTransaction transaction = RankingFragment.this.getActivity().getSupportFragmentManager().beginTransaction();
            Bundle b = new Bundle();
            b.putString(Constants.SharedPreferences.SYSTEM_NAME,
                    getApplicationList(getApplicationsFromBundle()).get(position).getApplicationName());

            SystemScoreFragment ns = new SystemScoreFragment();
            ns.setArguments(b);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.replace(R.id.content_frame, ns, "notas");
            transaction.commitAllowingStateLoss();
        });

        return view;
    }

    private ScoreResults[] getApplicationsFromBundle(){

        ScoreResults[] apps = new Gson()
                .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.LAST_OK_APPLICATIONS, Constants.SharedPreferences.LAST_OK_APPLICATIONS), ScoreResults[].class);

        Arrays.sort(apps, (Comparator<Object>) (o1, o2) ->
                Float.compare(((ScoreResults) o2).getScore(),
                        ((ScoreResults) o1).getScore()));
        return apps;
    }

    @OnClick(R.id.open_drawer)
    void onMenuClicked() {
        ((HomeActivity) getActivity()).openDrawer();
    }

    @OnClick(R.id.share_menu)
    void onShareClick(){

        User u = new Gson()
                .fromJson(getContext().getSharedPreferences(Constants.SharedPreferences.USER_PREFERENCES, MODE_PRIVATE)
                        .getString(Constants.SharedPreferences.JIRA_USER, Constants.SharedPreferences.JIRA_USER), User.class);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            //Permission already granted
            new Util().shareImages(getActivity(), u, list);
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
}
