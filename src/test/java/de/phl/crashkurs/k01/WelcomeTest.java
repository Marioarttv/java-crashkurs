package de.phl.crashkurs.k01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/** Die Klasse Welcome legst du selbst an. Deshalb sucht der Test sie über ihren Namen. */
@DisplayName("Kapitel 1, Aufgabe 3: neue Klasse Welcome")
class WelcomeTest {

    private static final String ORDNER = "src/main/java/de/phl/crashkurs/k01";

    private static Class<?> klasse() {
        try {
            return Class.forName("de.phl.crashkurs.k01.Welcome");
        } catch (ClassNotFoundException e) {
            if (Files.exists(Path.of(ORDNER, "Welcome.java"))) {
                return fail("Die Datei Welcome.java gibt es, aber ihre package-Zeile fehlt oder ist falsch. "
                        + "Deshalb liegt die Klasse nicht im Paket de.phl.crashkurs.k01. Die erste Zeile muss lauten: package de.phl.crashkurs.k01;");
            }
            return fail("Die Klasse Welcome gibt es im Paket de.phl.crashkurs.k01 noch nicht. "
                    + "Leg im Ordner " + ORDNER + " die Datei Welcome.java an: Rechtsklick auf den Ordner k01, "
                    + "New Java File, Class..., Name Welcome. Ganz oben in der Datei muss stehen: package de.phl.crashkurs.k01;");
        }
    }

    private static Method main() {
        try {
            Method m = klasse().getMethod("main", String[].class);
            assertTrue(Modifier.isStatic(m.getModifiers()) && m.getReturnType() == void.class,
                    "Die Zeile mit main muss genau so lauten: public static void main(String[] args) {");
            return m;
        } catch (NoSuchMethodException e) {
            return fail("In Welcome fehlt der Startpunkt. Schreib in die Klasse: public static void main(String[] args) { ... }");
        }
    }

    @Test
    @DisplayName("Welcome.java liegt im Ordner k01 und beginnt mit der richtigen package-Zeile")
    void dateiUndPackage() throws IOException {
        klasse();
        Path datei = Path.of(ORDNER, "Welcome.java");
        assertTrue(Files.exists(datei), "Die Datei Welcome.java liegt nicht im Ordner " + ORDNER + ".");
        String text = Files.readString(datei, StandardCharsets.UTF_8);
        assertTrue(Pattern.compile("^\\s*package\\s+de\\.phl\\.crashkurs\\.k01\\s*;", Pattern.MULTILINE).matcher(text).find(),
                "In Welcome.java fehlt die package-Zeile oder sie ist falsch. Die erste Zeile muss lauten: package de.phl.crashkurs.k01;");
    }

    @Test
    @DisplayName("Welcome hat den Startpunkt main")
    void hatMain() {
        main();
    }

    @Test
    @DisplayName("Welcome gibt die zwei Zeilen aus")
    void ausgabe() {
        Method m = main();
        List<String> ist = MensaBoardTest.zeilen(() -> {
            try {
                m.invoke(null, (Object) new String[0]);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        assertEquals(List.of("Willkommen in der Mensa", "Guten Appetit"), ist,
                "Die Ausgabe stimmt noch nicht. Erwartet sind genau zwei Zeilen: Willkommen in der Mensa, dann Guten Appetit.");
    }
}
