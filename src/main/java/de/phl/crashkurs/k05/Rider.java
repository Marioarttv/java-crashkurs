package de.phl.crashkurs.k05;

/**
 * Eine Person, die Räder ausleiht. Vorgegeben: Diese Datei änderst du nicht.
 */
public class Rider {

    private final String name;
    private int credit;

    /**
     * Baut eine neue Person mit Guthaben.
     *
     * @param name der Name
     * @param credit das Guthaben in Cent
     */
    public Rider(final String name, final int credit) {
        this.name = name;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    /**
     * Fährt mit einem Rad. Das Rad verliert Akku (1 Prozent pro Minute),
     * das Guthaben sinkt um 10 Cent pro Minute.
     *
     * @param bike das Rad
     * @param minutes wie lange gefahren wird
     */
    public void ride(final Bike bike, final int minutes) {
        bike.drive(minutes);
        credit = credit - minutes * 10;
    }

    public String getInfo() {
        return name + ", Guthaben " + credit + " Cent";
    }
}
