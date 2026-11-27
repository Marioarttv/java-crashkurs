package de.phl.crashkurs.k09;

/**
 * Ein Handy mit Akku (battery, in Prozent).
 * Diese Datei änderst du nicht.
 */
public class Phone {

    private int battery;

    public Phone(int battery) {
        this.battery = battery;
    }

    public int getBattery() {
        return battery;
    }

    /**
     * Spielt ein Video ab. Ein Video kostet 20 Prozent Akku.
     */
    public void playVideo() {
        battery -= 20;
    }

    /**
     * Lädt eine Viertelstunde: 25 Prozent mehr Akku, aber nie mehr als 100.
     */
    public void charge() {
        battery += 25;
        if (battery > 100) {
            battery = 100;
        }
    }
}
