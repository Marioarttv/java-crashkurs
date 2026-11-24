package de.phl.crashkurs.k04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Prüft die Klasse LibraryCard, die du selbst anlegst. Der Test sucht die Klasse über ihren Namen,
 * damit das Projekt auch ohne sie kompiliert.
 */
@DisplayName("Kapitel 4, Aufgabe 3: Klasse LibraryCard nach Steckbrief")
class LibraryCardTest {

    private static final String NAME = "de.phl.crashkurs.k04.LibraryCard";

    private Class<?> klasse() {
        try {
            return Class.forName(NAME);
        } catch (ClassNotFoundException e) {
            fail("Die Klasse LibraryCard gibt es noch nicht. Leg im Ordner src/main/java/de/phl/crashkurs/k04 die Datei LibraryCard.java an. "
                    + "Die erste Zeile heißt package de.phl.crashkurs.k04; und die Klasse public class LibraryCard.");
            return null;
        }
    }

    private Object neu(String owner) {
        Constructor<?> k;
        try {
            k = klasse().getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            fail("LibraryCard braucht einen öffentlichen Konstruktor mit einem Parameter vom Typ String: public LibraryCard(String owner). "
                    + "Achtung: Ein Konstruktor hat keinen Rückgabetyp, also kein void davor.");
            return null;
        }
        try {
            return k.newInstance(owner);
        } catch (InvocationTargetException e) {
            fail("Der Konstruktor von LibraryCard bricht mit einer Exception ab: " + e.getCause());
        } catch (ReflectiveOperationException e) {
            fail("LibraryCard lässt sich nicht bauen: " + e);
        }
        return null;
    }

    private Object rufe(Object karte, String methode) {
        Method m;
        try {
            m = klasse().getMethod(methode);
        } catch (NoSuchMethodException e) {
            fail("LibraryCard braucht eine öffentliche Methode " + methode + "() ohne Parameter. Prüfe die Schreibweise genau, auch Groß- und Kleinbuchstaben.");
            return null;
        }
        try {
            return m.invoke(karte);
        } catch (InvocationTargetException e) {
            fail("Die Methode " + methode + "() bricht mit einer Exception ab: " + e.getCause());
        } catch (ReflectiveOperationException e) {
            fail("Die Methode " + methode + "() lässt sich nicht aufrufen: " + e);
        }
        return null;
    }

    @Test
    @DisplayName("Die Klasse LibraryCard gibt es im Paket k04")
    void klasseDa() {
        assertEquals(NAME, klasse().getName());
    }

    @Test
    @DisplayName("Die Attribute owner und loans sind private")
    void attribute() {
        Class<?> c = klasse();
        pruefeAttribut(c, "owner", String.class);
        pruefeAttribut(c, "loans", int.class);
    }

    private void pruefeAttribut(Class<?> c, String name, Class<?> typ) {
        Field f;
        try {
            f = c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            fail("LibraryCard braucht ein Attribut " + name + " vom Typ " + typ.getSimpleName() + ". Es steht in der Klasse, aber außerhalb jeder Methode.");
            return;
        }
        assertEquals(typ, f.getType(), "Das Attribut " + name + " muss den Typ " + typ.getSimpleName() + " haben.");
        if (!Modifier.isPrivate(f.getModifiers())) {
            fail("Das Attribut " + name + " soll private sein: private " + typ.getSimpleName() + " " + name + ";");
        }
        if (Modifier.isStatic(f.getModifiers())) {
            fail("Das Attribut " + name + " darf nicht static sein. Jeder Ausweis hat seine eigenen Werte.");
        }
    }

    @Test
    @DisplayName("getOwner gibt den Namen aus dem Konstruktor zurück")
    void owner() {
        Object karte = neu("Lena");
        assertEquals("Lena", rufe(karte, "getOwner"), "Nach new LibraryCard(\"Lena\") muss getOwner() \"Lena\" zurückgeben. Speichert dein Konstruktor den Parameter mit this.owner = owner; ?");
    }

    @Test
    @DisplayName("Ein neuer Ausweis hat 0 Ausleihen")
    void anfangsNull() {
        Object karte = neu("Lena");
        assertEquals(0, rufe(karte, "getLoans"), "Ein neuer Ausweis hat 0 Ausleihen. getLoans() muss das Attribut loans zurückgeben.");
    }

    @Test
    @DisplayName("addLoan erhöht die Ausleihen um 1")
    void ausleihen() {
        Object karte = neu("Lena");
        rufe(karte, "addLoan");
        rufe(karte, "addLoan");
        assertEquals(2, rufe(karte, "getLoans"), "Nach zwei Aufrufen von addLoan() muss getLoans() 2 zurückgeben. Erhöhst du in addLoan() das Attribut loans um 1?");
    }

    @Test
    @DisplayName("Zwei Ausweise sind unabhängig")
    void zweiAusweise() {
        Object a = neu("Lena");
        Object b = neu("Jonas");
        rufe(a, "addLoan");
        assertEquals("Jonas", rufe(b, "getOwner"), "Der zweite Ausweis muss Jonas gehören.");
        assertEquals("Lena", rufe(a, "getOwner"), "Der erste Ausweis muss Lena gehören, auch nachdem ein zweiter gebaut wurde.");
        assertEquals(0, rufe(b, "getLoans"), "Der zweite Ausweis hat noch nichts ausgeliehen. Jedes Objekt hat sein eigenes Attribut loans.");
    }
}
