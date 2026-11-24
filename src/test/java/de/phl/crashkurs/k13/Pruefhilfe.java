package de.phl.crashkurs.k13;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Hilfsmethoden für die Tests des Probelaufs (Kapitel 13). Diese Datei änderst du nicht.
 */
public final class Pruefhilfe {

    /** So lange darf ein Programmteil im Test höchstens laufen. */
    public static final Duration ZEITLIMIT = Duration.ofSeconds(5);
    private static final int HOECHSTENS_ZEICHEN = 200_000;

    private Pruefhilfe() {
    }

    /** Etwas, das ausgeführt wird und dabei eine Exception werfen darf. */
    public interface Aktion {
        void los() throws Exception;
    }

    /**
     * Führt die Aktion aus und gibt zurück, was sie mit System.out ausgegeben hat.
     * Läuft die Aktion länger als 5 Sekunden, schlägt der Test mit einem Hinweis auf eine Endlosschleife fehl.
     * Der abgebrochene Programmteil wird beim nächsten Versuch, etwas auszugeben, gestoppt.
     */
    public static String ausgabe(String was, Aktion aktion) {
        Verteiler v = verteiler();
        Begrenzt puffer = new Begrenzt();
        Throwable[] fehler = new Throwable[1];
        Thread t = new Thread(() -> {
            try {
                aktion.los();
            } catch (Throwable e) {
                fehler[0] = e;
            }
        }, "k13-pruefung");
        t.setDaemon(true);
        v.ziele.put(t, puffer);
        t.start();
        try {
            t.join(ZEITLIMIT.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (t.isAlive()) {
            v.gesperrt.add(t);
            v.ziele.remove(t);
            t.interrupt();
            fail(was + " läuft länger als 5 Sekunden. Vermutlich endet eine Schleife nie: Ändert sich im Block etwas, das die Bedingung irgendwann falsch macht?");
        }
        v.ziele.remove(t);
        Throwable f = fehler[0];
        if (f instanceof InvocationTargetException) {
            f = f.getCause();
        }
        if (f instanceof AssertionError) {
            throw (AssertionError) f;
        }
        if (f != null) {
            throw new AssertionError(was + " bricht mit einer Exception ab: " + f, f);
        }
        return puffer.text().replace("\r\n", "\n");
    }

    private static synchronized Verteiler verteiler() {
        if (System.out instanceof PrintStream ps && Verteiler.AKTIV != null && ps == Verteiler.AKTIV.strom) {
            return Verteiler.AKTIV;
        }
        Verteiler v = new Verteiler(System.out);
        v.strom = new PrintStream(v, true, StandardCharsets.UTF_8);
        Verteiler.AKTIV = v;
        System.setOut(v.strom);
        return v;
    }

    /** Ruft die main-Methode einer Klasse auf und gibt die Ausgabe zurück. */
    public static String ausgabeVonMain(Class<?> klasse) {
        Method main = mainMethode(klasse);
        return ausgabe("Die main-Methode von " + klasse.getSimpleName(), () -> main.invoke(null, (Object) new String[0]));
    }

    /** Sucht eine Klasse über ihren Namen. Gibt es sie nicht, schlägt der Test mit einem Hinweis fehl. */
    public static Class<?> klasse(String paket, String name) {
        try {
            return Class.forName(paket + "." + name);
        } catch (ClassNotFoundException e) {
            fail("Die Klasse " + name + " gibt es im Paket " + paket + " noch nicht. Leg die Datei " + name
                    + ".java im Ordner src/main/java/" + paket.replace('.', '/') + " an. Prüfe: Heißt die Klasse genau wie die Datei, und passt die package-Zeile zum Ordner?");
            return null;
        }
    }

    /** Gibt die Methode public static void main(String[] args) zurück oder lässt den Test mit einem Hinweis fehlschlagen. */
    public static Method mainMethode(Class<?> klasse) {
        try {
            Method m = klasse.getDeclaredMethod("main", String[].class);
            if (!Modifier.isStatic(m.getModifiers()) || !Modifier.isPublic(m.getModifiers()) || m.getReturnType() != void.class) {
                fail("Die Methode main in " + klasse.getSimpleName() + " muss genau so beginnen: public static void main(String[] args)");
            }
            m.setAccessible(true);
            return m;
        } catch (NoSuchMethodException e) {
            fail("In der Klasse " + klasse.getSimpleName() + " fehlt die Methode public static void main(String[] args).");
            return null;
        }
    }

    /** Liest eine Quelldatei (Pfad ab src/main/java) und entfernt alle Kommentare. Auskommentierter Code zählt also nicht. */
    public static String quelltext(String pfadAbJava) {
        Path p = Path.of("src/main/java", pfadAbJava);
        Path ordner = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        while (ordner != null && !Files.exists(ordner.resolve("src/main/java").resolve(pfadAbJava))) {
            ordner = ordner.getParent();
        }
        if (ordner != null) {
            p = ordner.resolve("src/main/java").resolve(pfadAbJava);
        }
        try {
            String s = Files.readString(p, StandardCharsets.UTF_8).replace("\r\n", "\n");
            return ohneKommentare(s);
        } catch (IOException e) {
            fail("Die Datei " + p + " wurde nicht gefunden.");
            return "";
        }
    }

    /** Der Inhalt einer Methode im Quelltext: alles zwischen ihrer öffnenden und der passenden schließenden Klammer. */
    public static String methodenInhalt(String quelltext, String methodenName) {
        Matcher m = Pattern.compile("\\b" + Pattern.quote(methodenName) + "\\s*\\([^)]*\\)\\s*\\{").matcher(quelltext);
        if (!m.find()) {
            fail("Die Methode " + methodenName + " wurde im Quelltext nicht gefunden. Hast du ihren Kopf verändert?");
        }
        int tiefe = 1;
        int i = m.end();
        while (i < quelltext.length() && tiefe > 0) {
            char c = quelltext.charAt(i);
            if (c == '{') {
                tiefe++;
            } else if (c == '}') {
                tiefe--;
            }
            i++;
        }
        return quelltext.substring(m.end(), i - 1);
    }

    /** Wie oft kommt der reguläre Ausdruck im Text vor? */
    public static int anzahl(String text, String regex) {
        Matcher m = Pattern.compile(regex).matcher(text);
        int n = 0;
        while (m.find()) {
            n++;
        }
        return n;
    }

    static String ohneKommentare(String s) {
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '"') {
                int j = i + 1;
                while (j < s.length() && s.charAt(j) != '"' && s.charAt(j) != '\n') {
                    if (s.charAt(j) == '\\') {
                        j++;
                    }
                    j++;
                }
                out.append(s, i, Math.min(j + 1, s.length()));
                i = j + 1;
            } else if (s.startsWith("//", i)) {
                while (i < s.length() && s.charAt(i) != '\n') {
                    i++;
                }
            } else if (s.startsWith("/*", i)) {
                int e = s.indexOf("*/", i + 2);
                i = e < 0 ? s.length() : e + 2;
                out.append(' ');
            } else {
                out.append(c);
                i++;
            }
        }
        return out.toString();
    }

