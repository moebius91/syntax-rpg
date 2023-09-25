package waffen

import charakter.Charakter

class Stab: Waffe("Stab") {
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