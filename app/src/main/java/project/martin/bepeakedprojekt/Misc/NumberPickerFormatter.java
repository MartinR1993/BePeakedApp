package project.martin.bepeakedprojekt.Misc;

import android.widget.NumberPicker;

/**
 * Created by Lasse on 28-11-2016.
 */
public class NumberPickerFormatter implements NumberPicker.Formatter {
    private final int increment;

    public NumberPickerFormatter() {
        this(1);
    }

    public NumberPickerFormatter(int increment) {
        this.increment = increment;
    }

    @Override
    public String format(int value) {
        return "" + (value * increment);
    }
}
