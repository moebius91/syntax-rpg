package StatusEffekte.Buffs

import Charakter.Helden.Held

class SchildGebrauch(held: Held): Buff(held) {
    override var dauerRunde: Int = 1

    override var name: String = "SchildGebrauch"
    fun benutzen() {

    }
}