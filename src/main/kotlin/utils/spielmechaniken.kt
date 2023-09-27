package utils

import charakter.gegner.Drache
import charakter.gegner.Gegner
import charakter.helden.Druide
import charakter.helden.Held
import charakter.helden.Krieger
import charakter.helden.Magier
import items.Heiltrank
import items.Vitamine

/**
 * Eine kurze Begrüßung und Instruktion über das Spiel
 */

fun spielBegruessung() {
    erstelleLauftext("Hallo und herzlich Willkommen bei Golden Syntax!\r")
    sleep(500)
    erstelleLauftext("Auf Dich wartet eine spannende Reise.\r")
    sleep(500)
    erstelleLauftext("Stelle Deine Heldengruppe zusammen!\n")
}

/**
 * Verwaltet eine vollständige Spielrunde im Drachenkampf-Szenario.
 * Die Funktion führt solange Kampfrunden aus, bis entweder der Drache besiegt ist oder alle Helden gefallen sind.
 * Während des Spiels werden der Zustand der Helden und des Drachen kontinuierlich aktualisiert und geprüft.
 * Am Ende jeder Runde wird der Status der Lebenspunkte aller Kämpfenden ausgegeben.
 *
 * @param heldenliste Die Liste der Held-Objekte, die am Kampf teilnehmen.
 * @param gegnerliste Die Liste der Gegner-Objekte, die am Kampf teilnehmen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun spielrunde(heldenliste: MutableList<Held>, gegnerliste: MutableList<Gegner>) {
    var heldenGewonnen: Boolean = false
    while (gegnerliste.isNotEmpty() && heldenliste.isNotEmpty()) {
        kampfrunde(heldenliste, gegnerliste)
        ueberpruefeUndEntferneHeldGestorben(heldenliste)

        if (heldenliste.isEmpty()) {
            println("Die Helden wurden besiegt.")
            break
        }

        if (gegnerliste.isEmpty()) {
            heldenGewonnen = true
            println("Alle Gegner wurden besiegt!")
            break
        }

        lebenspunkteAusgabe(gegnerliste, heldenliste)
    }

    println(if (heldenGewonnen) "Die Helden haben gewonnen!" else "Spiel verloren.")
}

/**
 * Legt ein Standardinventar für einen Helden an, indem diverse Gegenstände zum Inventar des Helden hinzugefügt werden.
 *
 * @param held Das Held-Objekt, dessen Inventar angelegt wird.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun inventarAnlegen(held: Held) {
    held.inventar.add(Heiltrank())
    held.inventar.add(Heiltrank())
    held.inventar.add(Heiltrank())
    held.inventar.add(Vitamine())
}

/**
 * Legt für eine Liste von Helden ein Standardinventar an.
 * Ruft intern die `inventarAnlegen`-Funktion für jeden Helden in der Liste auf.
 *
 * @param heldenliste Die Liste der Held-Objekte, für die ein Inventar angelegt werden soll.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun heldenteamInventareAnlegen(heldenliste: List<Held>) {
    for (held in heldenliste) inventarAnlegen(held)
}

/**
 * Zeigt das Charaktermenü für den angegebenen Helden und handhabt die Interaktionen mit dem Gegner.
 * Leitet die Steuerung an die spezifischen Menüs der jeweiligen Heldenklassen weiter.
 *
 * @param held Der Held, für den das Menü angezeigt wird.
 * @param gegner Der Gegner, der im Spiel aktuell ist.
 * @param heldenliste Die Liste der Held-Objekte, die im Team sind.
 * @return Ein Boolean-Wert, der den Status des Menüs oder der Aktion zurückgibt.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun charakterMenue(held: Held, gegner: Gegner, heldenliste: List<Held>): Boolean {
    if (held.waffenTyp.name == "Unbewaffnet") {
        held.waehleWaffe()
    }
    return when (held) {
        is Krieger -> {
            held.menue(gegner, heldenliste)
        }

        is Magier -> {
            held.menue(gegner, heldenliste)
        }

        is Druide -> {
            held.menue(gegner, heldenliste)
        }

        else -> {
            false
        }
    }
}

/**
 * Instanziert einen neuen Helden basierend auf der Benutzereingabe.
 * Fragt den Benutzer nach der gewünschten Klasse und dem Namen des Helden, und gibt dann eine neue Instanz der entsprechenden Klasse zurück.
 *
 * @return Ein neu instanziiertes Held-Objekt der gewählten Klasse mit dem gewählten Namen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun heldenInstanziieren(): Held {
    println("Welche Klasse soll Dein Charakter erhalten?")
    println("1. Krieger - 2. Druide - 3. Magier")
    val eingabeKlasse: Int = sichereEingabe(3)
    print("Wie soll Dein Charakter heißen: ")
    val eingabeName: String = readln()

    return when (eingabeKlasse) {
        1 -> {
            Krieger(eingabeName)
        }
        2 -> {
            Druide(eingabeName)
        }
        else -> {
            Magier(eingabeName)
        }
    }
}

/**
 * Instanziert ein Team von Helden durch wiederholtes Aufrufen der `heldenInstanziieren`-Funktion.
 * Fügt die neu erstellten Helden zu einer Liste hinzu und gibt diese als Ergebnis zurück.
 *
 * @return Eine Liste mit drei Held-Objekten.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun heldenteamInstanziieren(): MutableList<Held> {
    val heldenliste: MutableList<Held> = mutableListOf()
    repeat(3) {
        heldenliste.add(heldenInstanziieren())
    }
    println()
    heldenteamInventareAnlegen(heldenliste)
    return heldenliste
}

/**
 * Instanziert einen Endgegner und fügt ihn zu einer Liste hinzu.
 * In diesem Fall wird ein Drache als Endgegner instanziiert.
 *
 * @return Eine Liste, die ein Drache-Objekt als Endgegner enthält.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun endgegnerUndGegnerlisteInstanziieren(): MutableList<Gegner> {
    return mutableListOf(Drache())
}