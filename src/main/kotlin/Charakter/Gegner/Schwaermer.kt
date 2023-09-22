package Charakter.Gegner

import Charakter.Helden.Held
import Charakter.Helden.Krieger

class Schwaermer: Drache() {
    override var name: String = "Kleiner Drache"
    override var lp: Int = (150..200).random()
    override var maxlp: Int = lp

    override fun feueratem(heldenliste: List<Held>) {
        val schaden: Int = (25..50).random()  / 3
        for (held in heldenliste) {
            held.schadenNehmen(schaden)
        }
    }

    override fun schwaermerFressen(schwaermer: Schwaermer) {
        println("${this.name} versucht sich selbst zu fressen")
    }

    fun schildEntreissen(krieger: Krieger) {
        if (krieger.schildVorhanden) {
            println("${this.name} entreißt ${krieger.name} sein Schild")
            krieger.schildVerlieren()
        }
    }
}