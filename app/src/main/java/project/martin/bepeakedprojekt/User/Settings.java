package project.martin.bepeakedprojekt.User;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Lasse on 27-11-2016.
 */
public class Settings
{
    public enum UnitSystem {
        METRIC, IMPERIAL;
    }

    public enum Language {
        ENGLISH, DANISH;
    }

    public static final String USTAG_WEIGHT = "weight";
    public static final String USTAG_LENGTH = "lenght";
    public static final String USTAG_VOLUME = "volume";
    public static final String USTAG_TEMPRATURE = "temp";

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

    public static UnitSystem unitSystemType = UnitSystem.METRIC;
    private static HashMap<String, String> unitSystem = metricSystem;

    public static void setUnitSystem(UnitSystem system) {
        unitSystemType = system;

        switch (unitSystemType) {
            default:
            case METRIC: {
                unitSystem = metricSystem;
                break;
            }
            case IMPERIAL: {
                unitSystem = imperialSystem;
                break;
            }
        }
    }

    public static UnitSystem getUnitSystem() {
        return unitSystemType;
    }

    public static final String getUnit(String unitTag) {
        return unitSystem.get(unitTag);
    }

    public static void setLanguage(Resources resources, Language lang) {
        String isoCode;

        switch (lang) {
            default:
            case ENGLISH: {
                isoCode = "en";
                break;
            }
            case DANISH: {
                isoCode = "da";
                break;
            }
        }

        Configuration config = new Configuration();
        config.setLocale(new Locale(isoCode));

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
