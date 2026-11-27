package de.phl.crashkurs.k13;

/**
 * Kapitel 13, Aufgabe 3: die Merch-Stände des Festivals.
 * Auf dem Gelände verkaufen mehrere Stände T-Shirts. Jeder Stand ist ein MerchStand-Objekt.
 * In dieser Datei stecken zwei Denkfehler. Sie kompiliert, aber zwei Tests sind rot.
 * Finde die Fehler und behebe sie. Die Köpfe der Methoden bleiben unverändert.
 */
public class MerchStand {

    /** So viele T-Shirts haben alle Stände zusammen verkauft. */
    private int totalSold = 0;

    /** So viele T-Shirts hat dieser eine Stand verkauft. */
    private int sold = 0;

    /**
     * Verkauft T-Shirts an diesem Stand.
     * Sie zählen bei diesem Stand und bei allen Ständen zusammen.
     *
     * @param shirts die Zahl der verkauften T-Shirts
     */
    public void sell(int shirts) {
        sold = sold + shirts;
        totalSold = totalSold + shirts;
    }

    /**
     * Gibt zurück, wie viele T-Shirts dieser Stand verkauft hat.
     */
    public int getSold() {
        return sold;
    }

    /**
     * Gibt zurück, wie viele T-Shirts alle Stände zusammen verkauft haben.
     */
    public int getTotalSold() {
        return totalSold;
    }

    /**
     * Prüft, ob ein Motiv ausverkauft ist.
     * Das Array stock enthält für jedes Motiv, wie viele T-Shirts davon noch im Lager liegen.
     * Gibt true zurück, wenn mindestens ein Motiv nur noch 0 T-Shirts hat. Sonst false.
     *
     * @param stock der Bestand je Motiv
     * @return true, wenn ein Motiv ausverkauft ist
     */
    public static boolean hasSoldOut(int[] stock) {
        for (int count : stock) {
            if (count == 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
