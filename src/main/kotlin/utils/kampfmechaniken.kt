package utils

import charakter.Charakter
import charakter.gegner.Drache
import charakter.gegner.Gegner
import charakter.gegner.Ork
import charakter.gegner.Schwaermer
import charakter.helden.Held
import charakter.helden.Krieger
import statuseffekte.debuffs.SchildVerlieren

/**
 * Führt eine vollständige Runde im Kampf aus, einschließlich der Züge für Helden und Gegner.
 * Diese Funktion stellt die Koordination zwischen den verschiedenen Elementen einer Kampfrunde sicher.
 *
 * @param heldenliste Die Liste der Held-Objekte, die am Kampf teilnehmen.
 * @param gegnerliste Die Liste der Gegner-Objekte, die am Kampf teilnehmen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun kampfrunde(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>){
    heldenzug(heldenliste,gegnerliste)
    toteGegnerEntfernen(gegnerliste)
    attackeGegner(gegnerliste, heldenliste)
    buffsDebuffsEntfernen(heldenliste)
}

fun gegnerischerSchadenBerechnen(maxSchaden: Int): Int {
    val range: IntRange = ((maxSchaden/2)..maxSchaden)
    var listeSchaden: MutableList<Int> = mutableListOf(0)
    for (zahl in range) {
        listeSchaden.add(zahl)
    }
    return listeSchaden.random()
}

/**
 * Mit Hilfe von ChatGPT erstellte Funktion, um Aktionen mit einer
 * bestimmten Wahrscheinlichkeit auszuführen.
 *
 * @param wahrscheinlichkeiten Erwartet eine Liste mit Wahrscheinlichkeiten in Prozent
 * @param aktionen Erwartet eine Liste mit Lambda-Funktionen.
 */

fun waehleUndStarteAktionNachWahrscheinlichkeit(wahrscheinlichkeiten: List<Int>, aktionen: List<() -> Unit>) {
    if (wahrscheinlichkeiten.size != aktionen.size) throw IllegalArgumentException("Du hast eine ungleiche Anzahl von Wahrscheinlichkeiten zu Aktionen übergeben!")
    if (wahrscheinlichkeiten.sum() != 100) throw IllegalArgumentException("Du hast Wahrscheinlichkeiten mit weniger oder mehr als 100% in Summe übergeben!")
    val zufallszahl: Int = (1..100).random()
    var sum: Int = 0

    for (i in wahrscheinlichkeiten.indices) {
        sum += wahrscheinlichkeiten[i]
        if (zufallszahl < sum) {
            aktionen[i].invoke()
            break
        }
    }
}

