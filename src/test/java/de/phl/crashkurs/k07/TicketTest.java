package de.phl.crashkurs.k07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 7, Aufgabe 2: Ticket.price")
class TicketTest {

    @Test
    @DisplayName("3 Jahre: 0 Euro")
    void kleinkindZahltNichts() {
        assertEquals(0, Ticket.price(3), "Mit 3 Jahren beträgt der Eintrittspreis 0 Euro.");
    }

    @Test
    @DisplayName("10 Jahre: 5 Euro")
    void kindZahltFuenf() {
        assertEquals(5, Ticket.price(10), "Mit 10 Jahren beträgt der Eintrittspreis 5 Euro.");
    }

    @Test
    @DisplayName("30 Jahre: 9 Euro")
    void erwachsenZahltNeun() {
        assertEquals(9, Ticket.price(30), "Mit 30 Jahren beträgt der Eintrittspreis 9 Euro.");
    }

    @Test
    @DisplayName("Grenzen: 5, 6, 17 und 18 Jahre")
    void grenzenStimmen() {
        assertEquals(0, Ticket.price(5), "Mit 5 Jahren ist das Alter noch unter 6. Der Preis muss 0 Euro sein.");
        assertEquals(5, Ticket.price(6), "Mit genau 6 Jahren muss der Preis 5 Euro sein. Prüfe deine Bedingung: Unter 6 heißt age < 6.");
        assertEquals(5, Ticket.price(17), "Mit 17 Jahren muss der Preis noch 5 Euro sein.");
        assertEquals(9, Ticket.price(18), "Mit genau 18 Jahren muss der Preis 9 Euro sein. Prüfe deine Bedingung: Unter 18 heißt age < 18.");
    }
}
