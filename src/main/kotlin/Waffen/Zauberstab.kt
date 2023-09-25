package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

class Zauberstab: Waffe("Zauberstab") {
    val intelligenz: Int = (10..20).random()

    override fun anlegen(charakter: Charakter){
        charakter.ausdauer += intelligenz
    }

    override fun ablegen(charakter: Charakter){
        charakter.ausdauer -= intelligenz
    }
}