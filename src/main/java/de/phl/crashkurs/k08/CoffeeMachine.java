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
     * Verkauft einen Kaffee in der gewählten Größe.
     * Legt nach der Größe Preis (in Cent) und Namen fest:
     * SMALL: 150, "kleiner Kaffee". MEDIUM: 200, "mittlerer Kaffee". LARGE: 250, "großer Kaffee".
     * Bezahlt den Preis mit der Karte (card.pay) und gibt den Namen zurück.
     *
     * @param card die Kaffeekarte, mit der bezahlt wird
     * @return der Name des Getränks
     */
    public String sell(CoffeeCard card) {
        // TODO: Schreib deinen Code hier. Die Zeile mit "public String sell" bleibt unverändert.
        return "";
    }
}
