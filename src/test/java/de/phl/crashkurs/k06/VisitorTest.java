package de.phl.crashkurs.k06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Der Zähler bleibt zwischen den Tests stehen (er gehört der Klasse).
 * Deshalb vergleichen die Tests immer den Stand vorher und nachher.
 */
@DisplayName("Kapitel 6, Aufgabe 1: Visitor zählt mit")
class VisitorTest {

    @Test
    @DisplayName("Ein neuer Besucher erhöht den Zähler um 1")
    void einBesucher() {
        int vorher = Visitor.getCount();
        new Visitor();
        assertEquals(vorher + 1, Visitor.getCount(), "Nach einem new Visitor() muss Visitor.getCount() um 1 größer sein als vorher. "
                + "Zählst du im Konstruktor mit count++ hoch, und gibt getCount() den Zähler zurück?");
    }

    @Test
    @DisplayName("Drei neue Besucher erhöhen den Zähler um 3")
    void dreiBesucher() {
        int vorher = Visitor.getCount();
        new Visitor();
        new Visitor();
        new Visitor();
        assertEquals(vorher + 3, Visitor.getCount(), "Nach drei new Visitor() muss Visitor.getCount() um 3 größer sein als vorher. "
                + "Läuft count++ im Konstruktor, also bei jedem new genau einmal?");
    }

    @Test
    @DisplayName("Der Zähler gehört der Klasse (static)")
    void zaehlerIstStatic() {
        Field f;
        try {
            f = Visitor.class.getDeclaredField("count");
        } catch (NoSuchFieldException e) {
            fail("Die Klasse Visitor braucht ein Attribut mit dem Namen count.");
            return;
        }
        assertTrue(Modifier.isStatic(f.getModifiers()), "Das Attribut count muss static sein: private static int count; Sonst hat jedes Objekt seinen eigenen Zähler.");
    }
}
