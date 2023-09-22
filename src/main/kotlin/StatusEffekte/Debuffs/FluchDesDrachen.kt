package StatusEffekte.Debuffs

import Charakter.Charakter
import Charakter.Helden.Held

class FluchDesDrachen(held: Held): Debuff(held) {
    override var dauerRunde: Int = -1
    override var abgeklungen: Boolean = false

    override fun schaden(charakter: Charakter) {
        if (!abgeklungen && charakter.lp > charakter.maxlp/5) {
            charakter.lp -= charakter.maxlp / 10
        } else {
            abgeklungen = true
        }
    }
}