package de.phl.crashkurs.k07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 7, Aufgabe 1: Exam.hasPassed")
class ExamTest {

    @Test
    @DisplayName("62 Punkte: bestanden")
    void mehrAlsFuenfzigIstBestanden() {
        assertTrue(Exam.hasPassed(62), "Mit 62 Punkten ist die Klausur bestanden. hasPassed(62) muss true (wahr) zurückgeben.");
    }

    @Test
    @DisplayName("49 Punkte: nicht bestanden")
    void wenigerAlsFuenfzigIstNichtBestanden() {
        assertFalse(Exam.hasPassed(49), "Mit 49 Punkten ist die Klausur nicht bestanden. hasPassed(49) muss false (falsch) zurückgeben.");
    }

    @Test
    @DisplayName("genau 50 Punkte: bestanden")
    void genauFuenfzigIstBestanden() {
        assertTrue(Exam.hasPassed(50), "Genau 50 Punkte reichen zum Bestehen. Prüfe deine Bedingung: Verwendest du >= statt >?");
    }
}
