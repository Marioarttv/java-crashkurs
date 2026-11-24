package de.phl.crashkurs.k04;

/**
 * Kapitel 4, Aufgabe 1: den Konstruktor füllen.
 * Ein Buch in der Bibliothek hat einen Titel, eine Seitenzahl und ist ausgeliehen oder nicht.
 */
public class Book {

    private String title;
    private int pages;
    private boolean borrowed;

    /**
     * Baut ein neues Buch. Am Anfang ist es nicht ausgeliehen.
     *
     * @param title der Titel des Buchs
     * @param pages die Zahl der Seiten
     */
    public Book(String title, int pages) {
        // TODO: Speichere die beiden Parameter in den Attributen. Die Zeile darüber bleibt unverändert.
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrow() {
        borrowed = true;
    }
}
