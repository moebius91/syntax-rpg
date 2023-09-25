package statuseffekte.debuffs

import charakter.Charakter
import charakter.helden.Held

class FluchDesDrachen(held: Held): Debuff(held) {
    // Dauer -1 wenn es eine andere Bedingung für das Aufheben des Debuffs gibt
    override var dauerRunde: Int = -1
    override var name: String = "FluchDesDrachen"
    override var beschreibung: String = "Der Fluch des Drachen: Jede Runde werden 10% der maximalen LP abgezogen, bis das Opfer nur 20% LP übrig hat.."

    override fun schaden(charakter: Charakter) {
        if (!abgeklungen && charakter.lebenspunkte() > charakter.maxLebenspunkte()/5) {
            charakter.schadenNehmen(charakter.maxLebenspunkte() / 10)
        } else {
            abgeklungen = true
        }
        if (charakter.lebenspunkte() < (charakter.maxLebenspunkte()/5)) charakter.lebenspunkteSetzen(charakter.maxLebenspunkte()/5)
    }
}