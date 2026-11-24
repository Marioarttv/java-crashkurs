package de.phl.crashkurs.k13.jam;

/**
 * Der Zustand eines Musikers oder eines Publikums.
 * READY: hat noch genug Energie (mehr als 10). BREAK: Energie von 0 bis 10, braucht eine Pause. DONE: Energie unter 0, hat Schluss.
 * Diese Datei ist vorgegeben. Du änderst sie nicht.
 */
public enum Status {
    READY, BREAK, DONE
}
