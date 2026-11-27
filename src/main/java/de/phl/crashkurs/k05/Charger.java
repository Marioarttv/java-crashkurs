package de.phl.crashkurs.k05;

/**
 * Eine Ladesäule für E-Bikes. Vorgegeben: Diese Datei änderst du nicht.
 */
public class Charger {

    private final int percentPerMinute;

    /**
     * Baut eine neue Ladesäule.
     *
     * @param percentPerMinute um wie viel Prozent der Akku pro Minute steigt
     */
    public Charger(final int percentPerMinute) {
        this.percentPerMinute = percentPerMinute;
    }

    /**
     * Rechnet aus, wie viele ganze Minuten ein Rad laden kann, ohne über 100 Prozent zu kommen.
     * Geht die Rechnung genau auf, ist der Akku danach voll.
     *
     * @param bike das Rad
     * @return die Minuten bis voll
     */
    public int minutesUntilFull(final Bike bike) {
        return (100 - bike.getBattery()) / percentPerMinute;
    }

    /**
     * Lädt ein Rad für die gegebene Zeit: Der Akku steigt um percentPerMinute pro Minute.
     *
     * @param bike das Rad
     * @param minutes wie lange geladen wird
     */
    public void plugIn(final Bike bike, final int minutes) {
        bike.charge(minutes * percentPerMinute);
    }
}
