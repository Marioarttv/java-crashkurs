package de.phl.crashkurs.k13;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * today gehört der Klasse und bleibt zwischen den Tests stehen.
 * Deshalb setzt startFestival() den Festivaltag vor jedem Test auf 1.
 */
@DisplayName("Kapitel 13, Aufgabe 1: Wristband")
class WristbandTest {

    @BeforeEach
    void festivalBeginnt() {
        Wristband.startFestival();
    }

    @Test
    @DisplayName("nextDay zählt den Festivaltag hoch")
    void naechsterTag() {
        Wristband.nextDay();
        assertEquals(2, Wristband.getToday(), "Nach einem Aufruf von Wristband.nextDay() muss today 2 sein. Zählst du today in nextDay um 1 hoch?");
        Wristband.nextDay();
        assertEquals(3, Wristband.getToday(), "Nach zwei Aufrufen von Wristband.nextDay() muss today 3 sein.");
    }

    @Test
    @DisplayName("Am ersten Tag gelten alle Bändchen")
    void ersterTag() {
        Wristband einTag = new Wristband("Lena", 1);
        Wristband dreiTage = new Wristband("Jonas", 3);
        assertTrue(einTag.isValid(), "Am Tag 1 gilt ein Bändchen mit lastDay 1 noch. 1 <= 1 ist wahr. Gibt isValid dann true zurück?");
        assertTrue(dreiTage.isValid(), "Am Tag 1 gilt ein Bändchen mit lastDay 3.");
    }

    @Test
    @DisplayName("Am zweiten Tag gilt das Bändchen für einen Tag nicht mehr")
    void zweiterTag() {
        Wristband einTag = new Wristband("Lena", 1);
        Wristband dreiTage = new Wristband("Jonas", 3);
        Wristband.nextDay();
        assertTrue(dreiTage.isValid(), "Am Tag 2 gilt ein Bändchen mit lastDay 3 noch. Zählt nextDay today hoch, und vergleicht isValid today mit lastDay?");
        assertFalse(einTag.isValid(), "Am Tag 2 gilt ein Bändchen mit lastDay 1 nicht mehr. 2 <= 1 ist falsch.");
    }

    @Test
    @DisplayName("Ein Aufruf von nextDay ändert die Antwort aller Bändchen")
    void einTagFuerAlle() {
        Wristband a = new Wristband("Ali", 2);
        Wristband b = new Wristband("Bea", 2);
        assertTrue(a.isValid() && b.isValid(), "Am Tag 1 gelten beide Bändchen mit lastDay 2.");
        Wristband.nextDay();
        assertTrue(a.isValid() && b.isValid(), "Am Tag 2 gelten beide Bändchen mit lastDay 2 noch. 2 <= 2 ist wahr.");
        Wristband.nextDay();
        assertFalse(a.isValid(), "Am Tag 3 gilt das Bändchen von Ali (lastDay 2) nicht mehr. Ein einziger Aufruf Wristband.nextDay() gilt für alle Bändchen.");
        assertFalse(b.isValid(), "Am Tag 3 gilt das Bändchen von Bea (lastDay 2) nicht mehr.");
    }

    @Test
    @DisplayName("Nach dem dritten Tag gilt kein Bändchen mehr")
    void nachDemFestival() {
        Wristband dreiTage = new Wristband("Jonas", 3);
        Wristband.nextDay();
        Wristband.nextDay();
        assertTrue(dreiTage.isValid(), "Am Tag 3 gilt ein Bändchen mit lastDay 3 noch. 3 <= 3 ist wahr.");
        Wristband.nextDay();
        assertEquals(4, Wristband.getToday(), "Nach drei Aufrufen von nextDay muss today 4 sein.");
        assertFalse(dreiTage.isValid(), "Am Tag 4 gilt auch ein Bändchen mit lastDay 3 nicht mehr.");
    }
}
