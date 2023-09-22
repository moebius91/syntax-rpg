package StatusEffekte.Debuffs

import Charakter.Charakter
import Charakter.Gegner.Gegner

class FluchDesMagiers(gegner: Gegner): Debuff(gegner) {
    override var dauerRunde: Int = 3
    override var name: String = "FluchDesMagiers"
    override var beschreibung: String = "Der Fluch des Magiers: 3 Runden werden 5% der maximalen LP abgezogen."

    override fun schaden(charakter: Charakter) {
        if (!abgeklungen && charakter.lp > charakter.maxlp/5) {
            charakter.lp -= charakter.maxlp / 20
        } else {
            abgeklungen = true
        }
    }
}