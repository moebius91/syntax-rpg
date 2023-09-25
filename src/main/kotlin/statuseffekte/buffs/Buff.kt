package statuseffekte.buffs

import charakter.Charakter

open class Buff(charakter: Charakter) {
    open var dauerRunde: Int = -1

    open var name: String = "Buff"
    open var beschreibung: String = "Positive Beeinflussung Deines Charakters."

    open fun effekt(charakter: Charakter) {

    }
    open fun aufheben(charakter: Charakter) {
    }
}