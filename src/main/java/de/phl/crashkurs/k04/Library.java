package de.phl.crashkurs.k04;

/**
 * Kapitel 4, Aufgabe 2: Getter und eine Methode, die ein Attribut ändert.
 * Eine Bibliothek hat einen Namen und zählt, wie viele Bücher sie hat.
 */
public class Library {

    private String name;
    private int bookCount;

    /**
     * Baut eine neue Bibliothek. Am Anfang hat sie keine Bücher.
     *
     * @param name der Name der Bibliothek
     */
    public Library(String name) {
        this.name = name;
        this.bookCount = 0;
    }

    /**
     * Gibt den Namen der Bibliothek zurück.
     */
    public String getName() {
        // TODO: Ersetze die nächste Zeile. Die Methode soll das Attribut name zurückgeben.
        return "";
    }

    /**
     * Gibt zurück, wie viele Bücher die Bibliothek hat.
     */
    public int getBookCount() {
        // TODO: Ersetze die nächste Zeile. Die Methode soll das Attribut bookCount zurückgeben.
        return -1;
    }

    /**
     * Ein neues Buch kommt dazu: bookCount steigt um 1.
     */
    public void addBook() {
        // TODO: Erhöhe das Attribut bookCount um 1.
    }
}
