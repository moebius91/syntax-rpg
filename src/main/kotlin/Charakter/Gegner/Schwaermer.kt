package Charakter.Gegner

import Charakter.Helden.Held
import Charakter.Helden.Krieger

class Schwaermer: Drache() {
    // Name
    override var name: String = "Kleiner Drache"

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = (150..200).random()
    override var maxlp: Int = lp

    // Attribute für Heilung, Schaden und Verteidigung
    override var staerke: Int = (10..30).random()
    override var ausdauer: Int = (10..30).random()
    override var intelligenz: Int = (10..30).random()

    override fun feueratem(heldenliste: List<Held>) {
        println("Der Feueratem des kleinen Drachen trifft alle Helden!")
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