package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Kapitel 2, Aufgabe 3: HostelBill.main")
class HostelBillTest {

    @Test
    @DisplayName("die vorgegebenen Zeilen sind unverändert")
    void vorgabe() {
        String text = Quelltext.lies("HostelBill.java");
        Quelltext.enthaelt(text, "\\bString\\s+hostel\\s*=\\s*\"Jugendherberge Hamburg\"\\s*;",
                "Die vorgegebene Zeile String hostel = \"Jugendherberge Hamburg\"; fehlt oder wurde geändert. Lass sie stehen.");
        Quelltext.enthaelt(text, "\\bint\\s+nights\\s*=\\s*3\\s*;", "Die vorgegebene Zeile int nights = 3; fehlt oder wurde geändert. Lass sie stehen.");
        Quelltext.enthaelt(text, "\\bdouble\\s+pricePerNight\\s*=\\s*27\\.5\\s*;",
                "Die vorgegebene Zeile double pricePerNight = 27.5; fehlt oder wurde geändert. Lass sie stehen.");
    }

    @Test
    @DisplayName("pricePerNight wird um 1,50 Euro erhöht, ohne neue Variable")
    void erhoehung() {
        String text = Quelltext.lies("HostelBill.java");
        assertFalse(Pattern.compile("(?<![\\w.])pricePerNight\\s*=\\s*29").matcher(text).find(),
                "Schreib nicht das fertige Ergebnis 29 hin. Rechne mit der Variable: pricePerNight = pricePerNight + 1.5;");
        Quelltext.enthaelt(text, "(?<![\\w.])pricePerNight\\s*(=\\s*pricePerNight\\s*\\+\\s*1\\.50?|=\\s*1\\.50?\\s*\\+\\s*pricePerNight|\\+=\\s*1\\.50?)\\s*;",
                "Erhöhe pricePerNight um 1.5 mit pricePerNight = pricePerNight + 1.5; oder kurz pricePerNight += 1.5; (ohne double davor, denn die Variable gibt es schon).");
    }

    @Test
    @DisplayName("stay ist eine Kommazahl aus nights mal pricePerNight")
    void rechnung() {
        Quelltext.enthaelt(Quelltext.lies("HostelBill.java"),
                "\\bdouble\\s+stay\\s*=\\s*(nights\\s*\\*\\s*pricePerNight|pricePerNight\\s*\\*\\s*nights)\\s*;",
                "Es fehlt die Zeile double stay = nights * pricePerNight; Der Preis hat Stellen nach dem Komma, also braucht stay den Typ double.");
    }

    @Test
    @DisplayName("gibt Herberge, Nächte und Preis in einer Zeile aus")
    void ausgabe() {
        assertEquals(List.of("Jugendherberge Hamburg, 3 Nächte: 87.0 Euro"), Quelltext.zeilen(() -> HostelBill.main(new String[0])),
                "Erwartet ist genau eine Zeile: Jugendherberge Hamburg, 3 Nächte: 87.0 Euro. "
                        + "Hast du pricePerNight vor der Rechnung erhöht? 3 mal 29.0 ist 87.0. Achte auf Komma und Leerzeichen im Text.");
        String text = Quelltext.lies("HostelBill.java");
        for (String name : new String[] {"hostel", "nights", "stay"}) {
            Quelltext.enthaelt(text, "println\\s*\\([^;]*\\b" + name + "\\b",
                    "Die Ausgabe stimmt, aber sie soll die Variable " + name + " benutzen, nicht den fertigen Text oder die fertige Zahl.");
        }
    }
}
