package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 1: BikeRentalApp.main, Station und Räder erzeugen")
class BikeRentalApp1Test {

    @Test
    @DisplayName("Die leere Station wird ausgegeben")
    void station() {
        String aus = AppRun.ausgabe();
        if (!aus.contains("Station Schlossplatz: 0 von 4 Plätzen belegt")) {
            fail("In der Ausgabe fehlt die Zeile \"Station Schlossplatz: 0 von 4 Plätzen belegt\". Erzeuge die Station mit new Station(\"Schlossplatz\", 4) "
                        + "und gib station.getInfo() mit System.out.println aus, bevor du Räder hinzufügst. Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("Rad B-12 wird ausgegeben")
    void rad12() {
        String aus = AppRun.ausgabe();
        if (!aus.contains("Rad B-12, Akku 80 Prozent")) {
            fail("In der Ausgabe fehlt die Zeile \"Rad B-12, Akku 80 Prozent\". Erzeuge das Rad mit new Bike(\"B-12\", 80) und gib getInfo() aus. "
                        + "Achte auf die Reihenfolge der Werte: erst die Nummer, dann der Akku. Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }

    @Test
    @DisplayName("Rad B-7 wird ausgegeben")
    void rad7() {
        String aus = AppRun.ausgabe();
        if (!aus.contains("Rad B-7, Akku 55 Prozent")) {
            fail("In der Ausgabe fehlt die Zeile \"Rad B-7, Akku 55 Prozent\". Erzeuge das zweite Rad mit new Bike(\"B-7\", 55) und gib getInfo() aus. Deine Ausgabe: "
                        + AppRun.zeigen(aus));
        }
    }
}
