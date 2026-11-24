package de.phl.crashkurs.k13.stage;

/**
 * Ein Song mit Titel und Beliebtheit (popularity).
 * Die Beliebtheit sagt, wie viele neue Fans eine Band gewinnt, wenn sie den Song spielt.
 * Diese Datei ist vorgegeben. Du änderst sie nicht.
 */
public class Song {

    private final String title;
    private final int popularity;

    /**
     * Erzeugt einen neuen Song.
     *
     * @param title      der Titel
     * @param popularity die Beliebtheit
     */
    public Song(final String title, final int popularity) {
        this.title = title;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public int getPopularity() {
        return popularity;
    }

    /**
     * Gibt einen Text mit Titel und Beliebtheit zurück.
     *
     * @return zum Beispiel "Song: Summer Lights, Beliebtheit: 150"
     */
    public String getInfo() {
        return "Song: " + title + ", Beliebtheit: " + popularity;
    }
}
