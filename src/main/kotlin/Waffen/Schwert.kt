package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

class Schwert: Waffe("Schwert") {
    val staerke: Int = (20..30).random()

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
    }
}