    /** Leitet jede Ausgabe an das Ziel des Programmteils weiter, der sie schreibt. Gesperrte Programmteile werden gestoppt. */
    private static final class Verteiler extends OutputStream {
        static Verteiler AKTIV;
        final PrintStream original;
        final Map<Thread, OutputStream> ziele = new ConcurrentHashMap<>();
        final Set<Thread> gesperrt = ConcurrentHashMap.newKeySet();
        PrintStream strom;

        Verteiler(PrintStream original) {
            this.original = original;
        }

        private OutputStream ziel() {
            Thread t = Thread.currentThread();
            if (gesperrt.contains(t)) {
                throw new IllegalStateException("Abgebrochen: lief länger als 5 Sekunden");
            }
            OutputStream z = ziele.get(t);
            return z != null ? z : original;
        }

        @Override
        public void write(int b) throws IOException {
            ziel().write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            ziel().write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            ziel().flush();
        }
    }

    /** Ein Puffer, der nach 200 000 Zeichen nichts mehr aufnimmt (falls eine Schleife endlos ausgibt). */
    private static final class Begrenzt extends OutputStream {
        private final ByteArrayOutputStream puffer = new ByteArrayOutputStream();

        @Override
        public synchronized void write(int b) {
            if (puffer.size() < HOECHSTENS_ZEICHEN) {
                puffer.write(b);
            }
        }

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            int platz = HOECHSTENS_ZEICHEN - puffer.size();
            if (platz > 0) {
                puffer.write(b, off, Math.min(platz, len));
            }
        }

        synchronized String text() {
            return puffer.toString(StandardCharsets.UTF_8);
        }
    }
}
