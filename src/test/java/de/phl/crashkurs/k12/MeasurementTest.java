package de.phl.crashkurs.k12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 12, Aufgabe 1: Measurement")
class MeasurementTest {

    @Test
    @DisplayName("Der Konstruktor speichert Ort und Temperatur")
    void konstruktorSpeichert() {
        Measurement m = new Measurement("Ludwigsburg", 7);
        assertEquals("Ludwigsburg", m.getPlace(), "Nach new Measurement(\"Ludwigsburg\", 7) muss getPlace() \"Ludwigsburg\" liefern. Kommt der Wert im Attribut an?");
        assertEquals(7, m.getTemperature(), "Nach new Measurement(\"Ludwigsburg\", 7) muss getTemperature() 7 liefern.");
    }

    @Test
    @DisplayName("Bei 0 Grad herrscht Frost")
    void nullGradIstFrost() {
        assertTrue(new Measurement("Ludwigsburg", 0).isFrost(), "Bei genau 0 Grad muss isFrost() true liefern. Laut Beschreibung zählt 0 Grad mit.");
    }

    @Test
    @DisplayName("Bei -4 Grad herrscht Frost")
    void minusVierIstFrost() {
        assertTrue(new Measurement("Ludwigsburg", -4).isFrost(), "Bei -4 Grad muss isFrost() true liefern.");
    }

    @Test
    @DisplayName("Bei 5 Grad herrscht kein Frost")
    void fuenfGradKeinFrost() {
        assertFalse(new Measurement("Ludwigsburg", 5).isFrost(), "Bei 5 Grad muss isFrost() false liefern.");
    }
}
