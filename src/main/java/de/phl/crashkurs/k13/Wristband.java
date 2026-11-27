package de.phl.crashkurs.k13;

/**
 * Kapitel 13, Aufgabe 1: Bändchen, die bis zu einem Festivaltag gelten.
 * Das Festival dauert drei Tage. Jedes Bändchen gilt bis zu einem bestimmten Tag (lastDay):
 * Ein Bändchen nur für den ersten Tag hat lastDay 1, ein Bändchen für alle drei Tage hat lastDay 3.
 * Welcher Festivaltag gerade ist, steht in today. Diesen Wert gibt es nur einmal, er gilt für alle Bändchen.
 */
public class Wristband {

    private static int today = 1;

    private String holder;
    private int lastDay;

    public Wristband(String holder, int lastDay) {
        this.holder = holder;
        this.lastDay = lastDay;
    }

    /**
     * Das Festival beginnt: today steht wieder auf 1.
     * Diese Methode ist fertig. Die Tests rufen sie vor jedem Test auf.
     */
    public static void startFestival() {
        today = 1;
    }

    /**
     * Gibt den aktuellen Festivaltag zurück. Diese Methode ist fertig.
     */
    public static int getToday() {
        return today;
    }

    public String getHolder() {
        return holder;
    }

    /**
     * Der nächste Festivaltag beginnt: today wird um 1 größer.
     * Diese Methode ist static. Du rufst sie ohne Objekt auf, mit Wristband.nextDay().
     */
    public static void nextDay() {
        // TODO: Schreib deinen Code hier zwischen die geschweiften Klammern. Die Zeile darüber bleibt unverändert.
    }

    /**
     * Gibt true zurück, wenn dieses Bändchen heute noch gilt: today ist höchstens lastDay.
     * Sonst gibt die Methode false zurück.
     */
    public boolean isValid() {
        // TODO: Ersetze die nächste Zeile durch deinen Code. Die Zeile darüber bleibt unverändert.
        return false;
    }
}
