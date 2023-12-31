package waffen

import charakter.Charakter

class Streitkolben: Waffe("Streitkolben") {
    val staerke: Int = (10..20).random()
    val ausdauer: Int = staerke + 10

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.ausdauer -= ausdauer
    }
}