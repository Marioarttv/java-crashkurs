package de.phl.crashkurs.k05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Kapitel 5, Aufgabe 2: BikeRentalApp.main, Räder zur Station hinzufügen")
class BikeRentalApp2Test {

    @Test
    @DisplayName("Die Station mit zwei Rädern wird ausgegeben")
    void zweiRaeder() {
        String aus = AppRun.ausgabe();
        String hinweis;
        if (aus.contains("Station Schlossplatz: 1 von 4 Plätzen belegt")) {
            hinweis = "Die Station meldet nur 1 Rad. Rufst du station.addBike(...) für beide Räder auf?";
        } else {
            hinweis = "Füge beide Räder mit station.addBike(...) zur Station hinzu und gib danach noch einmal station.getInfo() aus.";
        }
        if (!aus.contains("Station Schlossplatz: 2 von 4 Plätzen belegt")) {
            fail("In der Ausgabe fehlt die Zeile \"Station Schlossplatz: 2 von 4 Plätzen belegt\". " + hinweis + " Deine Ausgabe: " + AppRun.zeigen(aus));
        }
    }
}
