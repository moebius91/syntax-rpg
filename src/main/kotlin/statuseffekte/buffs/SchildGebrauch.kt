package statuseffekte.buffs

import charakter.helden.Held

class SchildGebrauch(held: Held): Buff(held) {
    override var dauerRunde: Int = 1

    override var name: String = "SchildGebrauch"
    override fun benutzen() {

    }
}