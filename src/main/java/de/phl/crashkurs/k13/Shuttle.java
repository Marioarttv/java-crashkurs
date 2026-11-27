package de.phl.crashkurs.k13;

/**
 * Kapitel 13, Aufgabe 5: ein E-Shuttle zwischen Bahnhof und Festivalgelände.
 * Der Shuttle hat seats Sitzplätze und einen Akku (battery, in Prozent).
 * Jede Fahrt verbraucht 15 Prozent Akku. trips zählt, wie oft der Shuttle schon gefahren ist.
 */
public class Shuttle {

    private int seats;
    private int battery;
    private int trips = 0;

    public Shuttle(int seats, int battery) {
        this.seats = seats;
        this.battery = battery;
    }

    public int getBattery() {
        return battery;
    }

    public int getTrips() {
        return trips;
    }

    /**
     * Bringt wartende Leute vom Bahnhof zum Gelände.
     * Der Shuttle fährt, solange noch jemand wartet und der Akku für eine Fahrt reicht (mindestens 15 Prozent).
     * Bei jeder Fahrt nimmt er bis zu seats Leute mit. Warten weniger Leute, nimmt er alle mit.
     * Danach sinkt der Akku um 15, und trips wird um 1 größer.
     *
     * @param waiting die Zahl der Leute, die am Bahnhof warten
     * @return die Zahl der Leute, die danach noch warten
     */
    public int run(int waiting) {
        // TODO: Ersetze die nächste Zeile durch deinen Code. Die Zeile darüber bleibt unverändert.
        return 0;
    }
}
