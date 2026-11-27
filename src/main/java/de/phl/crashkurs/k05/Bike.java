package de.phl.crashkurs.k05;

/**
 * Ein E-Bike im Fahrradverleih. Vorgegeben: Diese Datei änderst du nicht.
 */
public class Bike {

    private final String number;
    private int battery;

    /**
     * Baut ein neues Rad.
     *
     * @param number die Nummer des Rads, zum Beispiel "B-12"
     * @param battery der Akkustand in Prozent
     */
    public Bike(final String number, final int battery) {
        this.number = number;
        this.battery = battery;
    }

    public String getNumber() {
        return number;
    }

    public int getBattery() {
        return battery;
    }

    /**
     * Die Reichweite: wie viele ganze Kilometer das Rad mit seinem Akku noch fährt.
     * Für einen Kilometer braucht es 2 Prozent Akku. Bleibt beim Teilen ein Rest, wird abgerundet.
     *
     * @return die Reichweite in Kilometern
     */
    public int getRange() {
        return battery / 2;
    }

    /**
     * Lädt den Akku auf.
     *
     * @param percent um wie viel Prozent der Akkustand steigt
     */
    public void charge(final int percent) {
        battery = battery + percent;
    }
}