/**
 * Überprüft die Lebenspunkte aller Helden in der Liste und entfernt die gestorbenen Helden.
 * Die Funktion setzt die Lebenspunkte auf 0 für Helden, die gestorben sind, und gibt eine Nachricht aus.
 *
 * @param heldenliste Die Liste der Held-Objekte, die überprüft werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun ueberpruefeUndEntferneHeldGestorben(heldenliste: MutableList<Held>) {
    val gestorbeneHelden: MutableList<Held> = mutableListOf()
    for (held in heldenliste) {
        if (held.lebenspunkte() <= 0) {
            held.lebenspunkteSetzen(0)
            println("${held.name} ist gestorben.")
            gestorbeneHelden.add(held)
        }
    }
    for (held in gestorbeneHelden) {
        heldenliste.remove(held)
    }
}

/**
 * Bereinigt die Gegnerliste von 'Schwaermer'-Gegnern mit negativen Lebenspunkten, um eine korrekte Spiellogik zu gewährleisten.
 * Diese Funktion stellt sicher, dass keine Gegner mit negativen Lebenspunkten in der Liste verbleiben, indem sie die Lebenspunkte auf 0 setzt und sie aus der Liste entfernt.
 *
 * @param gegnerliste Die Liste von Gegner-Objekten, in der tote 'Schwaermer' korrigiert und entfernt werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun toteGegnerEntfernen(gegnerliste: MutableList<Gegner>) {
    val speicher: MutableList<Gegner> = mutableListOf()
    for (gegner in gegnerliste) {
        if (gegner.lebenspunkte() <= 0) {
            gegner.lebenspunkteSetzen(0)
            speicher.add(gegner)
        }
    }
    for (gegner in speicher) {
        println("${gegner.name} wurde besiegt.")
        gegnerliste.remove(gegner)
    }
}

/**
 * Führt die Angriffe der Gegner in einer Kampfrunde aus.
 * Diese Funktion behandelt die Aktionen der Gegner, die von ihren Typen abhängig sind.
 * Es enthält die Logik für den Drachen-Endgegner und eventuell beschworene Schwärmer.
 * Die Drachenattacken sind zufällig, während die Schwärmer einfache Angriffe oder Feueratem ausführen können.
 *
 * @param gegnerliste Die Liste der Gegner-Objekte, die am Kampf teilnehmen.
 * @param heldenliste Die Liste der Held-Objekte, die am Kampf teilnehmen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun attackeGegner(gegnerliste: MutableList<Gegner>, heldenliste: List<Held>) {
    // Zwischenspeicher für den möglichen Unterboss um
    // ihn hinzuzufügen oder zu entfernen.
    var speicher: Gegner? = null

    // Zwischenspeicher falls ein vorhandener Unterboss gefressen wurde
    var schwaermerGefressen: Boolean = false
    // Iteriert durch die Liste der Gegner.
    for (gegner in gegnerliste) {
        // Sorgt dafür, daß der Drache seine Aktionen ausführen kann
        if (gegner is Drache && gegner !is Schwaermer && gegner.lebenspunkte() > 0) {
            // Wählt die Attacke des Endgegners
            val wahrscheinlichkeiten: List<Int> = listOf(15,10,10,50,10,5)
            val aktionen: List<() -> Unit> = listOf(
                {
                    // Aktion 1 mit 15%
                    gegner.feueratem(heldenliste)
                },
                {
                    // Aktion 2 mit 10%
                    gegner.fluchDesDrachen(heldenliste)
                },
                {
                    // Aktion 3 mit 10%
                    var schwaermerVorhanden: Boolean = false
                    var schwaermer: Schwaermer = Schwaermer()
                    for (gegner in gegnerliste) {
                        if (gegner is Schwaermer) {
                            schwaermerVorhanden = true
                            schwaermer = gegner
                        }
                    }

                    if (!schwaermerVorhanden) {
                        speicher = gegner.schwaermerBeschwoeren(gegnerliste)
                    } else {
                        gegner.schwaermerFressen(schwaermer)
                        schwaermerGefressen = true
                    }
                },
                {
                    // Aktionen 4 mit 50%
                    val held: Held = heldenliste.random()
                    println("${gegner.name} greift ${held.name} an.")
                    gegner.angreifen(held)
                },
                {
                    // Aktion 5 mit 10%
                    gegner.verteidigen()
                },
                {
                    // Aktion 6 mit 5%
                    gegner.heilen()
                }
            )
            waehleUndStarteAktionNachWahrscheinlichkeit(wahrscheinlichkeiten,aktionen)
        }

        if (gegner is Schwaermer && !schwaermerGefressen) {
            // Wählt aus einer Liste eine zufällige Aktion und führt sie aus.
            val wahrscheinlichkeiten: List<Int> = listOf(10,70,15,5)
            val aktionen = listOf(
                {
                    println("${gegner.name} versucht anzugreifen, es geht daneben.\n")
                },
                {
                    val held: Held = heldenliste.random()
                    println("${gegner.name} greift ${held.name} an.")
                    gegner.angreifen(held)
                },
                {
                    gegner.feueratem(heldenliste)
                },
                {
                    val kriegerliste: MutableList<Krieger> = mutableListOf()
                    for (held in heldenliste) {
                        if (held is Krieger) {
                            kriegerliste.add(held)
                        }
                    }
                    if (kriegerliste.isNotEmpty()) {
                        val krieger: Krieger = kriegerliste.random()
                        if (krieger.schildVorhanden) {
                            krieger.debuffs.add(SchildVerlieren(krieger))
                            gegner.schildEntreissen(krieger)
                        } else {
                            println("${gegner.name} hat versucht ${krieger.name} sein Schild zu entreißen!")
                            println("${krieger.name} hat aber kein Schild..\n")
                        }
                    } else {
                        println("${gegner.name} sucht nach einem Krieger um ihn sein Schild zu entreißen.")
                        println("Es gibt keine Krieger in der Heldengruppe.\n")
                    }
                })
            waehleUndStarteAktionNachWahrscheinlichkeit(wahrscheinlichkeiten, aktionen)
        }

        if (gegner is Ork) {
            val wahrscheinlichkeiten: List<Int> = listOf(90,10)
            val aktionen: List<() -> Unit> = listOf(
                {
                    val held: Held = heldenliste.random()
                    println("${gegner.name} greift ${held.name} an.")
                    gegner.angreifen(held)
                },
                {
                    gegner.verteidigen()
                })

            waehleUndStarteAktionNachWahrscheinlichkeit(wahrscheinlichkeiten, aktionen)
        }

        // Speichert einen Verweis auf die zu löschende Objektinstanz
        if (gegner is Schwaermer && schwaermerGefressen) {
            speicher = gegner
        }
    }

    // Löscht den Schwärmer, wenn er gefressen wurde
    // Nach Iteration über die Gegnerliste
    if (schwaermerGefressen) {
        gegnerliste.remove(speicher)
        speicher = null
    }

    // Fügt einen Schwärmer der Gegnerliste hinzu
    if (speicher != null) {
        gegnerliste.add(speicher!!)
    }
}

/**
 * Ermöglicht dem Spieler, einen Gegner aus der Liste der verfügbaren Gegner auszuwählen.
 * Falls nur ein Gegner vorhanden ist, wird dieser automatisch ausgewählt.
 *
 * @param held Das Held-Objekt, das einen Gegner auswählen wird.
 * @param gegnerliste Die Liste der verfügbaren Gegner.
 * @return Gibt das Gegner-Objekt zurück, das ausgewählt wurde.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun gegnerAuswahl(held: Held, gegnerliste: MutableList<Gegner>): Gegner {
    return if (gegnerliste.size == 1) {
        gegnerliste[0]
    } else {
        println("${held.name}, welchen Gegner willst Du in der nächsten Runde angreifen?")
        var zaehler: Int = 0
        for (gegner in gegnerliste) {
            zaehler++
            println("$zaehler. ${gegner.name}")
        }
        val eingabe: Int = sichereEingabe(zaehler) -1
        gegnerliste[eingabe]
    }
}

/**
 * Führt einen Spielzug für jedes Held-Objekt in der gegebenen Liste aus.
 * Wendet zuerst alle aktiven Buffs und Debuffs auf den Helden an, dann führt der Held einen Zug gegen einen zufällig ausgewählten Gegner aus.
 *
 * @param heldenliste Die Liste der Held-Objekte, für die ein Zug ausgeführt werden soll.
 * @param gegnerliste Die Liste der verfügbaren Gegner-Objekte.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun heldenzug(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>) {
    for (held in heldenliste) {
        // Überprüft ob Gegner tot sind und entfernt sie.
        toteGegnerEntfernen(gegnerliste)
        // Überprüft ob der Endgegner gefallen ist und beendet den Heldenzug
        if (gegnerliste.isEmpty()) break
        buffsDebuffsAnwenden(held)

        val gegner: Gegner = gegnerAuswahl(held, gegnerliste)
        var bedingung: Boolean = false
        while(!bedingung && held.lebenspunkte() > 0 && gegnerliste.isNotEmpty()) {
            bedingung = charakterMenue(held,gegner,heldenliste)
        }
    }
}

/**
 * Gibt eine textuelle Beschreibung der durchgeführten Aktion und des verursachten Schadens aus.
 * Führt außerdem die Schadensberechnung am gegnerischen Charakter durch.
 *
 * @param angreifer Das Charakter-Objekt, das den Angriff ausführt.
 * @param schaden Die Höhe des verursachten Schadens.
 * @param maxSchaden Die maximale Höhe des Schadens, die verursacht werden kann.
 * @param gegner Das Charakter-Objekt, das den Schaden erhält.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun schadenAnwendenUndBerichten(angreifer: Charakter, schaden: Int, maxSchaden:Int, gegner: Charakter) {
    val zahl1: Int = maxSchaden / 3
    val zahl2: Int = maxSchaden / 3 * 2
    when (val echterSchaden: Int = gegner.schadenNehmen(schaden)) {
        0 -> {
            if (schaden == 0) {
                println("${angreifer.name} hat nicht getroffen..\n")
            } else {
                println("${angreifer.name} hat keinen Schaden verursacht..\n")
            }
        }
        in 1.. zahl1 -> {
            println("${angreifer.name} hat geringen Schaden verursacht.. ($echterSchaden)\n")
        }
        in 11 .. zahl2 -> {
            println("${angreifer.name} hat Schaden verursacht. ($echterSchaden)\n")
        }
        else -> {
            println("${angreifer.name} hat großen Schaden verursacht! ($echterSchaden)\n")
        }
    }
}

/**
 * Gibt die aktuellen Lebenspunkte für eine Liste von Helden und Gegnern aus.
 * Durchläuft sowohl die Liste der Gegner als auch der Helden und gibt deren Namen und Lebenspunkte aus.
 *
 * @param gegnerliste Die Liste der Gegner-Objekte, deren Lebenspunkte ausgegeben werden sollen.
 * @param heldenliste Die Liste der Held-Objekte, deren Lebenspunkte ausgegeben werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun lebenspunkteAusgabe(gegnerliste: MutableList<Gegner>, heldenliste: List<Held>) {
    println("Lebenspunkte Gegner:")
    if (gegnerliste.isNotEmpty()) {
        for (gegner in gegnerliste) {
            println("${gegner.name}: ${gegner.lebenspunkte()}/${gegner.maxLebenspunkte()}")
        }
    }
    println("\nLebenspunkte Helden:")
    if (heldenliste.isNotEmpty()) {
        for (held in heldenliste) {
            println("${held.name}: ${held.lebenspunkte()}/${held.maxLebenspunkte()}")
        }
    }
    println()
}