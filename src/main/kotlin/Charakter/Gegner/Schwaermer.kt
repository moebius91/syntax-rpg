package Charakter.Gegner

import Charakter.Helden.Held

class Schwaermer: Drache() {
    override var name: String = "Kleiner Drache"
    override var lp: Int = (150..200).random()

    override fun feueratem(heldenliste: List<Held>) {
        val schaden: Int = (25..50).random()  / 3
        for (held in heldenliste) {
            held.lp -= schaden
        }
    }
}