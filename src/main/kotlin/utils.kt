
import charakter.Charakter
import charakter.gegner.*
import charakter.helden.*
import items.Heiltrank
import items.Vitamine
import statuseffekte.buffs.Buff
import statuseffekte.debuffs.Debuff

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
        val eingabe = readln().replace(".", "").toInt()
        if(eingabe < 0 || eingabe > eintraege) throw IllegalArgumentException("Du hast eine ungültige Zahl eingegeben! '$eingabe'")
        eingabe
    } catch (ex: Exception) {
        println(ex.message)
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

fun endgegnerInstanziieren(): MutableList<Gegner> {
    return mutableListOf(Drache())
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
 * Wendet alle aktiven Buffs auf einen bestimmten Helden an.
 * Die Funktion durchläuft die Liste der Buffs des Helden und führt deren Effekte aus.
 *
 * @param held Das Held-Objekt, auf das die Buffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsAnwenden(charakter: Charakter) {
    if (charakter.buffs.isNotEmpty()) {
        for (buff in charakter.buffs) {
            buff.effekt(charakter)
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

fun debuffsAnwenden(charakter: Charakter) {
    if (charakter.debuffs.isNotEmpty()) {
        for (debuff in charakter.debuffs) {
            debuff.effekt(charakter)
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

fun buffsDebuffsAnwenden(charakter: Charakter) {
    buffsAnwenden(charakter)
    debuffsAnwenden(charakter)
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

fun buffsEntfernen(charakter: Charakter) {
    if (charakter.buffs.isNotEmpty()) {
        val buffs: MutableList<Buff> = mutableListOf()
        for (buff in charakter.buffs) {
            buff.dauerRunde --
            if (buff.dauerRunde == 0) {
                buffs.add(buff)
                buff.aufheben(charakter)
            }
        }
        for (buff in buffs) {
            charakter.buffs.remove(buff)
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

fun debuffsEntfernen(charakter: Charakter) {
    if (charakter.debuffs.isNotEmpty()) {
        val debuffs: MutableList<Debuff> = mutableListOf()
        for (debuff in charakter.debuffs) {
            debuff.dauerRunde --
            if (debuff.dauerRunde == 0 || debuff.abgeklungen) {
                debuffs.add(debuff)
                debuff.aufheben(charakter)
            }
        }
        for (debuff in debuffs) {
            charakter.debuffs.remove(debuff)
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

fun buffsDebuffsEntfernen(charakterliste: List<Charakter>) {
    for (charakter in charakterliste) {
        buffsEntfernen(charakter)
        debuffsEntfernen(charakter)
    }
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
    toteGegnerEntfernen(gegnerliste)
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
 * Eine kurze Begrüßung und Instruktion über das Spiel
 */
fun spielBegruessung() {
    print("Hallo und herzlich Willkommen bei Golden Syntax!\r")
    Thread.sleep(2000)
    print("Auf Dich wartet eine spannende Reise.\r")
    Thread.sleep(2000)
    println("Stelle Deine Heldengruppe zusammen!")
}

/**
 * Mit Hilfe von ChatGPT erstellte Funktion um Aktionen mit bestimmter
 * Wahrscheinlichkeit auszuführen.
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