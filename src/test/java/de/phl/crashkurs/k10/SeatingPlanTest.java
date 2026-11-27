package de.phl.crashkurs.k10;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 10, Aufgabe 3: SeatingPlan.countInRow")
class SeatingPlanTest {

    private static List<Student> klasse() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ali", 2));
        students.add(new Student("Bea", 1));
        students.add(new Student("Cem", 2));
        students.add(new Student("Dana", 3));
        students.add(new Student("Emil", 1));
        students.add(new Student("Fatma", 2));
        return students;
    }

    @Test
    @DisplayName("Reihe 2: drei Personen")
    void reiheZwei() {
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> SeatingPlan.countInRow(klasse(), 2)),
                "In Reihe 2 sitzen Ali, Cem und Fatma, also 3 Personen. Zählst du nur, wenn s.getRow() == row ist?");
    }

    @Test
    @DisplayName("Reihe 1: drei Personen, auch die erste in der Liste zählt")
    void reiheEins() {
        List<Student> students = klasse();
        students.add(0, new Student("Ida", 1));
        assertEquals(3, (int) Pruefen.mitZeitlimit(() -> SeatingPlan.countInRow(students, 1)),
                "In Reihe 1 sitzen Ida (ganz vorn in der Liste), Bea und Emil, also 3 Personen. Gehst du mit for-each durch die ganze Liste?");
    }

    @Test
    @DisplayName("Reihe 5: niemand")
    void niemand() {
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> SeatingPlan.countInRow(klasse(), 5)),
                "In Reihe 5 sitzt niemand. Das Ergebnis muss 0 sein. Startet dein Zähler bei 0?");
    }

    @Test
    @DisplayName("Leere Liste: 0")
    void leer() {
        assertEquals(0, (int) Pruefen.mitZeitlimit(() -> SeatingPlan.countInRow(new ArrayList<>(), 1)),
                "In einer leeren Liste sitzt niemand. Das Ergebnis muss 0 sein.");
    }

    @Test
    @DisplayName("Die Methode benutzt for")
    void benutztFor() throws IOException {
        assertTrue(Pruefen.benutzt("SeatingPlan.java", "for"), "In der Methode countInRow soll eine for-each-Schleife stehen: for (Student s : students).");
    }
}
