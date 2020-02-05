package br.com.aguido.weather.ui.customs;

import com.github.mikephil.charting.formatter.ValueFormatter;

public class BarValueFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return "" + (int)value;
    }
}