package level

import charakter.gegner.*
import charakter.helden.Held
import erstelleLauftext

class Verlies(heldenliste: MutableList<Held>): Level(heldenliste) {

    override fun begruessung() {
        erstelleLauftext("Willkommen im dunklen Verlies!\n")
        erstelleLauftext("Hier erwarten Dich fiese Monster und Gefahren...\n")
        super.gegnerAnzeigen()
    }

    override fun zufaelligeGegner() {
        gegnerliste.add(Drache())
        val zufallszahl: Int = (0..2).random()
        repeat(zufallszahl) {
            val zufallsGegner: Gegner = listOf(Schwaermer(), Ork(), Goblin()).random()
            gegnerliste.add(zufallsGegner)
        }
    }
}