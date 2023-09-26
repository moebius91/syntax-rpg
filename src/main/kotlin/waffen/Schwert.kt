package waffen

import charakter.Charakter

class Schwert: Waffe("Schwert") {
    val staerke: Int = (20..30).random()
    val ausdauer: Int = staerke / 3 * 2

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.ausdauer -= ausdauer
    }
}