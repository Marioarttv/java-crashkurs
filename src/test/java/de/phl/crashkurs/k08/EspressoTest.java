package de.phl.crashkurs.k08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 8, Aufgabe 1: Espresso.shots")
class EspressoTest {

    @Test
    @DisplayName("LARGE: 2 Portionen")
    void grossHatZwei() {
        assertEquals(2, Espresso.shots(Size.LARGE), "Ein großer Becher (LARGE) bekommt 2 Portionen. Gibt es bei dir ein case LARGE, das 2 speichert? Steht danach ein break? Ohne break läuft auch default und macht aus 2 wieder 1.");
    }

    @Test
    @DisplayName("SMALL: 1 Portion")
    void kleinHatEine() {
        assertEquals(1, Espresso.shots(Size.SMALL), "Ein kleiner Becher (SMALL) bekommt 1 Portion. Das erledigt der default-Fall.");
    }

    @Test
    @DisplayName("MEDIUM: 1 Portion")
    void mittelHatEine() {
        assertEquals(1, Espresso.shots(Size.MEDIUM), "Ein mittlerer Becher (MEDIUM) bekommt 1 Portion. Das erledigt der default-Fall.");
    }

    @Test
    @DisplayName("Die Methode benutzt switch")
    void benutztSwitch() throws IOException {
        assertTrue(Quelltext.benutzt("Espresso.java", "switch"), "In der Methode shots soll ein switch stehen, kein if.");
    }
}
