package de.phl.crashkurs.k09;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 9, Aufgabe 3: Match.play")
class MatchTest {

    @Test
    @DisplayName("b wird zuerst müde: 2 Runden, richtige Ausgabe")
    void bWirdMuede() {
        Player lea = new Player("Lea", 10, 4);
        Player jonas = new Player("Jonas", 8, 3);
        int[] runden = new int[1];
        String aus = Pruefen.ausgabe(() -> runden[0] = Match.play(lea, jonas));
        assertEquals(2, runden[0], "Lea (Konzentration 10, Kraft 4) gegen Jonas (Konzentration 8, Kraft 3): Nach 2 Runden ist Jonas TIRED. play muss 2 zurückgeben. Zählst du rounds in jeder Runde hoch?");
        assertEquals(PlayerStatus.TIRED, jonas.getStatus(), "Jonas muss am Ende TIRED sein.");
        assertEquals(7, lea.getConcentration(), "Jonas darf nur in Runde 1 zurückspielen. In Runde 2 ist er nach Leas Schlag schon TIRED. Lea muss deshalb noch 10 - 3 = 7 Konzentration haben. Fragst du vor b.hit(a) mit einem if, ob b noch READY ist?");
        assertEquals("Lea spielt zu Jonas\nJonas spielt zu Lea\nLea spielt zu Jonas\nLea: READY\nJonas: TIRED\n", aus,
                "Die Ausgabe stimmt nicht. Gib in jeder Runde den Satz von a.hit(b) aus und, wenn b noch READY ist, den Satz von b.hit(a). Nach der Schleife: a.getName() + \": \" + a.getStatus(), dann dasselbe für b.");
    }

    @Test
    @DisplayName("a wird zuerst müde: 1 Runde")
    void aWirdMuede() {
        Player lea = new Player("Lea", 3, 2);
        Player jonas = new Player("Jonas", 10, 5);
        int[] runden = new int[1];
        String aus = Pruefen.ausgabe(() -> runden[0] = Match.play(lea, jonas));
        assertEquals(1, runden[0], "Lea (Konzentration 3) wird schon in Runde 1 von Jonas' Schlag TIRED. play muss 1 zurückgeben.");
        assertEquals("Lea spielt zu Jonas\nJonas spielt zu Lea\nLea: TIRED\nJonas: READY\n", aus,
                "Die Ausgabe stimmt nicht. Erwartet sind die zwei Schläge aus Runde 1 und danach der Endstatus beider Spieler.");
    }

    @Test
    @DisplayName("Die Methode benutzt while")
    void benutztWhile() throws IOException {
        assertTrue(Pruefen.benutzt("Match.java", "while"), "In der Methode play soll eine while-Schleife stehen.");
    }
}
