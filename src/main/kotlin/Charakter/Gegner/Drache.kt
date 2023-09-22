package Charakter.Gegner

import Charakter.Helden.Held
import StatusEffekte.Debuffs.FluchDesDrachen

open class Drache: Gegner() {
    // Name
    override var name: String = listOf("Großer Smaragddrache", "Schwarzer Totenkopfdrache").random()

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = (300..350).random()
    override var maxlp: Int = lp
    open fun feueratem(heldenliste: List<Held>) {
        val schaden: Int = (50..100).random() / 3
        println("Der Feueratem des Drachen trifft alle Helden!")


        for (held in heldenliste) {
            var schildGebrauch: Boolean = false
            if (held.buffs.isNotEmpty()) {
                for (buff in held.buffs) {
                    if (buff.name == "SchildGebrauch") {
                        schildGebrauch = true
                    }
                }
            }

            if (!schildGebrauch) held.schadenNehmen(schaden)
        }
    }

    fun fluchDesDrachen(heldenliste: List<Held>) {
        val zahl: Int = (0..2).random()
        val held: Held = heldenliste[zahl]
        println("Der Fluch des Drachen trifft: ${held.name}.")
        held.debuffs.add(FluchDesDrachen(held))
    }

    fun schwaermerBeschwoeren(gegnerliste: MutableList<Gegner>): Schwaermer {
        println("Ein Schwärmer wird beschworen!")
        return Schwaermer()
    }

    open fun schwaermerFressen(schwaermer: Schwaermer) {
        println("${this.name} frisst ${schwaermer.name}.")
        println("${this.name} erhöht seine maximalen LP von ${this.maxlp} auf ${(this.maxlp*1.5).toInt()}")
        this.maxlp = (this.maxlp*1.5).toInt()
    }

    fun heilen(){
        println("${this.name} heilt sich selbst.")
        this.lp += this.maxlp / 10
        if (this.lp > this.maxlp) this.lp = this.maxlp
    }
}