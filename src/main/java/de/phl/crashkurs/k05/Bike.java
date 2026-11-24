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
     * Das Rad fährt: Der Akku sinkt um 1 Prozent pro Minute.
     *
     * @param minutes wie lange gefahren wird
     */
    public void drive(final int minutes) {
        battery = battery - minutes;
    }

    public String getInfo() {
        return "Rad " + number + ", Akku " + battery + " Prozent";
    }
}
