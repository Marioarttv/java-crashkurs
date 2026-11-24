package de.phl.crashkurs.k06;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Die nächste freie Nummer bleibt zwischen den Tests stehen (sie gehört der Klasse).
 * Deshalb wird der allererste Antrag einmal vor allen Tests gebaut. Danach prüfen die Tests,
 * dass die Nummern aufeinander folgen und jeder Antrag seine eigene behält.
 */
@DisplayName("Kapitel 6, Aufgabe 2: Request vergibt fortlaufende Nummern")
class RequestTest {

    private static Request erster;

    @BeforeAll
    static void ersterAntrag() {
        erster = new Request();
    }

    @Test
    @DisplayName("Der erste Antrag bekommt die Nummer 1001")
    void erstesIstTausendeins() {
        assertEquals(1001, erster.getNumber(), "Der allererste Antrag muss die Nummer 1001 bekommen. "
                + "Hat das Klassen-Attribut nextNumber den Startwert 1001? Und kommt im Konstruktor erst number = nextNumber; und danach nextNumber++; ?");
    }

    @Test
    @DisplayName("Zwei Anträge nacheinander haben aufeinanderfolgende Nummern")
    void fortlaufend() {
        Request a = new Request();
        Request b = new Request();
        assertEquals(a.getNumber() + 1, b.getNumber(), "Der zweite Antrag muss die Nummer direkt nach dem ersten haben. Erster Antrag: " + a.getNumber()
                + ", zweiter: " + b.getNumber() + ". Zählst du nextNumber im Konstruktor hoch, und gehört nextNumber der Klasse (static)?");
    }

    @Test
    @DisplayName("Jeder Antrag behält seine eigene Nummer")
    void eigeneNummer() {
        Request a = new Request();
        int nummerA = a.getNumber();
        new Request();
        new Request();
        assertEquals(nummerA, a.getNumber(), "Ein Antrag darf seine Nummer nicht ändern, wenn später weitere Anträge entstehen. "
                + "Die eigene Nummer number gehört jedem Objekt (ohne static), nur nextNumber gehört der Klasse.");
    }
}
