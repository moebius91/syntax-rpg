package Waffen

import Charakter.Helden.Held

class Schwert: Waffe("Schwert") {
    val staerke: Int = (20..30).random()

    override fun anlegen(held: Held){
        held.staerke += staerke
    }

    override fun ablegen(held: Held){
        held.staerke -= staerke
    }
}