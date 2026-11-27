package de.phl.crashkurs.k05;

/**
 * Ein Tarif im Fahrradverleih. Vorgegeben: Diese Datei änderst du nicht.
 */
public class Tariff {

    private final int unlockFee;
    private final int pricePerMinute;

    /**
     * Baut einen neuen Tarif.
     *
     * @param unlockFee was das Entsperren eines Rads kostet, in Cent
     * @param pricePerMinute was eine Minute Fahrt kostet, in Cent
     */
    public Tariff(final int unlockFee, final int pricePerMinute) {
        this.unlockFee = unlockFee;
        this.pricePerMinute = pricePerMinute;
    }

    /**
     * Rechnet den Preis für eine Fahrt aus: einmal Entsperren und dazu jede Minute.
     *
     * @param minutes wie lange die Fahrt dauert
     * @return der Preis in Cent
     */
    public int priceFor(final int minutes) {
        return unlockFee + minutes * pricePerMinute;
    }
}
