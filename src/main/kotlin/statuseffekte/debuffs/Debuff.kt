package statuseffekte.debuffs

import charakter.Charakter

open class Debuff(charakter: Charakter) {
    open var dauerRunde: Int = -1
    open var abgeklungen: Boolean = false

    open var name: String = "Debuff"
    open var beschreibung: String = "Negative Beeinflussung Deines Charakters."

    open fun effekt(charakter: Charakter) {
        schaden(charakter)
        werteVerringerung(charakter)
    }

    open fun schaden(charakter: Charakter) {
    }

    open fun werteVerringerung(charakter: Charakter) {
    }

    open fun aufheben(charakter: Charakter) {
    }

}
