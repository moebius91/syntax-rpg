package level

import charakter.gegner.Gegner
import charakter.gegner.Ork
import charakter.gegner.Schwaermer
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
        var zaehler: Int = 1
        while (gegnerliste.isNotEmpty() && heldenliste.isNotEmpty()) {
            // Rundenbasierte Aktionen abhängig vom Level
            zaehler = rundenbasierteLevelAktionen(zaehler)
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

    private fun rundenAusgabe(zaehler: Int): Int {
        println("Runde $zaehler beginnt:")
        return zaehler +1
    }

    protected open fun rundenbasierteLevelAktionen(zaehler: Int): Int{
        return rundenAusgabe(zaehler)
    }
}