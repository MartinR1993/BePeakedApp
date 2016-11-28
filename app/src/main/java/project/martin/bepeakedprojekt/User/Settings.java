package project.martin.bepeakedprojekt.User;

import java.util.HashMap;

/**
 * Created by Lasse on 27-11-2016.
 */
public class Settings {
    public static final int US_METRIC = 0;
    public static final int US_IMPLERIAL = 1;

    public static final String USTAG_WEIGHT = "weight";
    public static final String USTAG_LENGTH = "lenght";
    public static final String USTAG_VOLUME = "volume";
    public static final String USTAG_TEMPRATURE = "temp";

    public static int unitSystem = US_METRIC;

    private static final HashMap<String, String> metricSystem = new HashMap<String, String>() {{
        put(USTAG_WEIGHT,"kg");
        put(USTAG_LENGTH,"m");
        put(USTAG_VOLUME,"l");
        put(USTAG_TEMPRATURE,"C");
    }};

    private static final HashMap<String, String> imperialSystem = new HashMap<String, String>() {{
        put(USTAG_WEIGHT,"lb");
        put(USTAG_LENGTH,"ft");
        put(USTAG_VOLUME,"pt");
        put(USTAG_TEMPRATURE,"F");
    }};

    public void setUnitSystem(int systemID) {
        unitSystem = systemID;
    }

    public static final String getUnit(String unitTag) {
        final HashMap<String, String> us;

        switch (unitSystem) {
            default:
            case US_METRIC: {
                us = metricSystem;
                break;
            }
            case US_IMPLERIAL: {
                us = imperialSystem;
                break;
            }
        }

        return us.get(unitTag);
    }
}
