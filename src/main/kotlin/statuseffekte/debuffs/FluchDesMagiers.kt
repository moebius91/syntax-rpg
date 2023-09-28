package statuseffekte.debuffs

import charakter.Charakter
import charakter.gegner.Gegner

class FluchDesMagiers(gegner: Gegner): Debuff(gegner) {
    override var dauerRunde: Int = 3
    override var name: String = "FluchDesMagiers"
    override var beschreibung: String = "Der Fluch des Magiers: 3 Runden werden 5% der maximalen LP abgezogen."

    override fun schaden(charakter: Charakter) {
        if (!abgeklungen && charakter.lebenspunkte() > 0) {
            val schaden: Int = charakter.schadenDirektNehmen(charakter.maxLebenspunkte() / 20)
            println("${charakter.name} spürt ${this.name}. ($schaden)\n")
        } else {
            println("${charakter.name} ist nicht mehr mit ${this.name} belegt.\n")
            abgeklungen = true
        }
    }
}