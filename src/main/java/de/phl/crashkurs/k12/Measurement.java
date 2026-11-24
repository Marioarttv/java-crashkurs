package de.phl.crashkurs.k12;

/**
 * Kapitel 12, Aufgabe 1: ein Messwert der Wetterstation.
 * In dieser Datei stecken zwei Denkfehler. Sie kompiliert, aber zwei Tests sind rot.
 * Finde die Fehler und behebe sie. Die Köpfe der Methoden bleiben unverändert.
 */
public class Measurement {

    private String place;
    private int temperature;

    /**
     * Erzeugt einen Messwert und speichert Ort und Temperatur in den Attributen.
     *
     * @param place       der Ort der Messung
     * @param temperature die Temperatur in Grad Celsius
     */
    public Measurement(String place, int temperature) {
        place = place;
        this.temperature = temperature;
    }

    public String getPlace() {
        return place;
    }

    public int getTemperature() {
        return temperature;
    }

    /**
     * Frost herrscht bei 0 Grad und darunter.
     *
     * @return true, wenn die Temperatur 0 Grad oder weniger ist
     */
    public boolean isFrost() {
        return temperature < 0;
    }
}
