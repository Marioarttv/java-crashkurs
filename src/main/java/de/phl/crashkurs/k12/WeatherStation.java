package de.phl.crashkurs.k12;

/**
 * Kapitel 12, Aufgabe 2: Auswertungen der Wetterstation.
 * In dieser Datei stecken zwei Denkfehler. Sie kompiliert, aber zwei Tests sind rot.
 * Finde die Fehler und behebe sie. Die Köpfe der Methoden bleiben unverändert.
 */
public class WeatherStation {

    /**
     * Gibt die höchste Temperatur zurück. Das Array hat mindestens einen Wert.
     *
     * @param temps die gemessenen Temperaturen
     * @return der größte Wert im Array
     */
    public static int highest(int[] temps) {
        int max = 0;
        for (int i = 0; i < temps.length; i++) {
            if (temps[i] > max) {
                max = temps[i];
            }
        }
        return max;
    }

    /**
     * Zählt die Frosttage: Tage mit 0 Grad oder weniger.
     *
     * @param temps die Temperaturen, eine je Tag
     * @return die Zahl der Frosttage
     */
    public static int countFrostDays(int[] temps) {
        int count = 0;
        for (int i = 0; i <= temps.length; i++) {
            if (temps[i] <= 0) {
                count++;
            }
        }
        return count;
    }
}
