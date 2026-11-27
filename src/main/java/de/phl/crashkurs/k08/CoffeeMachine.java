package de.phl.crashkurs.k08;

/**
 * Kapitel 8, Aufgabe 3: ein Kaffeeautomat, auf dem eine Größe gewählt ist.
 */
public class CoffeeMachine {

    private Size size;

    public CoffeeMachine(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    /**
     * Gibt den Preis eines Kaffees in Cent zurück.
     * Grundpreis nach der Größe: SMALL 150, MEDIUM 200, LARGE 250.
     * Mit eigenem Becher (ownCup ist true) kostet der Kaffee 20 Cent weniger.
     *
     * @param ownCup true, wenn ein eigener Becher mitgebracht wird
     * @return der Preis in Cent
     */
    public int price(boolean ownCup) {
        // TODO: Schreib deinen Code hier. Die Zeile mit "public int price" bleibt unverändert.
        return 0;
    }
}
