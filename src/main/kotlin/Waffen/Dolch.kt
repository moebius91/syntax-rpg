package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

class Dolch: Waffe("Dolch") {
    val staerke: Int = (10..20).random()
    val intelligenz: Int = (10..20).random()

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.intelligenz += intelligenz
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.intelligenz -= intelligenz
    }
}