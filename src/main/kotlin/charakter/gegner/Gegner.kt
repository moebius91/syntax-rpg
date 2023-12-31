package charakter.gegner

import charakter.Charakter
import charakter.helden.Held
import statuseffekte.buffs.Verteidigung
import utils.schadenAnwendenUndBerichten

open class Gegner(override var lp: Int = 100): Charakter() {
    // Name
    override var name: String = "Gegner"

    open fun angreifen(held: Held) {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        var schaden: Int = (0..maxSchaden).random()
        schadenAnwendenUndBerichten(this,schaden, maxSchaden, held)
    }

    open fun verteidigen() {
        println("${this.name} sieht aus, als würde es einen Angriff erwarten!\n")
        this.buffs.add(Verteidigung(this))
    }
}