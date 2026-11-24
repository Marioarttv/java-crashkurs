# Java-Crashkurs: das Übungsprojekt

**Zur Webseite mit allen Kapiteln: https://marioarttv.github.io/java-crashkurs/**

Hier schreibst du die Aufgaben aus dem Crashkurs selbst. Die Erklärungen, Beispiele zum Durchklicken und die Lösungen stehen auf der Webseite. In diesem Projekt liegt der Code dazu.

Java läuft auf deinem Rechner nicht? Dann nimm einen Codespace: Klick oben auf **Code**, dann auf **Codespaces** und auf **Create codespace on main**. Das Projekt läuft dann im Browser, ohne Installation.

Du arbeitest in deiner eigenen Kopie. Hier kannst du nichts kaputt machen, und niemand bewertet deine Versuche.

## So holst du dir das Projekt

1. Klick oben auf dieser Seite auf den grünen Knopf **Code** und kopiere die Adresse.
2. Öffne VS Code. Drück `F1`, tippe `Git: Clone`, wähle den Befehl aus und füge die Adresse ein.
3. Wähl einen Ordner, in dem das Projekt liegen soll, und klick danach auf **Öffnen**.
4. Warte, bis unten links in VS Code nichts mehr lädt. Beim ersten Mal dauert das ein paar Minuten, weil Java und Gradle (das Werkzeug für die Tests) eingerichtet werden.

## So ist das Projekt aufgebaut

| Ordner | Inhalt |
|---|---|
| `src/main/java/de/phl/crashkurs/k01` … `k13` | Die Aufgaben, ein Ordner je Kapitel. Hier schreibst du deinen Code. |
| `src/test/java/de/phl/crashkurs/k01` … `k13` | Die Tests, ein Ordner je Kapitel. Diese Dateien änderst du nicht. |

Jedes Kapitel hat einen eigenen Ordner: `k01` gehört zu Kapitel 1, `k02` zu Kapitel 2 und so weiter.

| Kapitel | Thema |
|---|---|
| 1 | Dein erstes Programm |
| 2 | Variablen und Rechnen |
| 3 | Methoden |
| 4 | Klassen und Objekte |
| 5 | Objekte benutzen |
| 6 | static: einmal für alle |
| 7 | Entscheiden mit if und else |
| 8 | Auswählen mit switch und enum |
| 9 | Wiederholen mit while |
| 10 | Zählen mit for, Arrays und Listen |
| 11 | Exceptions |
| 12 | Wenn es rot wird |
| 13 | Probelauf auf Zeit |

In jeder Aufgabendatei steht ein Hinweis mit `TODO` (ein Kommentar im Code). Genau dort schreibst du deine Lösung hin.

## So prüfst du deine Lösung

Zu jeder Aufgabe gibt es einen Test, also ein Prüfprogramm. Der Test ruft deinen Code mit mehreren Beispielen auf und vergleicht die Ergebnisse mit der Vorgabe.

**In VS Code:** Öffne die Testdatei, zum Beispiel `ExamTest.java`. Klick auf den grünen Pfeil links neben `class ExamTest`.

**Im Terminal:**

```
./gradlew test --tests "*k07.ExamTest"
```

Unter Windows schreibst du `.\gradlew.bat` statt `./gradlew`.

Grün heißt: Alle Beispiele in diesem Test stimmen. Rot heißt: Mindestens ein Beispiel liefert noch nicht das erwartete Ergebnis. Lies die Meldung dazu. Sie erklärt dir auf Deutsch, welcher Fall noch nicht stimmt.

Am Anfang sind fast alle Tests rot. Das ist normal, denn die Aufgaben sind noch nicht gelöst.

## Wenn im Code etwas rot unterstrichen ist

Eine rote Wellenlinie bedeutet: Java versteht eine Stelle in deinem Code noch nicht. Solange irgendwo im Projekt etwas rot unterstrichen ist, kann kein Test starten. Behebe deshalb zuerst die rote Stelle und starte den Test danach neu.

Fahr mit der Maus über die rote Stelle. VS Code zeigt dir dann eine Fehlermeldung. Die häufigsten Meldungen erklärt die Webseite im jeweiligen Kapitel.

## Drei Regeln, die dir Ärger ersparen

1. Ändere nur das, was zwischen den geschweiften Klammern einer Methode (eines benannten Programmteils) steht. Die Zeile mit dem Namen der Methode, zum Beispiel `public void pay(int amount) {`, bleibt unverändert.
2. Ändere keine Testdateien.
3. Starte den Test nach jeder kleinen Änderung. So siehst du sofort, ob dein letzter Schritt gestimmt hat.
