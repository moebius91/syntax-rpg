package level

import charakter.gegner.Gegner
import charakter.gegner.Goblin
import charakter.gegner.Ork
import charakter.gegner.Schwaermer
import charakter.helden.Held
import utils.*

class Verlies(heldenliste: MutableList<Held>): Level(heldenliste) {

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

    override fun textAusgabeZwischenDenRunden(zaehler: Int) {
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