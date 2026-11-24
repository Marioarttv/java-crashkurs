package de.phl.crashkurs.k13.stage;

/**
 * Eine Band mit Name, Musikrichtung (genre) und Zahl der Fans.
 * Diese Datei ist vorgegeben. Du änderst sie nicht.
 */
public class Band {

    private final String name;
    private final String genre;
    private int fans;

    /**
     * Erzeugt eine neue Band.
     *
     * @param name  der Name der Band
     * @param genre die Musikrichtung
     * @param fans  die Zahl der Fans am Anfang
     */
    public Band(final String name, final String genre, final int fans) {
        this.name = name;
        this.genre = genre;
        this.fans = fans;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getFans() {
        return fans;
    }

    /**
     * Die Band spielt einen Song. Dabei gewinnt sie so viele neue Fans, wie der Song beliebt ist.
     *
     * @param song der Song, den die Band spielt
     */
    public void play(final Song song) {
        fans = fans + song.getPopularity();
        System.out.println(name + " spielt " + song.getTitle() + ".");
    }

    /**
     * Gibt einen Text mit Name, Musikrichtung und Fans zurück.
     *
     * @return zum Beispiel "Band: The Lindens, Genre: Indie, Fans: 1200"
     */
    public String getInfo() {
        return "Band: " + name + ", Genre: " + genre + ", Fans: " + fans;
    }
}
