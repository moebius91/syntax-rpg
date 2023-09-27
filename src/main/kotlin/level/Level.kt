package level

import charakter.gegner.*
import charakter.helden.Held
import utils.*

open class Level(val heldenliste: MutableList<Held>) {
    val gegnerliste: MutableList<Gegner> = endgegnerUndGegnerlisteInstanziieren()

    init {
        zufaelligeGegner()
        begruessung()
    }

    protected open fun begruessung() {
        erstelleLauftext("Willkommen im Testlevel!\n")
    }

    protected open fun zufaelligeGegner() {
        val zufallszahl: Int = (0..2).random()
        repeat(zufallszahl) {
            val zufallsGegner: Gegner = listOf(Schwaermer(), Ork()).random()
            gegnerliste.add(zufallsGegner)
        }

    }

    protected fun gegnerAnzeigen() {
        erstelleLauftext("\nZur Zeit halten sich hier folgende Gegner auf:\n")
        sekundenschlaf()
        var zaehler: Int = 1
        for (gegner in gegnerliste) {
            if (zaehler  == (gegnerliste.size)) {
                erstelleLauftext(gegner.name + ".\n\n")
            } else if (zaehler == gegnerliste.size -1) {
                erstelleLauftext(gegner.name)
                erstelleLauftext(" und ")
            } else {
                erstelleLauftext(gegner.name + ", ")
            }
            zaehler++
        }
    }

    fun spielrunde() {
        var heldenGewonnen: Boolean = false
        var endgegner: Drache = gegnerliste[0] as Drache
        var goblins: Boolean = false

        while (gegnerliste.isNotEmpty() && heldenliste.isNotEmpty()) {
            var zaehler: Int = 1
            textAusgabeZwischenDenRunden(zaehler)
            zaehler++
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

            if (endgegner.lebenspunkte() < ( endgegner.maxLebenspunkte() /2 ) && !goblins)  {
                gegnerliste.add(Goblin())
                gegnerliste.add(Goblin())
                println("Zwei Goblins kommen ${endgegner.name} zur Hilfe!\n")
                goblins = true
            }

            lebenspunkteAusgabe(gegnerliste, heldenliste)
        }

        println(if (heldenGewonnen) "Die Helden haben gewonnen!" else "Spiel verloren.")
    }

    protected open fun textAusgabeZwischenDenRunden(zaehler: Int) {
        when (zaehler) {
            1 -> {
                println("Runde: $zaehler beginnt.")
            }
            2 -> {
                println("Runde: $zaehler beginnt.")
            }
            3 -> {
                println("Runde: $zaehler beginnt.")
            }
            4 -> {
                println("Runde: $zaehler beginnt.")
            }
            else -> {
                println("Runde: $zaehler beginnt.")
            }
        }
    }
}