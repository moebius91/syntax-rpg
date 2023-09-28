package level

import charakter.gegner.Gegner
import charakter.gegner.Goblin
import charakter.gegner.Ork
import charakter.gegner.Schwaermer
import charakter.helden.Held
import utils.erstelleLauftext
import utils.sleep

class Verlies(heldenliste: MutableList<Held>): Level(heldenliste) {
    var goblins: Boolean = false

    override fun begruessung() {
        erstelleLauftext("Willkommen im dunklen Verlies!\n")
        erstelleLauftext("Hier erwarten Dich fiese Kreaturen und Gefahren...\n")
        super.gegnerAnzeigen()
    }

    override fun zufaelligeGegner() {
        val zufallszahl: Int = (0..2).random()
        repeat(zufallszahl) {
            val zufallsGegner: Gegner = listOf(Schwaermer(), Ork(), Goblin()).random()
            gegnerliste.add(zufallsGegner)
        }
    }

    override fun rundenbasierteLevelAktionen(zaehler: Int): Int {
        when {
            zaehler % 6 == 0 -> {
                goblins()
            }
            zaehler % 4 == 2 && zaehler % 3 == 1 -> {
                deckeneinsturz()
            }
        }

        return super.rundenbasierteLevelAktionen(zaehler)
    }

    fun goblins() {
        gegnerliste.add(Goblin())
        gegnerliste.add(Goblin())
        println("Zwei Goblins kommen ${gegnerliste[0].name} zur Hilfe!\n")
        goblins = true
    }

    fun deckeneinsturz() {
        erstelleLauftext("Die Decke im Verlies bricht ein, alle Kontrahenten nehmen Schaden!\n")
        for (gegner in gegnerliste) {
            val schaden: Int = gegner.schadenNehmen((10..30).random())
            println("${gegner.name} wird getroffen. ($schaden)")
            sleep(500)
        }
        for (held in heldenliste) {
            val schaden: Int = held.schadenNehmen((10..30).random())
            println("${held.name} wird getroffen. ($schaden)")
            sleep(500)
        }
        println()
    }
}