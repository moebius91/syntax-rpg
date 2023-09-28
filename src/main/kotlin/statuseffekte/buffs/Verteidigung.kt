package statuseffekte.buffs

import charakter.Charakter

class Verteidigung(charakter: Charakter): Buff(charakter) {
    override var dauerRunde: Int = 1

    override var name: String = "Verteidigung"

    override fun benutzen(charakter: Charakter) {
        charakter.ausdauer += 20
    }

    override fun aufheben(charakter: Charakter) {
        charakter.ausdauer -= 20
    }
}