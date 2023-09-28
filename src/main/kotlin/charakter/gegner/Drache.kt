package charakter.gegner

import charakter.helden.Held
import statuseffekte.buffs.SchildGebrauch
import statuseffekte.debuffs.FluchDesDrachen
import utils.buffsVorAngriffPruefen
import utils.gegnerischerSchadenBerechnen
import utils.schadenAnwendenUndBerichten

open class Drache: Gegner() {
    // Name
    override var name: String = listOf(
        "Großer Smaragddrache",
        "Schwarzer Totenkopfdrache",
        "Gefährlicher Horndrache",
        "Atlantischer Riesendrache").random()

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = (500..650).random()
    override var maxlp: Int = lp

    // Attribute für Heilung, Schaden und Verteidigung
    override var staerke: Int = (50..80).random()
    override var ausdauer: Int = (50..80).random()
    override var intelligenz: Int = (50..80).random()

    override fun angreifen(held: Held) {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        val maxSchaden: Int = staerke
        val schaden: Int = gegnerischerSchadenBerechnen(maxSchaden)
        val schildGebrauch: Boolean = buffsVorAngriffPruefen(held, SchildGebrauch(held).name)

        if (!schildGebrauch) {
            schadenAnwendenUndBerichten(this, schaden, maxSchaden, held)
        } else {
            println("${held.name} widersteht dem Angriff hinter seinem Schild!\n")
        }
    }
    open fun feueratem(heldenliste: List<Held>) {
        val schaden: Int = (50..100).random() / 3
        println("Der Feueratem des Drachen trifft alle Helden!\n")


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
        val held: Held = heldenliste.random()
        println("Der Fluch des Drachen trifft: ${held.name}.\n")
        held.debuffs.add(FluchDesDrachen(held))
    }

    fun schwaermerBeschwoeren(gegnerliste: MutableList<Gegner>): Schwaermer {
        println("Ein Schwärmer wird beschworen!\n")
        return Schwaermer()
    }

    open fun schwaermerFressen(schwaermer: Schwaermer) {
        println("${this.name} frisst ${schwaermer.name}.")
        println("${this.name} erhöht seine maximalen LP von ${this.maxlp} auf ${(this.maxlp*1.5).toInt()}\n")
        this.maxlp = (this.maxlp*1.5).toInt()
        lp = maxlp
    }

    fun heilen(){
        var alteLp: Int = this.lp
        var geheilt: Int = (this.maxlp / 20) * (intelligenz / 10)  // 25-40% der maxLP werden geheilt
        this.lp += geheilt
        if (this.lp > this.maxlp) {
            this.lp = this.maxlp
            geheilt = this.maxlp - alteLp
        }
        println("${this.name} heilt sich selbst um $geheilt Lebenspunkte.")
    }
}