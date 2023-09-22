package Waffen

import Charakter.Helden.Held

class Streitkolben: Waffe("Streitkolben") {
    val staerke: Int = (10..20).random()
    val ausdauer: Int = (10..20).random()

    override fun anlegen(held: Held){
        held.staerke += staerke
        held.ausdauer += ausdauer
    }

    override fun ablegen(held: Held){
        held.staerke -= staerke
        held.ausdauer -= ausdauer
    }
}