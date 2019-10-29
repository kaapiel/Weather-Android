package br.com.cielo.weather.ui.customs;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.cielo.weather.model.mongo.ScoreHistoryUnit;
import br.com.cielo.weather.util.Constants;

public class LineValueFormatter extends ValueFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public LineValueFormatter() {
        mFormat = new DecimalFormat("#.##");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DD_MM_YYYY, Locale.ENGLISH);
        return "Score".concat("\n")
                .concat(mFormat.format(value)
                        .concat("\n")
                        .concat(sdf.format(((ScoreHistoryUnit)entry.getData()).getDate()))); // e.g. append a dollar-sign
    }

}