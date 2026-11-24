package de.phl.crashkurs.k08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 8, Aufgabe 2: Cup.capacity")
class CupTest {

    @Test
    @DisplayName("SMALL: 200 ml")
    void klein() {
        assertEquals(200, new Cup(Size.SMALL).capacity(), "Ein kleiner Becher (SMALL) fasst 200 ml. Steht am Ende von case SMALL ein break?");
    }

    @Test
    @DisplayName("MEDIUM: 300 ml")
    void mittel() {
        assertEquals(300, new Cup(Size.MEDIUM).capacity(), "Ein mittlerer Becher (MEDIUM) fasst 300 ml. Gibt es ein case MEDIUM, das mit break endet?");
    }

    @Test
    @DisplayName("LARGE: 400 ml")
    void gross() {
        assertEquals(400, new Cup(Size.LARGE).capacity(), "Ein großer Becher (LARGE) fasst 400 ml. Gibt es ein case LARGE?");
    }

    @Test
    @DisplayName("Die Methode benutzt switch")
    void benutztSwitch() throws IOException {
        assertTrue(Quelltext.benutzt("Cup.java", "switch"), "In der Methode capacity soll ein switch über das Attribut size stehen.");
    }
}
