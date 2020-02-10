package br.com.aguido.weather.appManagement;

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
import java.util.Random;
import java.util.stream.Stream;

import br.com.aguido.weather.R;
import br.com.aguido.weather.model.mongo.ScoreHistory;
import br.com.aguido.weather.model.mongo.ScoreHistoryUnit;
import br.com.aguido.weather.model.mongo.ScoreResults;
import br.com.aguido.weather.model.mongo.TestsCase;
import br.com.aguido.weather.model.mongo._id;
import br.com.aguido.weather.model.numbers.OverallNumbers;
import br.com.aguido.weather.util.Constants;
import br.com.aguido.weather.util.Util;

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

    protected ScoreResults[] generateMockScoreResults() {


        ArrayList<ScoreResults> srs = new ArrayList<>();

        srs.add(generateMockscoreResult());
        srs.add(generateMockscoreResult());
        srs.add(generateMockscoreResult());
        srs.add(generateMockscoreResult());

        ScoreResults[] scoreResults = new ScoreResults[srs.size()];
        scoreResults = srs.toArray(scoreResults);

        return scoreResults;
    }

    private ScoreResults generateMockscoreResult() {

        ScoreResults sr = new ScoreResults();
        _id id = new _id();
        id.setDate(new Random().nextLong());
        sr.setApplicationName("Mock app");
        sr.set_id(id);
        TestsCase tc = new TestsCase();
        tc.setNumberLong(new Random().nextLong());
        sr.setTestsCase(tc);
        sr.setAverageAge(new Random().nextInt(5));
        sr.setAverageTimeFix(new Random().nextInt(5));
        sr.setCoverage(new Random().nextInt(100-60)+60);
        sr.setDefectsDensity(new Random().nextInt(100-60)+60);
        sr.setDuplicationDensity(new Random().nextInt(5));
        sr.setEffectiveness(new Random().nextInt(5));
        sr.setExecutionsPassed(new Random().nextInt(1000-500)+500);
        sr.setIssuesBlocker(new Random().nextInt(10));
        sr.setIssuesBug(new Random().nextInt(20));
        sr.setIssuesCodeSmell(new Random().nextInt(400));
        sr.setIssuesCritical(new Random().nextInt(50));
        sr.setIssuesMajor(new Random().nextInt(500));
        sr.setIssuesVulnerabilities(new Random().nextInt(600));
        sr.setNcloc(new Random().nextInt(10000-1000)+1000);
        sr.setOpenDefects(new Random().nextInt(50));
        sr.setOpenIssues(new Random().nextInt(100));
        sr.setScore(new Random().nextInt(5));
        sr.setScoreAverageAge(new Random().nextInt(5));
        sr.setScoreAverageTimeFix(new Random().nextInt(5));
        sr.setScoreCoverage(new Random().nextInt(5));
        sr.setScoreDuplicationDensity(new Random().nextInt(5));
        sr.setScoreEffectiveness(new Random().nextInt(5));
        sr.setScoreOpenDefects(new Random().nextInt(5));
        sr.setScoreOpenIssues(new Random().nextInt(5));
        sr.setScoreSuccessRate(new Random().nextInt(5));
        sr.setScoreTotalDefects(new Random().nextInt(5));
        sr.setScoreTotalIssues(new Random().nextInt(5));
        sr.setSuccessRate(new Random().nextInt(5));
        sr.setSysIcon(new Util().getRandomIcon());
        sr.setTotalDefects(new Random().nextInt(100));
        sr.setTotalIssues(new Random().nextInt(200));
        sr.setTotalProblems(new Random().nextInt(300));
        sr.setTotalTestExecutions(new Random().nextInt(4000-500)+500);

        return sr;
    }

    protected ScoreHistory generateMockScoreHistory() {

        ScoreHistory sh = new ScoreHistory();
        sh.setSysName("Mock App");
        sh.setScoreHistoryList(generateScoreHistoryUnits());

        return sh;
    }

    protected List<ScoreHistoryUnit> generateScoreHistoryUnits() {

        List<ScoreHistoryUnit> shus = new ArrayList<>();
        shus.add(generateScoreHistoryUnit());
        shus.add(generateScoreHistoryUnit());
        shus.add(generateScoreHistoryUnit());
        shus.add(generateScoreHistoryUnit());
        shus.add(generateScoreHistoryUnit());

        return shus;
    }

    protected ScoreHistoryUnit generateScoreHistoryUnit() {

        ScoreHistoryUnit shu = new ScoreHistoryUnit();
        shu.setDate(new Random().nextLong());
        shu.setScore(new Random().nextInt(5));
        return shu;
    }

}
