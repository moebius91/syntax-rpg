package Waffen

import Charakter.Helden.Held

class Zauberstab: Waffe("Zauberstab") {
    val intelligenz: Int = (10..20).random()

    override fun anlegen(held: Held){
        held.ausdauer += intelligenz
    }

    override fun ablegen(held: Held){
        held.ausdauer -= intelligenz
    }
}