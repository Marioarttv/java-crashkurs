package de.phl.crashkurs.k04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 4, Aufgabe 2: Library.getName, getBookCount, addBook")
class LibraryTest {

    @Test
    @DisplayName("getName gibt den Namen zurück")
    void name() {
        Library l = new Library("Stadtbücherei");
        assertEquals("Stadtbücherei", l.getName(), "Nach new Library(\"Stadtbücherei\") muss getName() \"Stadtbücherei\" zurückgeben. Gibst du mit return das Attribut name zurück?");
    }

    @Test
    @DisplayName("Eine neue Bibliothek hat 0 Bücher")
    void anfangsNull() {
        Library l = new Library("Stadtbücherei");
        assertEquals(0, l.getBookCount(), "Eine neue Bibliothek hat 0 Bücher. getBookCount() muss das Attribut bookCount zurückgeben.");
    }

    @Test
    @DisplayName("addBook erhöht die Zahl der Bücher um 1")
    void einBuch() {
        Library l = new Library("Stadtbücherei");
        l.addBook();
        assertEquals(1, l.getBookCount(), "Nach einem Aufruf von addBook() muss getBookCount() 1 zurückgeben. Erhöhst du in addBook() das Attribut bookCount um 1?");
    }

    @Test
    @DisplayName("Dreimal addBook: 3 Bücher")
    void dreiBuecher() {
        Library l = new Library("Stadtbücherei");
        l.addBook();
        l.addBook();
        l.addBook();
        assertEquals(3, l.getBookCount(), "Nach drei Aufrufen von addBook() muss getBookCount() 3 zurückgeben.");
    }

    @Test
    @DisplayName("Zwei Bibliotheken zählen getrennt")
    void zweiBibliotheken() {
        Library a = new Library("Stadtbücherei");
        Library b = new Library("Schulbibliothek");
        a.addBook();
        a.addBook();
        b.addBook();
        assertEquals(2, a.getBookCount(), "Die erste Bibliothek hat zwei Bücher bekommen. Jedes Objekt hat sein eigenes Attribut bookCount.");
        assertEquals(1, b.getBookCount(), "Die zweite Bibliothek hat ein Buch bekommen. Jedes Objekt hat sein eigenes Attribut bookCount.");
    }
}
