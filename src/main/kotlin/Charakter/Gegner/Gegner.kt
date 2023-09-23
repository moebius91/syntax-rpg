package Charakter.Gegner

import Charakter.Charakter
import Charakter.Helden.Held
import schadenAnwendenUndBerichten

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
        println("${this.name} sieht aus, als würde es einen Angriff erwarten!")
        // TODO("Buff hinzufügen der die Verteidigung kurzzeitig erhöht.")
    }
}