package de.phl.crashkurs.k03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 3, Aufgabe 2: Recipe.scale")
class RecipeTest {

    @Test
    @DisplayName("4 Personen: Menge bleibt gleich")
    void viererRezept() {
        assertEquals(500, Recipe.scale(500, 4),
                "scale(500, 4) muss 500 zurückgeben, denn das Rezept ist schon für 4 Personen. Rechne grams * persons / 4.");
    }

    @Test
    @DisplayName("2 Personen: halbe Menge")
    void zweiPersonen() {
        assertEquals(250, Recipe.scale(500, 2),
                "scale(500, 2) muss 250 zurückgeben: 500 * 2 / 4 = 250.");
    }

    @Test
    @DisplayName("6 Personen: anderthalbfache Menge")
    void sechsPersonen() {
        assertEquals(750, Recipe.scale(500, 6), "scale(500, 6) muss 750 zurückgeben: 500 * 6 / 4 = 750.");
        assertEquals(300, Recipe.scale(200, 6), "scale(200, 6) muss 300 zurückgeben: 200 * 6 / 4 = 300.");
    }
}
