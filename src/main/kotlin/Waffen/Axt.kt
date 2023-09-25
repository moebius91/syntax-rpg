package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

class Axt: Waffe("Axt") {
    val staerke: Int = (20..30).random()

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
    }
}