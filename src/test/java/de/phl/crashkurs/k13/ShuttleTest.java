package de.phl.crashkurs.k13;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@DisplayName("Kapitel 13, Aufgabe 5: Shuttle.run")
class ShuttleTest {

    private static final String ENDLOS = "run läuft länger als 2 Sekunden. Vermutlich ist das eine Endlosschleife. Ändert sich im Block der Schleife etwas, das die Bedingung irgendwann falsch macht?";

    private static int fahre(Shuttle shuttle, int waiting) {
        return assertTimeoutPreemptively(Duration.ofSeconds(2), () -> shuttle.run(waiting), ENDLOS);
    }

    @Test
    @DisplayName("20 Leute, 8 Plätze, voller Akku: 3 Fahrten, niemand wartet mehr")
    void alleKommenAn() {
        Shuttle shuttle = new Shuttle(8, 100);
        int rest = fahre(shuttle, 20);
        assertEquals(3, shuttle.getTrips(), "20 Leute, 8 Plätze: Die erste Fahrt bringt 8, die zweite 8, die dritte die letzten 4. trips muss 3 sein.");
        assertEquals(55, shuttle.getBattery(), "Drei Fahrten verbrauchen 3 mal 15 Prozent. Von 100 bleiben 55.");
        assertEquals(0, rest, "Nach drei Fahrten wartet niemand mehr. run muss 0 zurückgeben, nicht weniger als 0.");
    }

    @Test
    @DisplayName("Der Akku reicht nur für 3 Fahrten")
    void akkuLeer() {
        Shuttle shuttle = new Shuttle(8, 50);
        int rest = fahre(shuttle, 40);
        assertEquals(3, shuttle.getTrips(), "Mit 50 Prozent Akku schafft der Shuttle 3 Fahrten: 50, 35, 20, danach bleiben 5. Für eine vierte Fahrt reicht das nicht.");
        assertEquals(5, shuttle.getBattery(), "Nach 3 Fahrten hat der Akku noch 5 Prozent.");
        assertEquals(16, rest, "3 Fahrten bringen 24 von 40 Leuten. 16 warten noch.");
    }

    @Test
    @DisplayName("Genau 15 Prozent reichen noch für eine Fahrt")
    void genauFuenfzehn() {
        Shuttle shuttle = new Shuttle(8, 15);
        int rest = fahre(shuttle, 10);
        assertEquals(1, shuttle.getTrips(), "Bei genau 15 Prozent darf der Shuttle noch einmal fahren. Prüf die Grenze in der Bedingung: mindestens 15 heißt >= 15.");
        assertEquals(0, shuttle.getBattery(), "Nach der Fahrt hat der Akku 0 Prozent.");
        assertEquals(2, rest, "Eine Fahrt bringt 8 von 10 Leuten. 2 warten noch.");
    }

    @Test
    @DisplayName("Zweimal run: der Shuttle merkt sich seinen Akku")
    void zweimal() {
        Shuttle shuttle = new Shuttle(10, 40);
        int rest = fahre(shuttle, 15);
        assertEquals(2, shuttle.getTrips(), "15 Leute, 10 Plätze: Der Shuttle fährt zweimal (10, dann 5). trips muss 2 sein.");
        assertEquals(0, rest, "Nach zwei Fahrten wartet niemand mehr.");
        assertEquals(10, shuttle.getBattery(), "Nach zwei Fahrten hat der Akku noch 40 - 30 = 10 Prozent.");
        int spaeter = fahre(shuttle, 6);
        assertEquals(6, spaeter, "Später warten 6 Leute, aber der Akku hat nur noch 10 Prozent. Der Shuttle fährt nicht, alle 6 warten weiter.");
        assertEquals(2, shuttle.getTrips(), "Ohne Fahrt bleibt trips bei 2.");
    }
}
