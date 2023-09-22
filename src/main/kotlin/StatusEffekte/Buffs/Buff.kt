package StatusEffekte.Buffs

import Charakter.Charakter

open class Buff(charakter: Charakter) {
    open var dauerRunde: Int = -1

    open var name: String = "Buff"

    open fun effekt(charakter: Charakter) {

    }
    open fun aufheben(charakter: Charakter) {
        charakter.buffs.remove(this)
    }
}