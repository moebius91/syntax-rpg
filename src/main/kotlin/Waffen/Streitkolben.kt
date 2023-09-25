package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

class Streitkolben: Waffe("Streitkolben") {
    val staerke: Int = (10..20).random()
    val ausdauer: Int = (10..20).random()

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.ausdauer -= ausdauer
    }
}