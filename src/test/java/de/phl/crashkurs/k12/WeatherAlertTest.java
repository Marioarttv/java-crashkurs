package de.phl.crashkurs.k12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Kapitel 12, Aufgabe 3: WeatherAlert")
class WeatherAlertTest {

    @Test
    @DisplayName("30 km/h: keine Warnung")
    void wenigWind() {
        assertEquals(Warning.NONE, WeatherAlert.forWind(30), "Bei 30 km/h gibt es keine Warnung (NONE).");
    }

    @Test
    @DisplayName("70 km/h: Warnung WIND")
    void wind() {
        assertEquals(Warning.WIND, WeatherAlert.forWind(70), "Bei 70 km/h muss die Warnstufe WIND sein.");
    }

    @Test
    @DisplayName("120 km/h: Warnung STORM")
    void sturm() {
        assertEquals(Warning.STORM, WeatherAlert.forWind(120), "Bei 120 km/h muss die Warnstufe STORM sein. Welche Bedingung prüft Java zuerst?");
    }

    @Test
    @DisplayName("Rat bei NONE und STORM")
    void ratNoneStorm() {
        assertEquals("Alles ruhig", WeatherAlert.advice(Warning.NONE), "Bei NONE ist der Rat \"Alles ruhig\".");
        assertEquals("Drinnen bleiben", WeatherAlert.advice(Warning.STORM), "Bei STORM ist der Rat \"Drinnen bleiben\".");
    }

    @Test
    @DisplayName("Rat bei WIND")
    void ratWind() {
        assertEquals("Schirm zu Hause lassen", WeatherAlert.advice(Warning.WIND), "Bei WIND ist der Rat \"Schirm zu Hause lassen\".");
    }
}
