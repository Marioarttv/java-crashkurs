package de.phl.crashkurs.k02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/** Die Klasse TripPlanner legst du selbst an. Deshalb sucht der Test sie über ihren Namen. */
@DisplayName("Kapitel 2, Aufgabe 3: neue Klasse TripPlanner")
class TripPlannerTest {

    private static Class<?> klasse() {
        try {
            return Class.forName("de.phl.crashkurs.k02.TripPlanner");
        } catch (ClassNotFoundException e) {
            if (Files.exists(Path.of(Quelltext.ORDNER, "TripPlanner.java"))) {
                return fail("Die Datei TripPlanner.java gibt es, aber ihre package-Zeile fehlt oder ist falsch. "
                        + "Deshalb liegt die Klasse nicht im Paket de.phl.crashkurs.k02. Die erste Zeile muss lauten: package de.phl.crashkurs.k02;");
            }
            return fail("Die Klasse TripPlanner gibt es im Paket de.phl.crashkurs.k02 noch nicht. "
                    + "Leg im Ordner " + Quelltext.ORDNER + " die Datei TripPlanner.java an: Rechtsklick auf den Ordner k02, "
                    + "New Java File, Class..., Name TripPlanner. Ganz oben in der Datei muss stehen: package de.phl.crashkurs.k02;");
        }
    }

    private static Method main() {
        try {
            Method m = klasse().getMethod("main", String[].class);
            assertTrue(Modifier.isStatic(m.getModifiers()) && m.getReturnType() == void.class,
                    "Die Zeile mit main muss genau so lauten: public static void main(String[] args) {");
            return m;
        } catch (NoSuchMethodException e) {
            return fail("In TripPlanner fehlt der Startpunkt. Schreib in die Klasse: public static void main(String[] args) { ... }");
        }
    }

    @Test
    @DisplayName("1. Klasse TripPlanner im Paket k02 mit richtiger package-Zeile")
    void klasseUndPackage() {
        klasse();
        Quelltext.enthaelt(Quelltext.lies("TripPlanner.java"), "(?m)^\\s*package\\s+de\\.phl\\.crashkurs\\.k02\\s*;",
                "In TripPlanner.java fehlt die package-Zeile oder sie ist falsch. Die erste Zeile muss lauten: package de.phl.crashkurs.k02;");
    }

    @Test
    @DisplayName("2. public static void main")
    void hatMain() {
        main();
    }

    @Test
    @DisplayName("3. Variable busPrice mit dem Wert 1800")
    void busPrice() {
        Quelltext.enthaelt(Quelltext.lies("TripPlanner.java"), "\\bint\\s+busPrice\\s*=\\s*1800\\s*;",
                "Es fehlt die Zeile int busPrice = 1800; Schreib die Zahl ohne Tausenderpunkt: 1800, nicht 1.800.");
    }

    @Test
    @DisplayName("4. Variable travelers mit dem Wert 24")
    void travelers() {
        Quelltext.enthaelt(Quelltext.lies("TripPlanner.java"), "\\bint\\s+travelers\\s*=\\s*24\\s*;",
                "Es fehlt die Zeile int travelers = 24;");
    }

    @Test
    @DisplayName("5. Variable pricePerTraveler aus busPrice geteilt durch travelers")
    void pricePerTraveler() {
        Quelltext.enthaelt(Quelltext.lies("TripPlanner.java"), "\\bint\\s+pricePerTraveler\\s*=\\s*busPrice\\s*/\\s*travelers\\s*;",
                "Es fehlt die Zeile int pricePerTraveler = busPrice / travelers; Rechne mit den Namen der Variablen.");
    }

    @Test
    @DisplayName("6. Ausgabe enthält Bus pro Person: 75")
    void ausgabe() {
        Method m = main();
        List<String> ist = Quelltext.zeilen(() -> {
            try {
                m.invoke(null, (Object) new String[0]);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        assertTrue(ist.contains("Bus pro Person: 75"),
                "Die Ausgabe muss die Zeile Bus pro Person: 75 enthalten. Dein Programm gibt aus: " + ist);
        Quelltext.enthaelt(Quelltext.lies("TripPlanner.java"), "println\\s*\\([^;]*\\bpricePerTraveler\\b",
                "Die Ausgabe soll die Variable pricePerTraveler benutzen, nicht die fertige Zahl.");
    }
}
