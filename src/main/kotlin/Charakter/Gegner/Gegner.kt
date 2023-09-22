package Charakter.Gegner

import Charakter.Charakter
import Charakter.Helden.Held
import ausgabeSchaden

open class Gegner(override var lp: Int = 100): Charakter() {
    override var name: String = "Charakter/Gegner"

    open fun angreifen(held: Held) {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        var schaden: Int = (0..maxSchaden).random()
        ausgabeSchaden(this,schaden, maxSchaden, held)
    }

    open fun verteidigen() {

    }
}