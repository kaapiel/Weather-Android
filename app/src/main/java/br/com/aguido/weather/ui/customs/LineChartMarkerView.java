package br.com.aguido.weather.ui.customs;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import br.com.aguido.weather.R;
import br.com.aguido.weather.util.Constants;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Gabriel Aguido Fraga
 */
public class LineChartMarkerView extends MarkerView {

    private TextView tvContent;
    private DecimalFormat df;

    public LineChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        tvContent = findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(getContext().getString(R.string.score_uppercase)
                .concat("\t")
                .concat(df.format(e.getY()))
                .concat("\t").concat(" | ").concat("\t")
                .concat(new SimpleDateFormat(Constants.DD_MM_YYYY, Locale.ENGLISH).format((e.getData()))));
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}