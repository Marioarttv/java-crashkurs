package de.phl.crashkurs.k04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Kapitel 4, Aufgabe 1: Konstruktor von Book")
class BookTest {

    @Test
    @DisplayName("Der Konstruktor speichert den Titel")
    void titelGespeichert() {
        Book b = new Book("Momo", 320);
        assertEquals("Momo", b.getTitle(), "Nach new Book(\"Momo\", 320) muss getTitle() \"Momo\" zurückgeben. Schreibst du im Konstruktor this.title = title; ?");
    }

    @Test
    @DisplayName("Der Konstruktor speichert die Seitenzahl")
    void seitenGespeichert() {
        Book b = new Book("Momo", 320);
        assertEquals(320, b.getPages(), "Nach new Book(\"Momo\", 320) muss getPages() 320 zurückgeben. Schreibst du im Konstruktor this.pages = pages; ?");
    }

    @Test
    @DisplayName("Zwei Bücher behalten ihre eigenen Werte")
    void zweiBuecher() {
        Book a = new Book("Momo", 320);
        Book b = new Book("Krabat", 288);
        assertEquals("Momo", a.getTitle(), "Das erste Buch muss \"Momo\" heißen, auch nachdem ein zweites Buch gebaut wurde.");
        assertEquals("Krabat", b.getTitle(), "Das zweite Buch muss \"Krabat\" heißen.");
        assertEquals(288, b.getPages(), "Das zweite Buch muss 288 Seiten haben.");
    }

    @Test
    @DisplayName("Ein neues Buch ist nicht ausgeliehen")
    void nichtAusgeliehen() {
        Book b = new Book("Momo", 320);
        assertFalse(b.isBorrowed(), "Ein neues Buch darf nicht ausgeliehen sein. Setze borrowed im Konstruktor nicht auf true.");
    }
}
