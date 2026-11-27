package de.phl.crashkurs.k13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Kapitel 13, Aufgabe 4: Gate.checkAll")
class GateTest {

    private static ArrayList<Ticket> tickets(int... codes) {
        ArrayList<Ticket> liste = new ArrayList<>();
        for (int code : codes) {
            liste.add(new Ticket(code));
        }
        return liste;
    }

    /** Ruft checkAll auf und gibt zurück, was dabei mit System.out ausgegeben wurde. */
    private static String ausgabe(Gate gate, ArrayList<Ticket> liste) {
        PrintStream alt = System.out;
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(puffer, true, StandardCharsets.UTF_8));
        try {
            assertDoesNotThrow(() -> gate.checkAll(liste),
                    "checkAll darf nicht mit einer Exception abbrechen. Steht der Aufruf ticket.check() in einem try-Block mit passendem catch?");
        } finally {
            System.setOut(alt);
        }
        return puffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    @DisplayName("Drei gültige Tickets: alle drei kommen herein")
    void alleGueltig() {
        Gate gate = new Gate();
        ausgabe(gate, tickets(1234, 5678, 9999));
        assertEquals(3, gate.getAdmitted(), "Die Tickets 1234, 5678 und 9999 sind gültig. admitted muss danach 3 sein. Zählst du nach ticket.check() admitted hoch?");
        assertEquals(0, gate.getRejected(), "Kein Ticket ist ungültig. rejected muss 0 bleiben.");
    }

    @Test
    @DisplayName("Ein ungültiges Ticket in der Mitte: abgewiesen, die anderen kommen herein")
    void einUngueltiges() {
        Gate gate = new Gate();
        String aus = ausgabe(gate, tickets(1234, 99, 5678));
        assertEquals(2, gate.getAdmitted(), "Von den Tickets 1234, 99 und 5678 sind zwei gültig. admitted muss 2 sein. "
                + "Prüft die Schleife nach dem abgewiesenen Ticket weiter?");
        assertEquals(1, gate.getRejected(), "Das Ticket 99 gibt es nicht. rejected muss 1 sein. Zählst du im catch-Block rejected hoch?");
        assertEquals("Abgewiesen: Ticket 99 gibt es nicht\n", aus,
                "Für das Ticket 99 muss genau eine Zeile ausgegeben werden: \"Abgewiesen: \" und der Text der Exception. Den Text holst du mit e.getMessage().");
    }

    @Test
    @DisplayName("Dasselbe Ticket zweimal: beim zweiten Mal abgewiesen")
    void zweimalDasselbe() {
        Gate gate = new Gate();
        Ticket ticket = new Ticket(4711);
        ArrayList<Ticket> liste = new ArrayList<>();
        liste.add(ticket);
        liste.add(ticket);
        String aus = ausgabe(gate, liste);
        assertEquals(1, gate.getAdmitted(), "Das Ticket 4711 kommt zweimal in der Liste vor. Beim ersten Mal ist es gültig. admitted muss 1 sein.");
        assertEquals(1, gate.getRejected(), "Beim zweiten Mal wirft check() eine Exception. rejected muss 1 sein.");
        assertTrue(aus.contains("Abgewiesen: Ticket 4711 wurde schon benutzt"), "Für das zweite Mal muss \"Abgewiesen: Ticket 4711 wurde schon benutzt\" ausgegeben werden.");
    }

    @Test
    @DisplayName("Das erste Ticket ist ungültig: die Schleife läuft trotzdem weiter")
    void erstesUngueltig() {
        Gate gate = new Gate();
        ausgabe(gate, tickets(12, 3456, 100000));
        assertEquals(1, gate.getAdmitted(), "Von den Tickets 12, 3456 und 100000 ist nur 3456 gültig. admitted muss 1 sein. "
                + "Stehen try und catch im Block der Schleife, damit die Schleife nach einer Exception weiterläuft?");
        assertEquals(2, gate.getRejected(), "Die Tickets 12 und 100000 gibt es nicht. rejected muss 2 sein.");
    }
}
