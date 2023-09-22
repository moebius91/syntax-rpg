package Waffen

import Charakter.Helden.Held

class Stab: Waffe("Stab") {
    val staerke: Int = (10..20).random()
    val intelligenz: Int = (10..20).random()

    override fun anlegen(held: Held){
        held.staerke += staerke
        held.intelligenz += intelligenz
    }

    override fun ablegen(held: Held){
        held.staerke -= staerke
        held.intelligenz -= intelligenz
    }
}