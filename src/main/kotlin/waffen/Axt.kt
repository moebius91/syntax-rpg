package waffen

import charakter.Charakter

class Axt: Waffe("Axt") {
    val staerke: Int = (20..30).random()
    val ausdauer: Int = staerke / 2

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.ausdauer -= ausdauer
    }
}