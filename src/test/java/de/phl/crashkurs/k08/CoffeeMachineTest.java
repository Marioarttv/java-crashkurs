package de.phl.crashkurs.k08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 8, Aufgabe 3: CoffeeMachine.price")
class CoffeeMachineTest {

    @Test
    @DisplayName("SMALL ohne eigenen Becher: 150 Cent")
    void kleinOhne() {
        assertEquals(150, new CoffeeMachine(Size.SMALL).price(false), "Ein kleiner Kaffee (SMALL) ohne eigenen Becher kostet 150 Cent. Endet case SMALL mit break? Sonst überschreiben die Fälle darunter den Preis.");
    }

    @Test
    @DisplayName("MEDIUM ohne eigenen Becher: 200 Cent")
    void mittelOhne() {
        assertEquals(200, new CoffeeMachine(Size.MEDIUM).price(false), "Ein mittlerer Kaffee (MEDIUM) ohne eigenen Becher kostet 200 Cent. Fehlt ein break?");
    }

    @Test
    @DisplayName("LARGE ohne eigenen Becher: 250 Cent")
    void grossOhne() {
        assertEquals(250, new CoffeeMachine(Size.LARGE).price(false), "Ein großer Kaffee (LARGE) ohne eigenen Becher kostet 250 Cent.");
    }

    @Test
    @DisplayName("Mit eigenem Becher: 20 Cent weniger")
    void mitBecher() {
        assertEquals(130, new CoffeeMachine(Size.SMALL).price(true), "SMALL mit eigenem Becher kostet 150 - 20 = 130 Cent. Ziehst du nach dem switch mit if (ownCup) 20 Cent ab?");
        assertEquals(180, new CoffeeMachine(Size.MEDIUM).price(true), "MEDIUM mit eigenem Becher kostet 200 - 20 = 180 Cent.");
        assertEquals(230, new CoffeeMachine(Size.LARGE).price(true), "LARGE mit eigenem Becher kostet 250 - 20 = 230 Cent.");
    }

    @Test
    @DisplayName("Zweimal fragen: gleicher Preis")
    void zweimal() {
        CoffeeMachine machine = new CoffeeMachine(Size.MEDIUM);
        machine.price(true);
        assertEquals(180, machine.price(true), "Wer zweimal nach dem Preis fragt, bekommt zweimal denselben Preis. Rechnest du in einer Variable der Methode statt in einem Attribut?");
    }

    @Test
    @DisplayName("Die Methode benutzt switch")
    void benutztSwitch() throws IOException {
        assertTrue(Quelltext.benutzt("CoffeeMachine.java", "switch"), "In der Methode price soll ein switch über das Attribut size stehen.");
    }
}
