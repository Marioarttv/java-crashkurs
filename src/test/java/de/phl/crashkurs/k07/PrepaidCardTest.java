package de.phl.crashkurs.k07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 7, Aufgabe 3: PrepaidCard.pay")
class PrepaidCardTest {

    @Test
    @DisplayName("Bezahlen zieht den Betrag vom Guthaben ab")
    void bezahlenZiehtAb() {
        PrepaidCard card = new PrepaidCard(20);
        card.pay(5);
        assertEquals(15, card.getBalance(), "Bei 20 Euro Guthaben müssen nach pay(5) noch 15 Euro übrig sein. Ziehst du amount von balance ab?");
    }

    @Test
    @DisplayName("Guthaben über 0: Status bleibt ACTIVE")
    void restguthabenBleibtAktiv() {
        PrepaidCard card = new PrepaidCard(20);
        card.pay(5);
        assertEquals(CardStatus.ACTIVE, card.getStatus(), "Solange noch Guthaben über 0 da ist, bleibt der Status ACTIVE.");
    }

    @Test
    @DisplayName("Guthaben genau 0: Status EMPTY")
    void genauNullIstLeer() {
        PrepaidCard card = new PrepaidCard(20);
        card.pay(20);
        assertEquals(0, card.getBalance(), "Bei 20 Euro Guthaben muss das Guthaben nach pay(20) genau 0 sein.");
        assertEquals(CardStatus.EMPTY, card.getStatus(), "Bei genau 0 Euro Guthaben muss der Status EMPTY sein. Prüfe deine Bedingung: Zum Vergleichen brauchst du ==.");
    }

    @Test
    @DisplayName("Guthaben unter 0: Status OVERDRAWN")
    void unterNullIstUeberzogen() {
        PrepaidCard card = new PrepaidCard(20);
        card.pay(25);
        assertEquals(-5, card.getBalance(), "Bei 20 Euro Guthaben muss das Guthaben nach pay(25) genau -5 sein.");
        assertEquals(CardStatus.OVERDRAWN, card.getStatus(), "Bei einem Guthaben unter 0 Euro muss der Status OVERDRAWN sein.");
    }

    @Test
    @DisplayName("Zweimal bezahlen: erst ACTIVE, dann EMPTY")
    void zweimalBezahlen() {
        PrepaidCard card = new PrepaidCard(10);
        card.pay(4);
        assertEquals(CardStatus.ACTIVE, card.getStatus(), "Nach dem ersten Bezahlen sind noch 6 Euro auf der Karte. Der Status muss ACTIVE sein.");
        card.pay(6);
        assertEquals(CardStatus.EMPTY, card.getStatus(), "Nach dem zweiten Bezahlen ist das Guthaben 0. Der Status muss EMPTY sein.");
    }
}
