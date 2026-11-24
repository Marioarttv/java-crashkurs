package de.phl.crashkurs.k10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 10, Aufgabe 3: SeatingPlan.createStudents")
class SeatingPlanTest {

    private static List<Student> liste() {
        return Pruefen.mitZeitlimit(SeatingPlan::createStudents);
    }

    @Test
    @DisplayName("Genau 20 Objekte in der Liste")
    void zwanzig() {
        assertEquals(20, liste().size(), "Die Liste muss genau 20 Student-Objekte enthalten. Läuft deine Schleife von i = 0 solange i < 20? Rufst du in der Schleife students.add(...) auf?");
    }

    @Test
    @DisplayName("Das erste Objekt: Ali, Platz 1, Schließfach 100")
    void erstes() {
        List<Student> l = liste();
        assertTrue(l.size() >= 1, "Die Liste ist leer. Rufst du in der Schleife students.add(...) auf?");
        Student s = l.get(0);
        assertEquals("Ali", s.getName(), "Das erste Objekt muss den Namen names[0] haben, also \"Ali\". Beginnt deine Schleife bei 0?");
        assertEquals(1, s.getSeat(), "Das erste Objekt (i = 0) muss Sitzplatz 1 haben: i + 1.");
        assertEquals(100, s.getLocker(), "Das erste Objekt (i = 0) muss Schließfach 100 haben: 100 + i * 2.");
    }

    @Test
    @DisplayName("Ein Objekt aus der Mitte: Kian, Platz 11, Schließfach 120")
    void mitte() {
        List<Student> l = liste();
        assertTrue(l.size() >= 11, "Die Liste hat weniger als 11 Objekte. Läuft deine Schleife bis i < 20?");
        Student s = l.get(10);
        assertEquals("Kian", s.getName(), "Das Objekt mit der Nummer 10 muss den Namen names[10] haben, also \"Kian\".");
        assertEquals(11, s.getSeat(), "Das Objekt mit der Nummer 10 muss Sitzplatz 11 haben: i + 1.");
        assertEquals(120, s.getLocker(), "Das Objekt mit der Nummer 10 muss Schließfach 120 haben: 100 + 10 * 2.");
    }

    @Test
    @DisplayName("Das letzte Objekt: Tilda, Platz 20, Schließfach 138")
    void letztes() {
        List<Student> l = liste();
        assertTrue(l.size() >= 20, "Die Liste hat weniger als 20 Objekte. Läuft deine Schleife bis i < 20?");
        Student s = l.get(19);
        assertEquals("Tilda", s.getName(), "Das letzte Objekt muss den Namen names[19] haben, also \"Tilda\".");
        assertEquals(20, s.getSeat(), "Das letzte Objekt (i = 19) muss Sitzplatz 20 haben.");
        assertEquals(138, s.getLocker(), "Das letzte Objekt (i = 19) muss Schließfach 138 haben: 100 + 19 * 2.");
    }

    @Test
    @DisplayName("Die Methode benutzt for")
    void benutztFor() throws IOException {
        assertTrue(Pruefen.benutzt("SeatingPlan.java", "for"), "In der Methode createStudents soll eine for-Schleife stehen.");
    }
}
