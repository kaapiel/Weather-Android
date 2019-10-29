package br.com.cielo.weather.appManagement;

import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.com.cielo.weather.R;
import br.com.cielo.weather.model.mongo.ScoreResults;
import br.com.cielo.weather.model.numbers.OverallNumbers;
import br.com.cielo.weather.util.Constants;

/**
 * Created by Gabriel Aguido Fraga on 12/11/15.
 */
public class WeatherFragment extends Fragment {

    protected void fullScreenCall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    protected void startCountAnimation(TextView tv, int time, float to, String unit, boolean animate) {

        DecimalFormat df = new DecimalFormat("###.#");
        df.setRoundingMode(RoundingMode.DOWN);

        if (animate) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, to);
            animator.setDuration(time);
            animator.addUpdateListener(animation ->
                    tv.setText(df.format(animation.getAnimatedValue()).concat(unit)));
            animator.start();
        } else {
            tv.setText(df.format(to).concat(unit));
        }

    }

    protected String validateUnit(Object overAll) {

        String unit = overAll.toString();

        try {
            if (Integer.valueOf(unit) >= 1000000) {
                return "M";
            } else if (Integer.valueOf(unit) >= 1000) {
                return "k";
            } else {
                return "";
            }

        } catch(NumberFormatException nfe){

            if (unit.contains("M")) {
                return "M";
            } else if (unit.contains("k")) {
                return "k";
            } else {
                return "";
            }
        }
    }

    protected OverallNumbers getNumbers(ScoreResults[] apps){

        OverallNumbers on = new OverallNumbers();
        int overAllLines = 0;
        int overallIssues = 0;
        int overallBugs = 0;

        List<ScoreResults> a = new ArrayList<>(Arrays.asList(apps));

        for (ScoreResults app : a) {
            overAllLines += app.getNcloc();
            overallIssues += app.getOpenIssues();
            overallBugs += app.getIssuesBug();
        }

        on.setOverallLines(compressNumbers(overAllLines));
        on.setOverallIssues(compressNumbers(overallIssues));
        on.setOverallBugs(compressNumbers(overallBugs));

        return on;
    }

    protected OverallNumbers getNumbers(ScoreResults[] apps, TextView lastUpdt){

        OverallNumbers on = new OverallNumbers();
        int overAllLines = 0;
        int overallIssues = 0;
        int overallBugs = 0;

        List<ScoreResults> a = new ArrayList<>(Arrays.asList(apps));

        for (ScoreResults app : a) {
            if(app.getApplicationName().toUpperCase().contains(this.getArguments().get(Constants.SharedPreferences.SYSTEM_NAME).toString().toUpperCase())){

                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DD_MM_YYYY, Locale.ENGLISH);
                lastUpdt.setText(getResources().getString(R.string.systems_last_updated).concat(" "+sdf.format(app.get_id().getDate())));

                overAllLines += app.getNcloc();
                overallIssues += app.getOpenIssues();
                overallBugs += app.getIssuesBug();

            }
        }

        on.setOverallLines(compressNumbers(overAllLines));
        on.setOverallIssues(compressNumbers(overallIssues));
        on.setOverallBugs(compressNumbers(overallBugs));

        on.setOverallLinesNumber(overAllLines);
        on.setOverallIssuesNumber(overallIssues);
        on.setOverallBugsNumber(overallBugs);

        return on;
    }

    protected String compressNumbers(float number) {

        DecimalFormat df = new DecimalFormat("#.#");
        String stringNumber;

        if(number > 1000000){
            stringNumber = df.format((float) number/1000000) + "M";
            return stringNumber.replaceAll(",", ".");
        } else if (number > 1000){
            stringNumber = df.format((float) number/1000) + "k";
            return stringNumber.replaceAll(",", ".");
        } else {
            return (number + "".replaceAll(",", "."));
        }
    }

    protected static String removeLastChar(Object str) {

        String number = str.toString();

        if(number.length() < 2){
            return number;
        } else if (number.contains("-")){
            return "0";
        } else if(!number.contains(".*\\c.*")){
            return number.substring(0, number.length() - 1);
        } else {
            return number;
        }
    }

    protected List<ScoreResults> getApplicationList(ScoreResults[] apps) {
        return new ArrayList<>(Arrays.asList(apps));
    }

}
