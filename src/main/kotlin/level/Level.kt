package level

import charakter.gegner.*
import charakter.helden.Held
import erstelleLauftext
import kampfrunde
import lebenspunkteAusgabe
import sekundenschlaf
import ueberpruefeUndEntferneHeldGestorben

open class Level(val heldenliste: MutableList<Held>) {
    val gegnerliste: MutableList<Gegner> = mutableListOf()

    init {
        zufaelligeGegner()
        begruessung()
    }

    protected open fun begruessung() {
        erstelleLauftext("Willkommen im Testlevel!\n")
    }

    protected open fun zufaelligeGegner() {
        gegnerliste.add(Drache())
        val zufallszahl: Int = (0..2).random()
        repeat(zufallszahl) {
            val zufallsGegner: Gegner = listOf(Schwaermer(), Ork()).random()
            gegnerliste.add(zufallsGegner)
        }

    }

    protected fun gegnerAnzeigen() {
        erstelleLauftext("\nZur Zeit halten sich hier folgende Monster auf:\n")
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
        var endgegner: Drache = Drache()
        var goblins: Boolean = false
        for (gegner in gegnerliste) {
            if (gegner is Drache) endgegner = gegner
        }
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
}