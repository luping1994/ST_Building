package net.suntrans.building.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by Looney on 2018/3/15.
 */

public class WeekDataxAxisValueFormatter implements IAxisValueFormatter {

    private List<String> formatter;


    public WeekDataxAxisValueFormatter(List<String> formatter) {
        this.formatter = formatter;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return formatter.get((int) value);
    }
}
