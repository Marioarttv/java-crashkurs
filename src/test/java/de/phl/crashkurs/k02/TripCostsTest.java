package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 2, Aufgabe 1: TripCosts.main")
class TripCostsTest {

    @Test
    @DisplayName("Variable students mit dem Wert 24")
    void students() {
        Quelltext.enthaelt(Quelltext.lies("TripCosts.java"), "\\bint\\s+students\\s*=\\s*24\\s*;",
                "Es fehlt die Zeile int students = 24; (Typ int, Name students genau so geschrieben, Wert 24).");
    }

    @Test
    @DisplayName("Variable pricePerPerson mit dem Wert 85")
    void pricePerPerson() {
        Quelltext.enthaelt(Quelltext.lies("TripCosts.java"), "\\bint\\s+pricePerPerson\\s*=\\s*85\\s*;",
                "Es fehlt die Zeile int pricePerPerson = 85; (Achte auf die Großbuchstaben: price, Per, Person).");
    }

    @Test
    @DisplayName("Variable totalCost aus students mal pricePerPerson")
    void totalCost() {
        Quelltext.enthaelt(Quelltext.lies("TripCosts.java"),
                "\\bint\\s+totalCost\\s*=\\s*(students\\s*\\*\\s*pricePerPerson|pricePerPerson\\s*\\*\\s*students)\\s*;",
                "Es fehlt die Zeile int totalCost = students * pricePerPerson; Rechne mit den Namen der Variablen, nicht mit den Zahlen.");
    }

    @Test
    @DisplayName("gibt Gesamtkosten: 2040 aus")
    void ausgabe() {
        assertEquals(List.of("Gesamtkosten: 2040"), Quelltext.zeilen(() -> TripCosts.main(new String[0])),
                "Erwartet ist genau eine Zeile: Gesamtkosten: 2040. Verbinde den Text \"Gesamtkosten: \" mit + und der Variable totalCost.");
        Quelltext.enthaelt(Quelltext.lies("TripCosts.java"), "println\\s*\\([^;]*\\btotalCost\\b",
                "Die Ausgabe stimmt, aber sie soll die Variable totalCost benutzen, nicht die fertige Zahl.");
    }
}
