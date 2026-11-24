package de.phl.crashkurs.k11;

/**
 * Kapitel 11, Aufgabe 1: ein Parkhaus, das ungültige Werte ablehnt.
 * Das Parkhaus merkt sich, wie viele Plätze noch frei sind (freeSpots).
 */
public class ParkingGarage {

    private int freeSpots;

    public ParkingGarage(int freeSpots) {
        this.freeSpots = freeSpots;
    }

    public int getFreeSpots() {
        return freeSpots;
    }

    /**
     * Berechnet die Parkgebühr: 2 Euro pro Stunde.
     * Diese Methode ist fertig. Sie zeigt dir, wie eine Prüfung am Anfang einer Methode aussieht.
     * Du brauchst sie in Aufgabe 2.
     *
     * @param hours die Parkdauer in Stunden
     * @return die Gebühr in Euro
     */
    public static int fee(int hours) {
        if (hours < 1) {
            throw new IllegalArgumentException("Parkdauer muss mindestens 1 Stunde sein");
        }
        return hours * 2;
    }

    /**
     * Lässt Autos ins Parkhaus fahren.
     * Prüfe zuerst die Anzahl der Autos (cars):
     * Kleiner als 1: wirf eine IllegalArgumentException mit dem Text "Anzahl muss mindestens 1 sein".
     * Größer als freeSpots: wirf eine IllegalArgumentException mit dem Text "Nicht genug freie Plätze".
     * Sonst: ziehe cars von freeSpots ab.
     *
     * @param cars die Anzahl der Autos, die einfahren wollen
     */
    public void enter(int cars) {
        // TODO: Schreib deinen Code hier zwischen die geschweiften Klammern. Die Zeile darüber bleibt unverändert.
    }
}
