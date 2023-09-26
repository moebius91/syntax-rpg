package statuseffekte.debuffs

import charakter.Charakter
import charakter.helden.Krieger

class SchildVerlieren(charakter: Charakter) : Debuff(charakter) {
    override var dauerRunde: Int = 3

    override fun effekt(charakter: Charakter) {
        super.effekt(charakter)
        if (charakter is Krieger) {
            charakter.schildVerlieren()
            charakter.ausdauer -= 10
        }

    }

    override fun aufheben(charakter: Charakter) {
        if (charakter is Krieger) charakter.schildAnlegen()
        charakter.ausdauer += 10
        println("${charakter.name} hat sein Schild wieder aufgehoben!")
    }
}