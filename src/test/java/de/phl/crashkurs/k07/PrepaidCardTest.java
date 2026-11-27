package de.phl.crashkurs.k07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 7, Aufgabe 3: PrepaidCard.warning")
class PrepaidCardTest {

    @Test
    @DisplayName("35 Euro: genug Guthaben")
    void viel() {
        assertEquals("genug Guthaben", new PrepaidCard(35).warning(), "Bei 35 Euro Guthaben muss warning den Text \"genug Guthaben\" zurückgeben.");
    }

    @Test
    @DisplayName("Grenze 20 und 19 Euro")
    void grenzeZwanzig() {
        assertEquals("genug Guthaben", new PrepaidCard(20).warning(), "Bei genau 20 Euro ist noch genug Guthaben da (20 zählt mit). Brauchst du >= statt >?");
        assertEquals("bald aufladen", new PrepaidCard(19).warning(), "Bei 19 Euro muss warning \"bald aufladen\" zurückgeben.");
    }

    @Test
    @DisplayName("Grenze 5 und 4 Euro")
    void grenzeFuenf() {
        assertEquals("bald aufladen", new PrepaidCard(5).warning(), "Bei genau 5 Euro muss warning \"bald aufladen\" zurückgeben (5 zählt mit). Prüfe die zweite Bedingung.");
        assertEquals("jetzt aufladen", new PrepaidCard(4).warning(), "Bei 4 Euro muss warning \"jetzt aufladen\" zurückgeben.");
    }

    @Test
    @DisplayName("0 Euro: jetzt aufladen")
    void leer() {
        assertEquals("jetzt aufladen", new PrepaidCard(0).warning(), "Bei 0 Euro Guthaben muss warning \"jetzt aufladen\" zurückgeben. Gibt dein else-Block diesen Text zurück?");
    }

    @Test
    @DisplayName("Der Hinweis ändert das Guthaben nicht")
    void guthabenBleibt() {
        PrepaidCard card = new PrepaidCard(12);
        card.warning();
        assertEquals(12, card.getCredit(), "warning soll das Guthaben nur lesen, nicht ändern. Nach warning() müssen noch 12 Euro auf der Karte sein.");
    }
}
