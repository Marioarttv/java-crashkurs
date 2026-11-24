/* Java-Crashkurs: gemeinsame Technik aller Seiten. Keine Bibliotheken, kein Build-Schritt.
   Eine Kapitelseite lädt diese Datei und ruft am Ende Kurs.start({ ... }) auf.
   Die Schnittstelle ist in ../README.md (Abschnitt „Technik“) beschrieben. */
var Kurs = (function () {
  'use strict';

  /* ---------- Kapitelliste: die einzige Stelle, an der Kapitel stehen ---------- */

  var TEILE = [
    { id: 'A', titel: 'Die ersten Schritte' },
    { id: 'B', titel: 'Klassen und Objekte' },
    { id: 'C', titel: 'Den Ablauf steuern' },
    { id: 'D', titel: 'Wenn etwas schiefgeht' },
    { id: 'E', titel: 'Probe' }
  ];
  var KAPITEL = [
    { nr: 1, titel: 'Dein erstes Programm', teil: 'A', aufgaben: 3, sichtbar: true },
    { nr: 2, titel: 'Variablen und Rechnen', teil: 'A', aufgaben: 3, sichtbar: true },
    { nr: 3, titel: 'Methoden', teil: 'A', aufgaben: 3, sichtbar: true },
    { nr: 4, titel: 'Klassen und Objekte', teil: 'B', aufgaben: 3, sichtbar: true },
    { nr: 5, titel: 'Objekte benutzen', teil: 'B', aufgaben: 3, sichtbar: true },
    { nr: 6, titel: 'static: einmal für alle', teil: 'B', aufgaben: 2, sichtbar: true },
    { nr: 7, titel: 'Entscheiden mit if und else', teil: 'C', aufgaben: 3, sichtbar: true },
    { nr: 8, titel: 'Auswählen mit switch und enum', teil: 'C', aufgaben: 3, sichtbar: true },
    { nr: 9, titel: 'Wiederholen mit while', teil: 'C', aufgaben: 3, sichtbar: true },
    { nr: 10, titel: 'Zählen mit for, Arrays und Listen', teil: 'C', aufgaben: 3, sichtbar: true },
    { nr: 11, titel: 'Exceptions', teil: 'D', aufgaben: 2, sichtbar: true },
    { nr: 12, titel: 'Wenn es rot wird', teil: 'D', aufgaben: 3, sichtbar: true },
    { nr: 13, titel: 'Probelauf auf Zeit', teil: 'E', aufgaben: 3, sichtbar: true }
  ];
  function zwei(n) { return (n < 10 ? '0' : '') + n; }
  KAPITEL.forEach(function (k) { k.datei = 'kapitel-' + zwei(k.nr) + '.html'; k.paket = 'k' + zwei(k.nr); });

  /* Ausgeblendete Kapitel zeigt die Liste nur mit ?alle in der Adresse (zum Prüfen). */
  var ALLE = /[?&]alle\b/.test(location.search);
  function sichtbar(k, hier) { return k.sichtbar || ALLE || (hier && k.nr === hier); }

  /* ---------- kleine Helfer ---------- */

  function esc(s) { return String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;'); }
  function el(tag, cls, html) {
    var e = document.createElement(tag);
    if (cls) e.className = cls;
    if (html != null) e.innerHTML = html;
    return e;
  }
  function knopf(cls, html, aktion) { var b = el('button', cls, html); b.type = 'button'; if (aktion) b.onclick = aktion; return b; }
  function urteil(ausdruck, wert) {
    return '<code>' + esc(ausdruck) + '</code> ist <span class="urteil ' + (wert ? 'wahr' : 'falsch') + '">' + (wert ? 'wahr' : 'falsch') + '</span>';
  }
  function merken(key, val) { try { localStorage.setItem(key, val); } catch (e) {} }
  function holen(key) { try { return localStorage.getItem(key); } catch (e) { return null; } }
  var ZAHLWORT = ['keine', 'eine', 'zwei', 'drei', 'vier', 'fünf', 'sechs'];
  function zahlwort(n) { return ZAHLWORT[n] || String(n); }
  function liste(x) { return x == null ? [] : Array.isArray(x) ? x : [x]; }
  function wertVon(f, v) { return typeof f === 'function' ? f(v) : f; }

  /* ---------- Code färben ---------- */

  var FARBEN = ['violett', 'orange', 'blau', 'pink', 'tuerkis', 'typ'];
  /* Feste Bedeutung im ganzen Kurs. Abweichungen nur laut Tabelle in KURSBLATT.md. */
  var LEGENDE_STANDARD = { violett: 'Schlüsselwort', orange: 'Bedingung', blau: 'Block', pink: 'Name', tuerkis: 'Wert', typ: 'Typ' };
  var legendeKapitel = {};

  /* Markierung im Zeilentext: «farbe:Text». Erlaubte Farben siehe FARBEN. */
  var MARK = /«(violett|orange|blau|pink|tuerkis|typ):([^»]*)»/g;
  function rein(z) { return String(z).replace(MARK, '$2'); }

  var KONTROLLWORT = 'if|else|while|for|switch|case|default|do|break|continue|try|catch|finally';
  var SCHLUESSEL = new RegExp('(//.*$)|("(?:[^"\\\\]|\\\\.)*")|\\b(' + KONTROLLWORT + ')\\b|\\b(int|double|long|char|String|boolean|return|public|private|protected|static|final|void|new|class|enum|interface|package|import|this|null|true|false|throw|throws)\\b', 'g');
  function grund(stueck) {
    return esc(stueck).replace(SCHLUESSEL, function (m, cm, str, wahl, kw) {
      if (cm) return '<span class="cm">' + cm + '</span>';
      if (str) return '<span class="str">' + str + '</span>';
      if (wahl) return '<span class="r-violett">' + wahl + '</span>';
      return '<span class="kw">' + kw + '</span>';
    });
  }
  function markiert(s) {
    var out = '', last = 0;
    s.replace(MARK, function (m, f, inhalt, pos) {
      out += grund(s.slice(last, pos)) + '<span class="r-' + f + '">' + grund(inhalt) + '</span>';
      last = pos + m.length;
      return m;
    });
    return out + grund(s.slice(last));
  }
  var BLOCKWORT = /\b(if|else|while|for|switch|do|try|catch|finally)\b/;
  /* Färbt eine Zeile. Kontrollwörter violett; die runde Klammer hinter if und while orange
     (nur in Zeilen ohne «»-Markierung); die geschweiften Klammern eines Kontrollblocks blau. */
  function farbe(zeile, info) {
    var wahl = BLOCKWORT.test(rein(zeile));
    info = info || { auf: wahl, zu: wahl };
    var vorn = '', hinten = '', m;
    if (info.zu && (m = /^(\s*)\}/.exec(zeile))) { vorn = m[1] + '<span class="r-blau">}</span>'; zeile = zeile.slice(m[0].length); }
    if (info.auf && (m = /\{(\s*)$/.exec(zeile))) { hinten = '<span class="r-blau">{</span>' + m[1]; zeile = zeile.slice(0, m.index); }
    if (zeile.indexOf('«') >= 0) return vorn + markiert(zeile) + hinten;
    var mitte = grund(zeile), wo = /\b(if|while)\b/.exec(zeile);
    if (wo) {
      var a = zeile.indexOf('(', wo.index), tiefe = 0, e = -1;
      for (var i = a; a >= 0 && i < zeile.length; i++) {
        if (zeile[i] === '(') tiefe++;
        if (zeile[i] === ')' && --tiefe === 0) { e = i; break; }
      }
      if (e > 0) mitte = grund(zeile.slice(0, a)) + '<span class="r-orange">' + grund(zeile.slice(a, e + 1)) + '</span>' + grund(zeile.slice(e + 1));
    }
    return vorn + mitte + hinten;
  }
  /* Baut einen Codeblock. opts: { start: erste Zeilennummer, klasse: zusätzliche CSS-Klasse } */
  function codeBlock(zeilen, opts) {
    if (typeof opts === 'string') opts = { klasse: opts };
    opts = opts || {};
    var div = el('div', 'code' + (opts.klasse ? ' ' + opts.klasse : '')), stapel = [];
    var start = opts.start || 1;
    zeilen.forEach(function (z, i) {
      var r = rein(z), wahl = BLOCKWORT.test(r), info = { auf: false, zu: false };
      var schliesst = /^\s*\}/.test(r), oeffnet = /\{\s*$/.test(r);
      if (schliesst) info.zu = stapel.pop() === 'wahl';
      var innen = !schliesst && stapel.length > 0 && stapel[stapel.length - 1] === 'wahl';
      if (oeffnet) { info.auf = wahl; stapel.push(wahl ? 'wahl' : 'sonst'); }
      var ln = el('div', 'ln' + (innen ? ' im-block' : ''));
      ln.innerHTML = '<span class="nr">' + (start + i) + '</span><span class="txt">' + (farbe(z, info) || ' ') + '</span>';
      div.appendChild(ln);
    });
    return div;
  }
  /* Legende mit den Farben, die in den gegebenen Elementen vorkommen. */
  function legende(elemente, eigene) {
    var namen = {}, k;
    for (k in LEGENDE_STANDARD) namen[k] = LEGENDE_STANDARD[k];
    for (k in legendeKapitel) namen[k] = legendeKapitel[k];
    if (eigene) for (k in eigene) namen[k] = eigene[k];
    var ul = el('ul', 'legende'), n = 0;
    FARBEN.forEach(function (f) {
      var da = liste(elemente).some(function (e) { return e.querySelector('.r-' + f); });
      if (da && namen[f]) { ul.appendChild(el('li', 'f-' + f, '<i></i>' + namen[f])); n++; }
    });
    return n ? ul : null;
  }

  /* ---------- Schritt-für-Schritt-Läufe ---------- */

  /* Werte im Speicher:
     Zahl oder Text  wird so gezeigt, wie er ist. Strings mit Anführungszeichen übergeben ('"Bello"'), double als Text ('5.0').
     null/undefined  „kein Wert“ (Variable angelegt, aber noch nichts gespeichert).
     Kurs.NULL       Javas null (die Variable zeigt auf kein Objekt).
     { ref: '1' }    zeigt auf die Karte mit der Nummer 1.
     { array: [..] } bzw. { liste: [..] }  Reihe von Fächern, nummeriert ab 0 (Elemente dürfen { ref } sein). */
  var NULL = { ref: null };
  var laeufe = [];
  var SCHRITT_FELDER = ['z', 'datei', 'sagt', 'skip', 'vars', 'bereiche', 'laeuft', 'objekte', 'statisch', 'neu', 'aus', 'gibt', 'abbruch', 'teil'];

  function zeigeWert(w, neuZellen) {
    if (w === null || w === undefined) return { leer: true, html: 'kein Wert' };
    if (typeof w === 'object' && 'ref' in w) {
      if (w.ref === null) return { html: 'null' };
      return { html: '<span class="zeiger">→ <span class="marke">' + esc(w.ref) + '</span></span>' };
    }
    if (typeof w === 'object' && (w.array || w.liste)) {
      var werte = w.array || w.liste, html = '<span class="faecher">';
      if (!werte.length) html += '<span class="leer-liste">leer</span>';
      werte.forEach(function (x, i) {
        html += '<span' + (neuZellen && neuZellen.indexOf(i) >= 0 ? ' class="neu"' : '') + '>' + zeigeWert(x).html + '<small>' + i + '</small></span>';
      });
      return { html: html + '</span>', reihe: true };
    }
    return { html: esc(String(w)) };
  }
  function textVon(w) {
    if (w === null || w === undefined) return 'kein Wert';
    if (typeof w === 'object' && 'ref' in w) return w.ref === null ? 'null' : '->' + w.ref;
    if (typeof w === 'object' && (w.array || w.liste)) return (w.array ? 'array' : 'liste') + '[' + (w.array || w.liste).map(textVon).join(',') + ']';
    return String(w);
  }
  /* Alle Werte eines Schritts als flache Liste Schlüssel → Text (für „neu“ und die Datenprüfung). */
  function flach(s) {
    var m = {};
    function wert(k, w) {
      m[k] = textVon(w);
      if (w && typeof w === 'object' && (w.array || w.liste)) (w.array || w.liste).forEach(function (x, i) { m[k + '[' + i + ']'] = textVon(x); });
    }
    if (s.bereiche) s.bereiche.forEach(function (b, i) { liste(b.vars).forEach(function (v) { wert(i + '/' + v[0], v[1]); }); });
    else liste(s.vars).forEach(function (v) { wert('0/' + v[0], v[1]); });
    liste(s.objekte).forEach(function (o) { m['#' + o.id] = o.klasse || o.titel; liste(o.attr).forEach(function (a) { wert(o.id + '.' + a[0], a[1]); }); if (o.reihe) wert(o.id, { array: o.reihe }); });
    liste(s.statisch).forEach(function (o) { liste(o.attr).forEach(function (a) { wert(o.klasse + '.' + a[0], a[1]); }); });
    return m;
  }

  /* Zustandsschreiber: baut die Schritte eines Laufs, ohne dass man den Speicher in jedem Schritt neu aufschreiben muss.
     var a = Kurs.ablauf();
     a.setze('points', 62);                 Variable im Bereich, der gerade läuft
     a.schritt(1, 'Text …');                 Momentaufnahme: Zeile 1 (der aktuellen Datei) ist gelb
     a.bereich('Dog(…)', 'Dog.java');        neuer Bereich (Methode/Konstruktor läuft); die Aufrufstelle wartet
     a.verlasse();                           Bereich endet, zurück zur wartenden Stelle
     a.objekt('1', 'Dog', [['name', '"Bello"']]);  a.attr('1', 'age', 13);
     a.statisch('Dog', 'count', 1);  a.aus('Text');  a.ausRot('Exception …');
     a.schritt(z, sagt, { skip: [..], abbruch: true, gibt: 7, neu: [..], datei: 'Main.java', teil: 'orange' });
     schritte: function (v) { …; return a.schritte; } */
  function ablauf() {
    var st = { bereiche: null, vars: [], objekte: [], statisch: [], aus: [], datei: null };
    var schritte = [], letzte = null;
    function kopie(x) { return JSON.parse(JSON.stringify(x)); }
    function aktVars() { return st.bereiche ? st.bereiche[st.bereiche.length - 1].vars : st.vars; }
    function setzeIn(liste_, name, wert) {
      for (var i = 0; i < liste_.length; i++) if (liste_[i][0] === name) { liste_[i][1] = wert; return; }
      liste_.push([name, wert]);
    }
    function objekt(id) { for (var i = 0; i < st.objekte.length; i++) if (st.objekte[i].id === id) return st.objekte[i]; throw new Error('ablauf: Objekt ' + id + ' gibt es nicht'); }
    var a = {
      schritte: schritte,
      setze: function (name, wert) { setzeIn(aktVars(), name, wert); return a; },
      loesche: function (name) { var l = aktVars(); for (var i = 0; i < l.length; i++) if (l[i][0] === name) l.splice(i, 1); return a; },
      /* aufrufZeile: Zeile, die wartet (Standard: Zeile des letzten Schritts, wenn er in derselben Datei stand). */
      bereich: function (name, datei, aufrufZeile) {
        if (!st.bereiche) st.bereiche = [{ name: 'main', vars: st.vars }];
        var oben = st.bereiche[st.bereiche.length - 1];
        oben.datei = st.datei || (letzte && letzte.datei);
        if (aufrufZeile != null) oben.z = aufrufZeile;
        else if (letzte && letzte.datei === oben.datei) oben.z = letzte.z;
        else delete oben.z;
        st.bereiche.push({ name: name, vars: [] });
        if (datei) st.datei = datei;
        return a;
      },
      verlasse: function () {
        st.bereiche.pop();
        var oben = st.bereiche[st.bereiche.length - 1];
        if (oben.datei) st.datei = oben.datei;
        delete oben.z; delete oben.datei;
        return a;
      },
      datei: function (name) { st.datei = name; return a; },
      objekt: function (id, klasse, attr) { st.objekte.push({ id: String(id), klasse: klasse, attr: attr || [] }); return a; },
      reihe: function (id, titel, werte) { st.objekte.push({ id: String(id), titel: titel, reihe: werte || [] }); return a; },
      attr: function (id, name, wert) { setzeIn(objekt(String(id)).attr, name, wert); return a; },
      fach: function (id, i, wert) { objekt(String(id)).reihe[i] = wert; return a; },
      statisch: function (klasse, name, wert) {
        var k = st.statisch.filter(function (x) { return x.klasse === klasse; })[0];
        if (!k) { k = { klasse: klasse, attr: [] }; st.statisch.push(k); }
        setzeIn(k.attr, name, wert); return a;
      },
      aus: function (t) { st.aus.push(String(t)); return a; },
      ausRot: function (t) { st.aus.push({ rot: String(t) }); return a; },
      schritt: function (z, sagt, extra) {
        var s = { z: z, sagt: sagt };
        if (st.datei) s.datei = st.datei;
        if (st.bereiche) s.bereiche = kopie(st.bereiche); else s.vars = kopie(st.vars);
        if (st.objekte.length) s.objekte = kopie(st.objekte);
        if (st.statisch.length) s.statisch = kopie(st.statisch);
        if (st.aus.length) s.aus = kopie(st.aus);
        if (extra) for (var k in extra) { s[k] = extra[k]; if (k === 'datei') st.datei = extra[k]; }
        schritte.push(s); letzte = s;
        return a;
      }
    };
    return a;
  }

  function bauLauf(id, def) {
    var box = document.getElementById(id);
    if (!box) { fehlerMelden('Lauf ' + id + ': kein Element mit dieser id'); return; }
    box.classList.add('lauf');
    if (def.nebeneinander) box.classList.add('nebeneinander', 'breit');
    if (def.untereinander) box.classList.add('nebeneinander', 'untereinander');
    var ein = def.eingabe !== undefined ? def.eingabe : (def.label ? { art: 'zahl', label: def.label, start: def.start, proben: def.proben, min: def.min, max: def.max } : null);
    var pos = 0, schritte, dateien, gewaehlt = null;

    var eingabe = el('div', 'eingabe'), inp = null, chips = [];
    function wahlWert(w) { return w && typeof w === 'object' && 'wert' in w ? w.wert : w; }
    function wahlText(w) { return w && typeof w === 'object' && 'text' in w ? w.text : String(w); }
    if (ein && ein.art === 'zahl') {
      var lab = el('label', null, esc(ein.label) + ':'); lab.htmlFor = id + '-in';
      inp = document.createElement('input'); inp.type = 'number'; inp.id = id + '-in';
      if (ein.min != null) inp.min = ein.min;
      if (ein.max != null) inp.max = ein.max;
      eingabe.appendChild(lab); eingabe.appendChild(inp);
      liste(ein.proben).forEach(function (p) {
        var c = knopf('chip', esc(String(p)), function () { setze(p); });
        c.setAttribute('aria-label', ein.label + ' auf ' + p + ' setzen');
        eingabe.appendChild(c);
      });
      inp.onchange = function () { setze(inp.value); };
    } else if (ein && ein.art === 'wahl') {
      eingabe.appendChild(el('span', 'label', esc(ein.label) + ':'));
      ein.werte.forEach(function (w) {
        var c = knopf('chip', esc(wahlText(w)), function () { setze(wahlWert(w)); });
        c.setAttribute('aria-label', ein.label + ': ' + wahlText(w));
        c._wert = wahlWert(w); chips.push(c); eingabe.appendChild(c);
      });
    } else if (ein) fehlerMelden('Lauf ' + id + ': eingabe.art muss "zahl" oder "wahl" sein');

    var reiter = el('div', 'reiter'), dateiBox = el('div', 'dateien');
    var unten = el('div', 'unten'), links = el('div'), rechts = el('div');
    var speicher = el('div', 'speicher'), konsole = el('div', 'konsole');
    links.appendChild(el('h4', null, 'Im Speicher')); links.appendChild(speicher);
    rechts.appendChild(el('h4', null, 'Ausgabe')); rechts.appendChild(konsole);
    unten.appendChild(links); unten.appendChild(rechts);
    var sagt = el('div', 'sagt'); sagt.setAttribute('aria-live', 'polite');
    var knoepfe = el('div', 'knoepfe');
    var weiter = knopf('knopf haupt', 'Nächster Schritt'), zurueck = knopf('knopf', 'Zurück'), neu = knopf('knopf', 'Von vorn');
    var stand = el('span', 'stand');
    [weiter, zurueck, neu, stand].forEach(function (k) { knoepfe.appendChild(k); });
    box.innerHTML = '';
    if (ein) box.appendChild(eingabe);
    // Knöpfe über dem Code: beim Scrollen bleiben sie oben stehen (position: sticky), Code und Erklärung darunter.
    [knoepfe, reiter, dateiBox, unten, sagt].forEach(function (k) { box.appendChild(k); });

    function dateiIndex(d, s) {
      if (d == null) return 0;
      for (var i = 0; i < dateien.length; i++) if (dateien[i].name === d) return i;
      fehlerMelden('Lauf ' + id + ', Schritt ' + (s + 1) + ': Datei "' + d + '" gibt es nicht');
      return 0;
    }
    function pruefeDaten(v) {
      var wo = 'Lauf ' + id + (ein ? ' (' + ein.label + ' ' + v + ')' : '');
      schritte.forEach(function (s, i) {
        var w = wo + ', Schritt ' + (i + 1) + ': ';
        Object.keys(s).forEach(function (k) { if (SCHRITT_FELDER.indexOf(k) < 0) fehlerMelden(w + 'unbekanntes Feld "' + k + '"'); });
        var d = dateien[dateiIndex(s.datei, i)], n = d.code.length, start = d.start || 1;
        if (typeof s.z !== 'number' || s.z < start || s.z >= start + n) fehlerMelden(w + 'Zeile ' + s.z + ' gibt es in ' + (d.name || 'dem Code') + ' nicht (Zeilen zählen ab 1)');
        if (!s.sagt) fehlerMelden(w + 'sagt fehlt');
        var ids = liste(s.objekte).map(function (o) { return String(o.id); });
        var m = flach(s);
        Object.keys(m).forEach(function (k) { var r = /^->(.+)$/.exec(m[k]); if (r && ids.indexOf(r[1]) < 0) fehlerMelden(w + k + ' zeigt auf Karte ' + r[1] + ', die es nicht gibt'); });
        liste(s.aus).forEach(function (a) { if (typeof a !== 'string' && !(a && a.rot)) fehlerMelden(w + 'aus enthält etwas anderes als Text oder {rot}'); });
      });
    }
    function setze(v) {
      if (ein && ein.art === 'zahl') {
        v = Math.round(Number(v));
        if (isNaN(v)) v = ein.start;
        if (ein.min != null) v = Math.max(ein.min, v);
        if (ein.max != null) v = Math.min(ein.max, v);
        inp.value = v;
      }
      chips.forEach(function (c) { c.classList.toggle('an', c._wert === v); c.setAttribute('aria-pressed', c._wert === v ? 'true' : 'false'); });
      pos = 0; gewaehlt = null;
      try { schritte = def.schritte(v); } catch (e) { fehlerMelden('Lauf ' + id + ': schritte() bricht ab: ' + e.message); schritte = [{ z: 1, sagt: 'Fehler im Lauf' }]; }
      dateien = def.dateien ? wertVon(def.dateien, v) : [{ code: wertVon(def.code, v) }];
      if (!schritte || !schritte.length) { fehlerMelden('Lauf ' + id + ': keine Schritte'); schritte = [{ z: 1, sagt: 'Fehler im Lauf' }]; }
      dateiBox.innerHTML = ''; reiter.innerHTML = '';
      var bloecke = [];
      dateien.forEach(function (d, i) {
        var teil = el('div', 'datei');
        if (dateien.length > 1 || d.name) teil.appendChild(el('div', 'datei-name', esc(d.name || '')));
        var cb = codeBlock(d.code, { start: d.start }); bloecke.push(cb);
        teil.appendChild(cb); dateiBox.appendChild(teil);
        d._teil = teil; d._block = cb; d._kopf = teil.querySelector('.datei-name');
        if (dateien.length > 1) {
          d._reiter = knopf('chip', esc(d.name), function () { gewaehlt = i; zeigeDatei(); });
          reiter.appendChild(d._reiter);
        }
      });
      var lg = legende(bloecke, def.legende);
      if (lg) dateiBox.appendChild(lg);
      var gross = schritte.some(function (s) { return s.bereiche || s.objekte || s.statisch; });
      unten.classList.toggle('gross', gross || !!def.grosserSpeicher);
      pruefeDaten(v);
      zeige();
    }
    function zeigeDatei() {
      var di = gewaehlt != null ? gewaehlt : dateiIndex(schritte[pos].datei, pos);
      dateien.forEach(function (d, i) {
        d._teil.classList.toggle('sichtbar', i === di);
        if (d._reiter) { d._reiter.classList.toggle('an', i === di); d._reiter.setAttribute('aria-pressed', i === di ? 'true' : 'false'); }
      });
    }
    function zeige() {
      var s = schritte[pos], di = dateiIndex(s.datei, pos);
      var vorher = pos > 0 ? flach(schritte[pos - 1]) : {}, jetzt = flach(s);
      var neuAuto = Object.keys(jetzt).filter(function (k) { return vorher[k] !== jetzt[k]; });
      var wartet = {};
      liste(s.bereiche).forEach(function (b) { if (b.z != null) wartet[(b.datei || '') + ':' + b.z] = true; });
      dateien.forEach(function (d, i) {
        var skip = Array.isArray(s.skip) ? (i === di ? s.skip : []) : (s.skip && s.skip[d.name]) || [];
        var start = d.start || 1;
        d._block.querySelectorAll('.ln').forEach(function (ln, j) {
          var nr = start + j, hier = i === di && nr === s.z;
          ln.classList.toggle('cur', hier);
          ln.classList.toggle('wirft', hier && !!s.abbruch);
          ln.classList.toggle('skip', skip.indexOf(nr) >= 0 || (i === di && !!s.abbruch && nr > s.z));
          ln.classList.toggle('wartet', !hier && !!wartet[(d.name || '') + ':' + nr]);
          if (hier && s.teil) ln.setAttribute('data-teil', s.teil); else ln.removeAttribute('data-teil');
        });
        if (d._kopf) d._kopf.classList.toggle('aktiv', dateien.length > 1 && i === di);
      });
      gewaehlt = null; zeigeDatei();
      zeichneSpeicher(s, s.neu != null ? null : neuAuto);
      var aus = liste(s.aus);
      if (aus.length) {
        konsole.className = 'konsole';
        konsole.innerHTML = aus.map(function (a) { return typeof a === 'object' ? '<span class="rot">' + esc(a.rot) + '</span>' : esc(a); }).join('\n');
      } else { konsole.className = 'konsole leer'; konsole.textContent = 'noch nichts'; }
      sagt.innerHTML = '<p>' + s.sagt + '</p>';
      stand.textContent = 'Schritt ' + (pos + 1) + ' von ' + schritte.length;
      zurueck.disabled = pos === 0;
      weiter.disabled = pos === schritte.length - 1;
    }
    function zeichneSpeicher(s, neuAuto) {
      /* „neu“ von Hand: Namen wie 'points', '1.age', 'Dog.count', 'names[2]'. Sonst automatisch: alles, was sich geändert hat. */
      var handNeu = liste(s.neu);
      function istNeu(schluessel, kurz) { return neuAuto ? neuAuto.indexOf(schluessel) >= 0 : handNeu.indexOf(kurz) >= 0; }
      function zellen(schluessel, kurz) {
        var r = [];
        if (neuAuto) neuAuto.forEach(function (k) { var m = /^(.+)\[(\d+)\]$/.exec(k); if (m && m[1] === schluessel) r.push(Number(m[2])); });
        else handNeu.forEach(function (n) { var m = /^(.+)\[(\d+)\]$/.exec(n); if (m && m[1] === kurz) r.push(Number(m[2])); });
        return r;
      }
      function varKarte(bi, v) {
        var key = bi + '/' + v[0], z = zeigeWert(v[1], zellen(key, v[0]));
        var d = el('div', 'var' + (z.leer ? ' leer' : '') + (istNeu(key, v[0]) ? ' neu' : ''));
        d.appendChild(el('span', 'name', esc(v[0])));
        d.appendChild(el(z.reihe ? 'div' : 'span', 'wert', z.html));
        return d;
      }
      function karte(o, istKlasse) {
        var key = istKlasse ? o.klasse : String(o.id);
        var k = el('div', 'karte' + (istKlasse ? ' klasse' : '') + (!istKlasse && istNeu('#' + key, key) ? ' neu' : ''));
        k.appendChild(el('div', 'kopf-k', istKlasse ? esc(o.klasse) + ' <small>gehört der Klasse</small>' : '<span class="marke">' + esc(o.id) + '</span>' + esc(o.klasse || o.titel || '')));
        if (o.reihe) {
          var z = zeigeWert({ array: o.reihe }, zellen(key, key));
          k.appendChild(el('div', 'var-reihe', z.html));
        }
        liste(o.attr).forEach(function (a) {
          var ak = key + '.' + a[0], z2 = zeigeWert(a[1], zellen(ak, ak));
          k.appendChild(el('div', 'attr' + (istNeu(ak, ak) ? ' neu' : ''), '<span class="an">' + esc(a[0]) + '</span><span class="aw">' + z2.html + '</span>'));
        });
        return k;
      }
      speicher.innerHTML = '';
      if (s.bereiche) {
        var teil = el('div', 'speicher-teil'), reihe = el('div', 'speicher');
        teil.appendChild(el('h5', null, 'Variablen (ein Bereich je Methode, die gerade läuft oder wartet)'));
        var laeuft = s.laeuft != null ? s.laeuft : s.bereiche.length - 1;
        s.bereiche.forEach(function (b, i) {
          var bd = el('div', 'bereich' + (i === laeuft ? ' laeuft' : '') + (liste(b.vars).length ? '' : ' leer'));
          bd.appendChild(el('div', 'titel', esc(b.name) + (i !== laeuft && b.z != null ? ' · wartet in Zeile ' + b.z : '')));
          var vs = el('div', 'vars');
          liste(b.vars).forEach(function (v) { vs.appendChild(varKarte(i, v)); });
          bd.appendChild(vs);
          reihe.appendChild(bd);
        });
        if (s.gibt != null) reihe.appendChild(el('div', 'rueckgabe', 'gibt zurück: ' + zeigeWert(s.gibt).html));
        teil.appendChild(reihe); speicher.appendChild(teil);
      } else {
        liste(s.vars).forEach(function (v) { speicher.appendChild(varKarte(0, v)); });
        if (s.gibt != null) speicher.appendChild(el('div', 'rueckgabe', 'gibt zurück: ' + zeigeWert(s.gibt).html));
      }
      if (s.objekte && s.objekte.length) {
        var ot = el('div', 'speicher-teil');
        ot.appendChild(el('h5', null, 'Objekte'));
        var karten = el('div', 'karten');
        s.objekte.forEach(function (o) { karten.appendChild(karte(o, false)); });
        ot.appendChild(karten); speicher.appendChild(ot);
      }
      if (s.statisch && s.statisch.length) {
        var kt = el('div', 'speicher-teil');
        kt.appendChild(el('h5', null, 'static: gehört der Klasse, gibt es nur einmal'));
        var kk = el('div', 'karten');
        s.statisch.forEach(function (o) { kk.appendChild(karte(o, true)); });
        kt.appendChild(kk); speicher.appendChild(kt);
      }
    }
    weiter.onclick = function () { if (pos < schritte.length - 1) { pos++; zeige(); } };
    zurueck.onclick = function () { if (pos > 0) { pos--; zeige(); } };
    neu.onclick = function () { pos = 0; zeige(); };
    setze(ein ? (ein.art === 'wahl' && ein.start === undefined ? wahlWert(ein.werte[0]) : ein.start) : null);

    laeufe.push({
      id: id, box: box, setze: setze,
      proben: ein ? (ein.art === 'wahl' ? ein.werte.map(wahlWert) : liste(ein.proben)) : [null],
      schritteFuer: function (v) { return def.schritte(v); },
      dateienFuer: function (v) { return def.dateien ? wertVon(def.dateien, v) : [{ code: wertVon(def.code, v) }]; },
      java: def.java
    });
  }

  /* ---------- Fragen ---------- */

  /* Feste, aber unregelmäßige Reihenfolge (jedes Mal gleich, nie schon richtig). */
  function mischen(n, saat) {
    var o = [], x = saat || 7, i, j, t;
    for (i = 0; i < n; i++) o.push(i);
    for (i = n - 1; i > 0; i--) { x = (x * 1103515245 + 12345) % 2147483648; j = x % (i + 1); t = o[i]; o[i] = o[j]; o[j] = t; }
    if (o.every(function (v, k) { return v === k; }) && n > 1) { t = o[0]; o[0] = o[1]; o[1] = t; }
    return o;
  }

  var fragen = [];
  function bauFragen(liste_, ziel) {
    var fragenBox = document.getElementById(ziel || 'fragen');
    if (!fragenBox) { if (liste_.length) fehlerMelden('Fragen: kein Element mit id="fragen"'); return; }
    liste_.forEach(function (f, n) {
      var box = el('div', 'frage');
      box.appendChild(el('h3', null, (n + 1) + '. ' + f.frage));
      if (f.text) box.appendChild(el('div', null, f.text));
      var rueck = el('div', 'rueck'); rueck.setAttribute('aria-live', 'polite');
      function melde(gut, text) { rueck.className = 'rueck ' + (gut ? 'richtig' : 'falsch'); rueck.innerHTML = text; }
      var api = { art: f.art, box: box, rueck: rueck, f: f };

      if (f.art === 'wahl') {
        if (f.code) box.appendChild(codeBlock(f.code));
        var reihe = el('div', 'antworten');
        if (!f.optionen.some(function (o) { return o.ok; })) fehlerMelden('Frage ' + (n + 1) + ': keine Antwort mit ok: true');
        f.optionen.forEach(function (o) {
          var b = knopf('antwort' + (f.code || f.mono ? '' : ' text'), esc(o.t), function () {
            reihe.querySelectorAll('.antwort').forEach(function (x) { x.classList.remove('richtig', 'falsch'); });
            b.classList.add(o.ok ? 'richtig' : 'falsch');
            melde(!!o.ok, o.warum);
          });
          reihe.appendChild(b);
        });
        box.appendChild(reihe);
      } else if (f.art === 'zeile') {
        var oks = liste(f.ok);
        var cb = codeBlock(f.code, { klasse: 'waehlbar', start: f.start });
        var start = f.start || 1;
        cb.querySelectorAll('.ln').forEach(function (ln, i) {
          var nr = start + i, gut = oks.indexOf(nr) >= 0;
          ln.tabIndex = 0; ln.setAttribute('role', 'button'); ln.setAttribute('aria-label', 'Zeile ' + nr + ' wählen');
          function wahl() {
            cb.querySelectorAll('.ln').forEach(function (x) { x.classList.remove('richtig', 'falsch'); });
            ln.classList.add(gut ? 'richtig' : 'falsch');
            var extra = f.warum && f.warum[nr];
            melde(gut, extra || (gut ? f.richtig : f.falsch));
          }
          ln.onclick = wahl;
          ln.onkeydown = function (e) { if (e.key === 'Enter' || e.key === ' ') { e.preventDefault(); wahl(); } };
        });
        box.appendChild(cb);
      } else if (f.art === 'luecke') {
        var zeilen = f.code || [f.vor + '___' + f.nach];
        var antworten = zeilen.join('\n').split('___').length - 1;
        var oksL = antworten > 1 ? f.ok : [f.ok];
        var cbl = codeBlock(zeilen.map(function (z) { return z.replace(/___/g, '\u0001'); }));
        var inputs = [], nrL = 0;
        cbl.querySelectorAll('.txt').forEach(function (t) {
          t.innerHTML = t.innerHTML.replace(/\u0001/g, function () {
            var breite = Math.max.apply(null, liste(oksL[nrL]).map(function (o) { return o.length; }).concat([2]));
            nrL++;
            return '<input class="luecke" style="width:' + (breite * 0.62 + 2.6) + 'em" aria-label="Lücke ' + nrL + '" maxlength="' + (breite + 12) + '">';
          });
        });
        cbl.querySelectorAll('input').forEach(function (i) { inputs.push(i); });
        box.appendChild(cbl);
        var norm = function (s) { return f.exakt ? s.trim() : s.replace(/\s+/g, ''); };
        var check = function () {
          var gut = inputs.every(function (inp, i) { return liste(oksL[i]).map(norm).indexOf(norm(inp.value)) >= 0; });
          var extra = !gut && f.falschBei && inputs.length === 1 && f.falschBei[norm(inputs[0].value)];
          melde(gut, gut ? f.richtig : (extra || f.falsch));
        };
        inputs.forEach(function (inp) { inp.onkeydown = function (e) { if (e.key === 'Enter') check(); }; });
        var r = el('div', 'antworten'); r.appendChild(knopf('knopf haupt', 'Prüfen', check)); box.appendChild(r);
        api.inputs = inputs; api.oks = oksL;
      } else if (f.art === 'reihenfolge') {
        var n0 = f.zeilen.length;
        var ord = f.start ? f.start.map(function (x) { return x - 1; }) : mischen(n0, f.zeilen.join('').length);
        var ul = el('ol', 'reihe-liste');
        var zeichne = function (markieren) {
          ul.innerHTML = '';
          ord.forEach(function (zi, platz) {
            var li = el('li', 'reihe-zeile');
            if (markieren) li.classList.add(passt(platz) ? 'richtig' : 'falsch');
            li.appendChild(el('span', 'txt', farbe(f.zeilen[zi])));
            var hoch = knopf('', '↑', function () { tausche(platz, platz - 1); });
            var runter = knopf('', '↓', function () { tausche(platz, platz + 1); });
            hoch.setAttribute('aria-label', 'Zeile ' + (platz + 1) + ' nach oben'); runter.setAttribute('aria-label', 'Zeile ' + (platz + 1) + ' nach unten');
            hoch.disabled = platz === 0; runter.disabled = platz === ord.length - 1;
            li.appendChild(hoch); li.appendChild(runter);
            ul.appendChild(li);
          });
        };
        var passt = function (platz) { return rein(f.zeilen[ord[platz]]) === rein(f.zeilen[platz]); };
        var tausche = function (a, b) {
          if (b < 0 || b >= ord.length) return;
          var t = ord[a]; ord[a] = ord[b]; ord[b] = t;
          zeichne(false); rueck.className = 'rueck'; rueck.innerHTML = '';
          var ziel = ul.children[b].querySelectorAll('button')[b < a ? 0 : 1];
          if (ziel && !ziel.disabled) ziel.focus(); else ul.children[b].querySelector('button:not([disabled])').focus();
        };
        zeichne(false);
        box.appendChild(ul);
        var pruefeR = function () {
          var gut = ord.every(function (_, p) { return passt(p); });
          zeichne(true);
          melde(gut, gut ? f.richtig : f.falsch);
        };
        var rr = el('div', 'antworten'); rr.appendChild(knopf('knopf haupt', 'Prüfen', pruefeR)); box.appendChild(rr);
        api.setzeOrdnung = function (o) { ord = o.slice(); zeichne(false); };
        api.ordnung = function () { return ord.slice(); };
        api.pruefe = pruefeR;
      } else if (f.art === 'zuordnen') {
        var optionen = f.optionen || f.paare.map(function (p) { return p[1]; }).slice().sort(function (a, b) { return rein(a).localeCompare(rein(b), 'de'); });
        var tab = el('table', 'zuordnung'), selects = [];
        f.paare.forEach(function (p, i) {
          var tr = el('tr');
          tr.appendChild(el('td', null, f.linksCode === false ? p[0] : esc(p[0])));
          var td = el('td'), sel = document.createElement('select');
          sel.setAttribute('aria-label', 'Bedeutung für ' + rein(p[0]));
          sel.appendChild(new Option('bitte wählen', ''));
          optionen.forEach(function (o) { sel.appendChild(new Option(o, o)); });
          td.appendChild(sel); tr.appendChild(td); tab.appendChild(tr); selects.push(sel);
        });
        box.appendChild(el('div', 'tabelle-rahmen')).appendChild(tab);
        var pruefeZ = function () {
          var falsch = [];
          f.paare.forEach(function (p, i) {
            var gut = selects[i].value === p[1];
            tab.rows[i].className = gut ? 'richtig' : 'falsch';
            if (!gut) falsch.push(p[2] ? '<li><code>' + esc(p[0]) + '</code>: ' + p[2] + '</li>' : '');
          });
          var gut = !falsch.length;
          melde(gut, gut ? f.richtig : (f.falsch || 'Noch nicht ganz.') + (falsch.join('') ? '<ul>' + falsch.join('') + '</ul>' : ''));
        };
        var rz = el('div', 'antworten'); rz.appendChild(knopf('knopf haupt', 'Prüfen', pruefeZ)); box.appendChild(rz);
        api.selects = selects; api.pruefe = pruefeZ;
      } else {
        fehlerMelden('Frage ' + (n + 1) + ': unbekannte Art ' + f.art);
      }
      box.appendChild(rueck);
      fragenBox.appendChild(box);
      fragen.push(api);
    });
  }

  /* ---------- Typische Fehler als Daten ----------
     fehler: [{ titel, code: [Zeilen], start, rot: [Zeilen], meldung: 'echter Wortlaut', quelle: 'VS Code' | 'Terminal' | 'Test' | 'Programm',
                text: 'HTML: was das heißt und wie du es behebst' }]   (quelle fehlt oder meldung fehlt: kein roter Fehlertext) */
  var QUELLE = { 'Warnung': 'VS Code warnt (gelb unterstrichen):', 'VS Code': 'VS Code meldet:', 'Terminal': 'Im Terminal steht:', 'Test': 'Der Test meldet:', 'Programm': 'Das Programm bricht ab mit:' };
  function bauFehler(liste_) {
    var ziel = document.getElementById('fehler');
    if (!ziel) { if (liste_.length) fehlerMelden('Fehler: kein Element mit id="fehler"'); return; }
    liste_.forEach(function (f, i) {
      var box = el('div', 'fehler');
      box.appendChild(el('h3', null, f.titel));
      if (f.code) {
        var cb = codeBlock(f.code, { start: f.start });
        liste(f.rot).forEach(function (z) { var ln = cb.querySelectorAll('.ln')[z - (f.start || 1)]; if (ln) ln.classList.add('wirft'); else fehlerMelden('Fehler ' + (i + 1) + ': rote Zeile ' + z + ' gibt es nicht'); });
        box.appendChild(cb);
      }
      if (f.meldung) {
        if (!QUELLE[f.quelle || 'VS Code']) fehlerMelden('Fehler ' + (i + 1) + ': unbekannte quelle ' + f.quelle);
        box.appendChild(el('p', null, (QUELLE[f.quelle || 'VS Code'] || '') + ' <span class="meldung' + (f.quelle === 'Warnung' ? ' warnung' : '') + '">' + esc(f.meldung) + '</span>'));
      }
      if (f.text) box.appendChild(el('div', 'fehler-text', f.text));
      ziel.appendChild(box);
    });
  }

  /* ---------- Aufgaben im Projekt ---------- */

  var aufgabenListe = [], kapitelNr = null;
  function bauAufgaben(liste_) {
    var aufgabenBox = document.getElementById('aufgaben');
    if (!aufgabenBox) { if (liste_.length) fehlerMelden('Aufgaben: kein Element mit id="aufgaben"'); return; }
    var fortschritt = document.getElementById('fortschritt');
    if (!fortschritt) { fortschritt = el('p', 'fortschritt'); fortschritt.id = 'fortschritt'; aufgabenBox.parentNode.insertBefore(fortschritt, aufgabenBox.nextSibling); }
    var naechstes = nachbarn(kapitelNr).weiter;
    function zaehle() {
      var n = aufgabenBox.querySelectorAll('input[type=checkbox]:checked').length;
      fortschritt.textContent = n === liste_.length
        ? (liste_.length === 1 ? 'Aufgabe geschafft.' : 'Alle ' + zahlwort(liste_.length) + ' Aufgaben geschafft.') + (naechstes ? ' Weiter mit Kapitel ' + naechstes.nr + '.' : '')
        : n + ' von ' + liste_.length + ' Aufgaben geschafft.';
    }
    liste_.forEach(function (a, i) {
      var id = a.id || ('k' + zwei(kapitelNr) + '-' + (i + 1));
      var box = el('div', 'aufgabe');
      box.appendChild(el('h3', null, a.titel));
      if (a.datei) box.appendChild(el('div', 'datei', esc(a.datei)));
      box.appendChild(el('div', null, a.text));
      if (a.befehl) box.appendChild(el('p', null, 'Test im Terminal: <code>' + esc(a.befehl) + '</code>'));
      liste(a.tipps).forEach(function (t, j) {
        var d = el('details'); d.appendChild(el('summary', null, 'Tipp ' + (j + 1))); d.appendChild(el('p', null, t)); box.appendChild(d);
      });
      var d = el('details'); d.appendChild(el('summary', null, 'Lösung zeigen (erst nach einem eigenen Versuch)'));
      var teile = typeof a.loesung[0] === 'string' ? [{ code: a.loesung }] : a.loesung;
      teile.forEach(function (t) {
        if (t.datei) d.appendChild(el('div', 'datei-name', esc(t.datei)));
        d.appendChild(codeBlock(t.code, { start: t.start }));
      });
      if (a.erklaerung) d.appendChild(el('div', null, a.erklaerung));
      box.appendChild(d);
      var lab = el('label', 'geschafft'), cb = document.createElement('input'); cb.type = 'checkbox';
      cb.checked = holen('crashkurs-' + id) === '1';
      cb.onchange = function () { merken('crashkurs-' + id, cb.checked ? '1' : '0'); zaehle(); };
      lab.appendChild(cb); lab.appendChild(document.createTextNode('Mein Test ist grün'));
      box.appendChild(lab);
      aufgabenBox.appendChild(box);
      aufgabenListe.push(id);
    });
    zaehle();
  }

  /* ---------- Navigation ---------- */

  function nachbarn(nr) {
    var sicht = KAPITEL.filter(function (k) { return sichtbar(k, nr); }), r = { zurueck: null, weiter: null };
    sicht.forEach(function (k, i) { if (k.nr === nr) { r.zurueck = sicht[i - 1] || null; r.weiter = sicht[i + 1] || null; } });
    return r;
  }
  function geschafft(k) {
    var n = 0;
    for (var i = 1; i <= k.aufgaben; i++) if (holen('crashkurs-' + k.paket + '-' + i) === '1') n++;
    return n;
  }
  function kapitelListe(hier, mitStand) {
    var wrap = el('div', 'teile');
    TEILE.forEach(function (t) {
      var ks = KAPITEL.filter(function (k) { return k.teil === t.id && sichtbar(k, hier); });
      if (!ks.length) return;
      wrap.appendChild(el('h3', null, 'Teil ' + t.id + ': ' + esc(t.titel)));
      var ol = el('ol', 'kapitel');
      ks.forEach(function (k) {
        var li = el('li', k.nr === hier ? 'hier' : '');
        if (k.nr === hier) li.setAttribute('aria-current', 'page');
        var html = '<span class="nr">' + k.nr + '</span><span><a href="' + k.datei + '">' + esc(k.titel) + '</a>';
        if (mitStand) {
          var g = geschafft(k);
          html += '<small>' + (g === k.aufgaben ? 'alle Aufgaben geschafft' : g + ' von ' + k.aufgaben + ' Aufgaben geschafft') + '</small>';
          if (g === k.aufgaben) li.classList.add('fertig');
        }
        li.innerHTML = html + '</span>';
        ol.appendChild(li);
      });
      wrap.appendChild(ol);
    });
    return wrap;
  }
  function bauNavigation(nr) {
    var main = document.querySelector('main.kurs');
    var k = KAPITEL.filter(function (x) { return x.nr === nr; })[0];
    if (!k) return;
    var teil = TEILE.filter(function (t) { return t.id === k.teil; })[0];
    var kopf = el('nav', 'kopf'); kopf.setAttribute('aria-label', 'Kursnavigation');
    kopf.innerHTML = '<a href="index.html">Java-Crashkurs</a><span class="wo">Kapitel ' + nr + ' von ' + KAPITEL.length + '</span><span>Teil ' + k.teil + ': ' + esc(teil.titel) + '</span>';
    var alle = el('details', 'alle');
    alle.appendChild(el('summary', null, 'Alle Kapitel'));
    alle.appendChild(kapitelListe(nr, false));
    main.insertBefore(alle, main.firstChild);
    main.insertBefore(kopf, alle);
    if (!main.querySelector('h1')) main.insertBefore(el('h1', null, esc(k.titel)), alle.nextSibling);
    var nb = nachbarn(nr), unten = el('nav', 'blaettern'); unten.setAttribute('aria-label', 'Vor und zurück');
    if (nb.zurueck) unten.appendChild(el('a', 'zurueck', '<small>← Kapitel ' + nb.zurueck.nr + '</small>' + esc(nb.zurueck.titel))).href = nb.zurueck.datei;
    else unten.appendChild(el('a', 'zurueck', '<small>← Start</small>Übersicht')).href = 'index.html';
    if (nb.weiter) unten.appendChild(el('a', 'vor', '<small>Kapitel ' + nb.weiter.nr + ' →</small>' + esc(nb.weiter.titel))).href = nb.weiter.datei;
    else unten.appendChild(el('a', 'vor', '<small>Zurück zum Start →</small>Übersicht')).href = 'index.html';
    main.appendChild(unten);
    if (!/–|Java-Crashkurs/.test(document.title)) document.title = 'Kapitel ' + nr + ': ' + k.titel + ' – Java-Crashkurs';
  }

  /* ---------- Fehler in den Kapiteldaten sichtbar machen ---------- */

  var datenfehler = [];
  function fehlerMelden(text) {
    datenfehler.push(text);
    if (window.console) console.error('Crashkurs: ' + text);
  }

  /* ---------- Start ---------- */

  function standalone() {
    var wurzel = document.documentElement;
    if (!getComputedStyle(wurzel).getPropertyValue('--background').trim()) wurzel.classList.add('standalone');
  }
  var selbsttest = null;
  function start(cfg) {
    standalone();
    kapitelNr = cfg.nr;
    legendeKapitel = cfg.legende || {};
    bauNavigation(cfg.nr);
    var ls = cfg.laeufe || {};
    Object.keys(ls).forEach(function (id) { bauLauf(id, ls[id]); });
    document.querySelectorAll('.code[data-code]').forEach(function (d) {
      var start_ = Number(d.getAttribute('data-start')) || 1;
      var neu = codeBlock(d.getAttribute('data-code').split('\n'), { start: start_ });
      d.innerHTML = neu.innerHTML;
      var rot = (d.getAttribute('data-rot') || '').split(',').filter(Boolean).map(Number);
      var gelb = (d.getAttribute('data-gelb') || '').split(',').filter(Boolean).map(Number);
      d.querySelectorAll('.ln').forEach(function (ln, i) {
        if (rot.indexOf(start_ + i) >= 0) ln.classList.add('wirft');
        if (gelb.indexOf(start_ + i) >= 0) ln.classList.add('cur');
      });
      /* data-legende: 'nein', 'ja' (auch in Fehlerkästen) oder eigene Texte wie 'pink=Name der Methode; tuerkis=Parameter' */
      var will = d.getAttribute('data-legende'), eigene = null;
      if (will && will.indexOf('=') > 0) {
        eigene = {};
        will.split(';').forEach(function (t) { var p = t.split('='); if (p[1]) eigene[p[0].trim()] = p[1].trim(); });
        will = 'ja';
      }
      if (will !== 'nein' && (will === 'ja' || !d.closest('.fehler'))) {
        var lg = legende(d, eigene);
        if (lg) d.parentNode.insertBefore(lg, d.nextSibling);
      }
    });
    bauFehler(cfg.fehler || []);
    bauFragen(cfg.fragen || []);
    bauAufgaben(cfg.aufgaben || []);
    selbsttest = cfg.selbsttest || null;
  }
  function startseite() {
    standalone();
    var ziel = document.getElementById('uebersicht');
    if (ziel) { ziel.classList.add('uebersicht'); ziel.appendChild(kapitelListe(null, true)); }
    var erstes = KAPITEL.filter(function (k) { return sichtbar(k); })[0];
    document.querySelectorAll('a[data-erstes-kapitel]').forEach(function (a) { if (erstes) a.href = erstes.datei; });
  }

  /* ---------- Klicktest: klickt alles durch und liefert ein Protokoll ---------- */

  function wertText(e) {
    var f = e.querySelector('.faecher');
    if (f) return '[' + [].map.call(f.children, function (c) { var k = c.cloneNode(true), sm = k.querySelector('small'); if (sm) sm.remove(); return k.textContent.replace(/\s+/g, ' ').trim(); }).join(',') + ']';
    return e.textContent.replace(/\s+/g, ' ').trim();
  }
  function pruefen() {
    var o = [], log = function (x) { o.push(x); }, schonGemeldet = datenfehler.length;
    datenfehler.forEach(function (t) { log('DATENFEHLER ' + t); });
    var nav = document.querySelector('.kopf');
    if (nav) log('NAV ' + nav.textContent + ' | ' + [].map.call(document.querySelectorAll('.blaettern a'), function (a) { return a.getAttribute('href'); }).join(' '));
    laeufe.forEach(function (l) {
      l.proben.forEach(function (v) {
        var b = l.box;
        l.setze(v);
        var dateien = l.dateienFuer(v), mehr = dateien.length > 1;
        var w = b.querySelector('.knopf.haupt'), n = 1, pfad = [], schritte = l.schritteFuer(v), i = 0, s;
        function stelle() {
          var cur = b.querySelector('.ln.cur');
          if (!cur) { log('  FEHLER Schritt ' + n + ': Zeile ' + s.z + ' gibt es nicht'); return '?'; }
          var name = '';
          if (mehr) { var kopf = cur.closest('.datei').querySelector('.datei-name'); name = (kopf ? kopf.firstChild.textContent : '') + ':'; }
          return name + cur.querySelector('.nr').textContent + (cur.classList.contains('wirft') ? '!' : '');
        }
        for (;;) {
          s = schritte[i];
          pfad.push(stelle());
          if (w.disabled) break;
          w.click(); n++; i++;
        }
        var vars = [].map.call(b.querySelectorAll('.var'), function (e) {
          var ber = e.closest('.bereich'), pre = ber && b.querySelectorAll('.bereich').length > 1 ? ber.querySelector('.titel').textContent + '.' : '';
          return pre + e.querySelector('.name').textContent + '=' + wertText(e.querySelector('.wert'));
        }).join(';');
        var zeile = l.id + ' v=' + v + ' steps=' + n + ' path=' + pfad.join('>') + ' out=' + b.querySelector('.konsole').textContent.replace(/\n/g, '/') +
          ' skip=' + [].map.call(b.querySelectorAll('.ln.skip .nr'), function (e) { return e.textContent; }).join(',') + ' vars=' + vars;
        if (s.objekte || s.statisch) zeile += ' obj=' + [].map.call(b.querySelectorAll('.karte'), function (k) {
          var r = k.querySelector('.var-reihe');
          return k.querySelector('.kopf-k').textContent + (r ? wertText(r) : '') + '{' + [].map.call(k.querySelectorAll('.attr'), function (a) { return a.querySelector('.an').textContent + '=' + wertText(a.querySelector('.aw')); }).join(';') + '}';
        }).join(' ');
        if (s.gibt != null) zeile += ' gibt=' + textVon(s.gibt);
        log(zeile);
        b.querySelectorAll('.knopf')[1].click(); b.querySelectorAll('.knopf')[2].click();
        if (b.querySelector('.stand').textContent.indexOf('Schritt 1 von') !== 0) log('  RESET KAPUTT');
      });
    });
    if (selbsttest) selbsttest(log);
    fragen.forEach(function (q, i) {
      var r = q.rueck, F = 'F' + (i + 1);
      q.box.querySelectorAll('.antwort').forEach(function (a) { a.click(); log(F + ' [' + a.textContent + '] ' + a.className.replace(' text', '') + ' | ' + r.className); });
      q.box.querySelectorAll('.code.waehlbar .ln').forEach(function (l, j) { l.click(); log(F + ' Zeile ' + l.querySelector('.nr').textContent + ' ' + l.className + ' | ' + r.className); });
      if (q.art === 'luecke') {
        var proben = q.f.proben || ['?!'].concat(liste(q.oks[0])[0]);
        proben.forEach(function (v) {
          q.inputs.forEach(function (inp, k) { inp.value = k === 0 ? v : liste(q.oks[k])[0]; });
          q.box.querySelector('.knopf.haupt').click(); log(F + ' Lücke "' + v + '" | ' + r.className);
        });
      }
      if (q.art === 'reihenfolge') {
        q.pruefe(); log(F + ' Reihenfolge ' + q.ordnung().map(function (x) { return x + 1; }).join(',') + ' | ' + r.className);
        var zielO = q.f.zeilen.map(function (_, k) { return k; });
        while (q.ordnung().join() !== zielO.join()) {
          var ord = q.ordnung(), p = 0; while (ord[p] === p) p++;
          var von = ord.indexOf(p);
          q.box.querySelectorAll('.reihe-zeile')[von].querySelectorAll('button')[0].click();
        }
        q.pruefe(); log(F + ' Reihenfolge ' + q.ordnung().map(function (x) { return x + 1; }).join(',') + ' | ' + r.className);
      }
      if (q.art === 'zuordnen') {
        q.selects.forEach(function (s) { s.selectedIndex = 1; });
        q.pruefe(); log(F + ' Zuordnung alle erste | ' + r.className + ' ' + [].map.call(q.box.querySelectorAll('tr'), function (t) { return t.className; }).join(','));
        q.selects.forEach(function (s, k) { s.value = q.f.paare[k][1]; });
        q.pruefe(); log(F + ' Zuordnung richtig | ' + r.className);
      }
    });
    document.querySelectorAll('.aufgabe input[type=checkbox]').forEach(function (c) { c.click(); log('P: ' + document.getElementById('fortschritt').textContent); });
    document.querySelectorAll('.aufgabe input[type=checkbox]').forEach(function (c) { c.click(); });
    log('details=' + document.querySelectorAll('.aufgabe details').length + ' fehlercode=' + [].map.call(document.querySelectorAll('.fehler .code'), function (e) { return e.textContent; }).join(' | '));
    var ue = document.getElementById('uebersicht');
    if (ue) log('UEBERSICHT ' + [].map.call(ue.querySelectorAll('li'), function (li) { return li.textContent; }).join(' | '));
    datenfehler.slice(schonGemeldet).filter(function (t, i, a) { return a.indexOf(t) === i; }).forEach(function (t) { log('DATENFEHLER ' + t); });
    return o.join('\n');
  }

  /* Für pruefen/lauf-java.py: Code und letzte Ausgabe jedes Laufs mit jeder Probe. */
  function laeufeAlsDaten() {
    var r = [];
    laeufe.forEach(function (l) {
      l.proben.forEach(function (v) {
        var sch = l.schritteFuer(v), letzte = sch[sch.length - 1] || {};
        r.push({ id: l.id, v: v, dateien: l.dateienFuer(v).map(function (d) { return { name: d.name || null, code: d.code.map(rein), start: d.start || 1 }; }),
          aus: liste(letzte.aus), java: l.java ? l.java(v) : null });
      });
    });
    return JSON.stringify(r);
  }

  return {
    start: start, startseite: startseite, pruefen: pruefen, laeufeAlsDaten: laeufeAlsDaten, ablauf: ablauf, NULL: NULL,
    KAPITEL: KAPITEL, TEILE: TEILE,
    el: el, esc: esc, knopf: knopf, urteil: urteil, codeBlock: codeBlock, code: codeBlock, farbe: farbe, legende: legende,
    merken: merken, holen: holen, zahlwort: zahlwort
  };
})();
