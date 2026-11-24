package de.phl.crashkurs.k03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 3, Aufgabe 3: Recipe.printShoppingList und main")
class ShoppingListTest {

    private static void scaleMussStimmen() {
        assertTrue(Recipe.scale(500, 2) == 250 && Recipe.scale(200, 6) == 300,
                "Löse zuerst Aufgabe 2: scale rechnet noch nicht richtig. Diese Aufgabe benutzt scale.");
    }

    @Test
    @DisplayName("Einkaufsliste für 2 Personen")
    void zweiPersonen() {
        scaleMussStimmen();
        assertEquals(List.of("Mehl: 250 g", "Zucker: 100 g"), Ausgabe.zeilen(() -> Recipe.printShoppingList(2)),
                "printShoppingList(2) muss genau zwei Zeilen ausgeben: Mehl: 250 g und Zucker: 100 g. Rechne mit scale(500, persons) und scale(200, persons).");
    }

    @Test
    @DisplayName("Einkaufsliste für 6 Personen")
    void sechsPersonen() {
        scaleMussStimmen();
        assertEquals(List.of("Mehl: 750 g", "Zucker: 300 g"), Ausgabe.zeilen(() -> Recipe.printShoppingList(6)),
                "printShoppingList(6) muss genau zwei Zeilen ausgeben: Mehl: 750 g und Zucker: 300 g. Benutzt du den Parameter persons statt einer festen Zahl?");
    }

    @Test
    @DisplayName("printShoppingList ruft scale auf")
    void ruftScaleAuf() {
        String text = Ausgabe.quelltext("Recipe.java");
        Matcher m = Pattern.compile("\\bscale\\s*\\(").matcher(text);
        int anzahl = 0;
        while (m.find()) {
            anzahl++;
        }
        assertTrue(text.isEmpty() || anzahl >= 3,
                "printShoppingList soll die Mengen mit scale umrechnen, also scale zweimal aufrufen, statt selbst zu rechnen.");
    }

    @Test
    @DisplayName("main ruft printShoppingList für 6 Personen auf")
    void main() {
        scaleMussStimmen();
        assertEquals(List.of("Mehl: 750 g", "Zucker: 300 g"), Ausgabe.zeilen(() -> Recipe.main(new String[0])),
                "main soll printShoppingList(6) aufrufen. Dann gibt das Programm Mehl: 750 g und Zucker: 300 g aus.");
    }
}
