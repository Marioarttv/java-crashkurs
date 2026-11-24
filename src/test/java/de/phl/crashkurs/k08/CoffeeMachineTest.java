package de.phl.crashkurs.k08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 8, Aufgabe 3: CoffeeMachine.sell")
class CoffeeMachineTest {

    @Test
    @DisplayName("SMALL: Name kleiner Kaffee")
    void kleinName() {
        CoffeeMachine machine = new CoffeeMachine(Size.SMALL);
        assertEquals("kleiner Kaffee", machine.sell(new CoffeeCard(1000)), "Bei SMALL muss sell den Text \"kleiner Kaffee\" zurückgeben. Gibst du am Ende name zurück? Endet case SMALL mit break? Sonst überschreiben die Fälle darunter den Namen.");
    }

    @Test
    @DisplayName("SMALL: Karte zahlt 150 Cent")
    void kleinPreis() {
        CoffeeMachine machine = new CoffeeMachine(Size.SMALL);
        CoffeeCard card = new CoffeeCard(1000);
        machine.sell(card);
        assertEquals(850, card.getBalance(), "Bei SMALL kostet der Kaffee 150 Cent. Von 1000 Cent müssen 850 übrig sein. Rufst du nach dem switch card.pay(price) auf? Endet case SMALL mit break?");
    }

    @Test
    @DisplayName("MEDIUM: mittlerer Kaffee für 200 Cent")
    void mittel() {
        CoffeeMachine machine = new CoffeeMachine(Size.MEDIUM);
        CoffeeCard card = new CoffeeCard(1000);
        assertEquals("mittlerer Kaffee", machine.sell(card), "Bei MEDIUM muss sell den Text \"mittlerer Kaffee\" zurückgeben.");
        assertEquals(800, card.getBalance(), "Bei MEDIUM kostet der Kaffee 200 Cent. Von 1000 Cent müssen 800 übrig sein. Fehlt ein break?");
    }

    @Test
    @DisplayName("LARGE: großer Kaffee für 250 Cent")
    void gross() {
        CoffeeMachine machine = new CoffeeMachine(Size.LARGE);
        CoffeeCard card = new CoffeeCard(1000);
        assertEquals("großer Kaffee", machine.sell(card), "Bei LARGE muss sell den Text \"großer Kaffee\" zurückgeben.");
        assertEquals(750, card.getBalance(), "Bei LARGE kostet der Kaffee 250 Cent. Von 1000 Cent müssen 750 übrig sein.");
    }

    @Test
    @DisplayName("Zweimal verkaufen: Karte zahlt zweimal")
    void zweimal() {
        CoffeeMachine machine = new CoffeeMachine(Size.MEDIUM);
        CoffeeCard card = new CoffeeCard(1000);
        machine.sell(card);
        machine.sell(card);
        assertEquals(600, card.getBalance(), "Zwei mittlere Kaffees kosten zusammen 400 Cent. Von 1000 Cent müssen 600 übrig sein.");
    }

    @Test
    @DisplayName("Die Methode benutzt switch")
    void benutztSwitch() throws IOException {
        assertTrue(Quelltext.benutzt("CoffeeMachine.java", "switch"), "In der Methode sell soll ein switch über das Attribut size stehen.");
    }
}
