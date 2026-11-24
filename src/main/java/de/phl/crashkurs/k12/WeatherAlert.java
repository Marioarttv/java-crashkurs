package de.phl.crashkurs.k12;

/**
 * Kapitel 12, Aufgabe 3: Warnungen der Wetterstation.
 * In dieser Datei stecken zwei Denkfehler. Sie kompiliert, aber zwei Tests sind rot.
 * Finde die Fehler und behebe sie. Die Köpfe der Methoden bleiben unverändert.
 */
public class WeatherAlert {

    /**
     * Bestimmt die Warnstufe aus der Windgeschwindigkeit in km/h.
     * Über 90: STORM. Über 50 (bis einschließlich 90): WIND. Sonst: NONE.
     *
     * @param windSpeed die Windgeschwindigkeit in km/h
     * @return die Warnstufe
     */
    public static Warning forWind(int windSpeed) {
        if (windSpeed > 50) {
            return Warning.WIND;
        } else if (windSpeed > 90) {
            return Warning.STORM;
        } else {
            return Warning.NONE;
        }
    }

    /**
     * Gibt den Rat zur Warnstufe zurück.
     * NONE: "Alles ruhig". WIND: "Schirm zu Hause lassen". STORM: "Drinnen bleiben".
     *
     * @param warning die Warnstufe
     * @return der Rat
     */
    public static String advice(Warning warning) {
        String text = "";
        switch (warning) {
            case NONE:
                text = "Alles ruhig";
                break;
            case WIND:
                text = "Schirm zu Hause lassen";
            case STORM:
                text = "Drinnen bleiben";
                break;
        }
        return text;
    }
}
