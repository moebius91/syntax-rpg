import Charakter.Charakter
import Charakter.Gegner.*
import Charakter.Helden.*
import Items.Heiltrank
import Items.Vitamine
import StatusEffekte.Buffs.Buff
import StatusEffekte.Debuffs.Debuff
import kotlin.Exception

/**
 * Liest eine Ganzzahl vom Benutzer ein und stellt sicher, dass sie innerhalb eines bestimmten Bereichs liegt.
 *
 * @param eintraege Die Obergrenze für die akzeptierte Eingabe (die Untergrenze ist 0).
 * @return Die eingelesene und validierte Ganzzahl.
 * @throws IllegalArgumentException Wenn die Eingabe außerhalb des vorgegebenen Bereichs liegt.
 * @throws Exception Wenn bei der Konvertierung der Eingabe ein Fehler auftritt.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */
fun sichereEingabe(eintraege:Int): Int {
    return try {
        val eingabe: Int = readln().toInt()
        if(eingabe < 0 || eingabe > eintraege) throw IllegalArgumentException("Du hast eine ungültige Zahl eingegeben! '$eingabe'")
        eingabe
    } catch (e: Exception) {
        println(e.message)
        sichereEingabe(eintraege)
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

fun anwendenUndBerichtenSchaden(angreifer: Charakter, schaden: Int, maxSchaden:Int, gegner: Charakter) {
    val zahl1: Int = maxSchaden / 3
    val zahl2: Int = maxSchaden / 3 * 2

    when (schaden) {
        0 -> {
            println("${angreifer.name} hat nicht getroffen..")
        }
        in 1.. zahl1 -> {
            gegner.schadenNehmen(schaden)
            println("${angreifer.name} hat geringen Schaden verursacht.. ($schaden)")
        }
        in 11 .. zahl2 -> {
            gegner.schadenNehmen(schaden)
            println("${angreifer.name} hat Schaden verursacht. ($schaden)")
        }
        else -> {
            gegner.schadenNehmen(schaden)
            println("${angreifer.name} hat großen Schaden verursacht! ($schaden)")
        }
    }
}

/**
 * Legt ein Standardinventar für einen Held an, indem diverse Gegenstände zum Inventar des Helden hinzugefügt werden.
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
    return if (held is Krieger) {
        held.menue(gegner, heldenliste)
    } else if (held is Magier) {
        held.menue(gegner, heldenliste)
    } else if (held is Druide) {
        held.menue(gegner, heldenliste)
    } else {
        false
    }
}

/**
 * Reduziert die Dauer der Buffs eines Helden und entfernt abgelaufene Buffs.
 * Geht durch die Liste der aktiven Buffs und verringert ihre Dauer,
 * sammelt abgelaufene Buffs in einer temporären Liste und entfernt sie.
 *
 * @param held Der Held, dessen Buffs bearbeitet werden.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsEntfernen(held: Held) {
    if (held.buffs.isNotEmpty()) {
        var buffs: MutableList<Buff> = mutableListOf()
        for (buff in held.buffs) {
            buff.dauerRunde --
            if (buff.dauerRunde == 0) {
                buffs.add(buff)
            }
        }
        for (buff in buffs) {
            held.buffs.remove(buff)
        }
    }
}

/**
 * Reduziert die Dauer der Debuffs eines Helden und entfernt abgelaufene oder abgeklungene Debuffs.
 * Geht durch die Liste der aktiven Debuffs, verringert ihre Dauer und
 * sammelt abgelaufene oder abgeklungene Debuffs in einer temporären Liste, um sie dann zu entfernen.
 *
 * @param held Der Held, dessen Debuffs bearbeitet werden.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun debuffsEntfernen(held: Held) {
    if (held.debuffs.isNotEmpty()) {
        var debuffs: MutableList<Debuff> = mutableListOf()
        for (debuff in held.debuffs) {
            debuff.dauerRunde --
            if (debuff.dauerRunde == 0 || debuff.abgeklungen) {
                debuffs.add(debuff)
            }
        }
        for (debuff in debuffs) {
            held.debuffs.remove(debuff)
        }
    }
}

/**
 * Entfernt Buffs und Debuffs für eine Liste von Helden.
 * Ruft die Funktionen `buffsEntfernen` und `debuffsEntfernen` für jeden Helden in der Liste auf.
 *
 * @param heldenliste Die Liste der Held-Objekte, deren Buffs und Debuffs entfernt werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsDebuffsEntfernen(heldenliste: List<Held>) {
    for (held in heldenliste) {
        buffsEntfernen(held)
        debuffsEntfernen(held)
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
    for (gegner in gegnerliste) {
        println("${gegner.name}: ${gegner.lebenspunkte()}")
    }
    for (held in heldenliste) {
        println("${held.name}: ${held.lebenspunkte()}")
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

fun heldenteamInstanziieren(): List<Held> {
    val heldenliste: MutableList<Held> = mutableListOf()
    repeat(3) {
        heldenliste.add(heldenInstanziieren())
    }

    return heldenliste.toList()
}

/**
 * Instanziert einen Endgegner und fügt ihn zu einer Liste hinzu.
 * In diesem Fall wird ein Drache als Endgegner instanziiert.
 *
 * @return Eine Liste, die ein Drache-Objekt als Endgegner enthält.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun endgegnerInstanziieren(): MutableList<Gegner> {
    return mutableListOf(Drache())
}

/**
 * Bereinigt die Gegnerliste von 'Schwaermer'-Gegnern mit negativen Lebenspunkten, um eine korrekte Spiellogik zu gewährleisten.
 * Diese Funktion stellt sicher, dass keine Gegner mit negativen Lebenspunkten in der Liste verbleiben, indem sie die Lebenspunkte auf 0 setzt und sie aus der Liste entfernt.
 *
 * @param gegnerliste Die Liste von Gegner-Objekten, in der tote 'Schwaermer' korrigiert und entfernt werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun toteSchwaermerEntfernen(gegnerliste: MutableList<Gegner>) {
    var speicher: MutableList<Gegner> = mutableListOf()
    for (gegner in gegnerliste) {
        if (gegner.lebenspunkte() < 0 && gegner is Schwaermer) {
            gegner.lebenspunkteSetzen(0)
            speicher.add(gegner)
        }
    }
    for (gegner in speicher) {
        gegnerliste.remove(gegner)
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
    val gegner: Gegner = gegnerliste.random()

    for (held in heldenliste) {
        buffsDebuffsAnwenden(held)

        var bedingung: Boolean = false
        while(!bedingung && held.lebenspunkte() > 0 && gegnerliste[0].lebenspunkte() > 0) {
            bedingung = charakterMenue(held,gegner,heldenliste)
        }
    }
}

/**
 * Wendet alle aktiven Buffs auf einen bestimmten Helden an.
 * Die Funktion durchläuft die Liste der Buffs des Helden und führt deren Effekte aus.
 *
 * @param held Das Held-Objekt, auf das die Buffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsAnwenden(held: Held) {
    if (held.buffs.isNotEmpty()) {
        for (buff in held.buffs) {
            buff.effekt(held)
        }
    }
}

/**
 * Wendet alle aktiven Debuffs auf einen bestimmten Helden an.
 * Die Funktion durchläuft die Liste der Debuffs des Helden und führt deren Effekte aus.
 *
 * @param held Das Held-Objekt, auf das die Debuffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun debuffsAnwenden(held: Held) {
    if (held.debuffs.isNotEmpty()) {
        for (debuff in held.debuffs) {
            debuff.effekt(held)
        }
    }
}

/**
 * Wendet sowohl Buffs als auch Debuffs auf einen bestimmten Helden an.
 * Die Funktion ruft intern `buffsAnwenden` und `debuffsAnwenden` auf.
 *
 * @param held Das Held-Objekt, auf das Buffs und Debuffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsDebuffsAnwenden(held: Held) {
    buffsAnwenden(held)
    debuffsAnwenden(held)
}

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
    toteSchwaermerEntfernen(gegnerliste)
    attackeGegner(gegnerliste, heldenliste)
    buffsDebuffsEntfernen(heldenliste)
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
    // Generiert eine zufällige Zahl, welche die Attacke des
    // Endgegners bestimmt.
    val zufallszahl: Int = (1..6).random()

    // Zwischenspeicher für den möglichen Unterboss um
    // ihn hinzuzufügen oder zu entfernen.
    var speicher: Gegner? = null

    // Zwischenspeicher falls ein vorhandener Unterboss gefressen wurde
    var schwaermerGefressen: Boolean = false
    // Iteriert durch die Liste der Gegner.
    for (gegner in gegnerliste) {
        // Sorgt dafür, daß der Drache seine Aktionen ausführen kann
        if (gegner is Drache && gegner !is Schwaermer) {
            // Wählt die Attacke des Endgegners
            when (zufallszahl) {
                1 -> {
                    gegner.feueratem(heldenliste)
                }

                2 -> {
                    gegner.fluchDesDrachen(heldenliste)
                }

                3 -> {
                    var schwaermerVorhanden: Boolean = false
                    for (gegner in gegnerliste) {
                        if (gegner is Schwaermer) schwaermerVorhanden = true
                    }
                    if (!schwaermerVorhanden) {
                        speicher = gegner.schwaermerBeschwoeren(gegnerliste)
                    } else {
                        if (gegnerliste[1] is Schwaermer) {
                            gegner.schwaermerFressen(gegnerliste[1] as Schwaermer)
                            schwaermerGefressen = true
                        }
                    }
                }
                4 -> {
                    gegner.angreifen(heldenliste.random())
                }
                5 -> {
                    gegner.verteidigen()
                }
                else -> {
                    gegner.heilen()
                }
            }       // Wählt den Unterboss aus
        } else if (gegner is Schwaermer && !schwaermerGefressen) {
            // Wählt aus einer Liste eine zufällige Aktion und führt sie aus.
            listOf(
                {
                    println("Der Schwärmer versucht anzugreifen, es geht daneben.")
                },
                {
                    val held: Held = heldenliste.random()
                    println("Der Schwärmer greift ${held.name} an.")
                    gegner.angreifen(held)
                },
                {
                    gegner.feueratem(heldenliste)
                }
            ).random()
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
    }

    // Fügt einen Schwärmer der Gegnerliste hinzu
    if (speicher != null) {
        gegnerliste.add(speicher)
    }
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

fun spielrunde(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>) {
    val held1: Held = heldenliste[0]
    val held2: Held = heldenliste[1]
    val held3: Held = heldenliste[2]

    val drache: Gegner = gegnerliste[0]

    while (drache.lebenspunkte() > 0 && (held1.lebenspunkte() > 0 || held2.lebenspunkte() > 0 || held3.lebenspunkte() > 0)) {
        kampfrunde(heldenliste, gegnerliste)
        if (drache.lebenspunkte() < 0) {
            drache.lebenspunkteSetzen(0)
            if (gegnerliste.size == 2) gegnerliste[1].lebenspunkteSetzen(0)
            println("Der Drache wurde besiegt!")
            break
        }
        if (held1.lebenspunkte() < 0) {
            held1.lebenspunkteSetzen(0)
            println("${held1.name} ist gestorben")
        } else if (held2.lebenspunkte() < 0) {
            held2.lebenspunkteSetzen(0)
            println("${held2.name} ist gestorben")
        } else if (held3.lebenspunkte() < 0) {
            held3.lebenspunkteSetzen(0)
            println("${held3.name} ist gestorben")
        }

        if (held1.lebenspunkte() < 0) held1.lebenspunkteSetzen(0)
        if (held2.lebenspunkte() < 0) held2.lebenspunkteSetzen(0)
        if (held3.lebenspunkte() < 0) held3.lebenspunkteSetzen(0)
        if (held1.lebenspunkte() == 0 && held2.lebenspunkte() == 0 && held3.lebenspunkte() == 0) {
            println("Der Drache hat alle Helden besiegt.")
            break
        }

        lebenspunkteAusgabe(gegnerliste, heldenliste)
    }
}