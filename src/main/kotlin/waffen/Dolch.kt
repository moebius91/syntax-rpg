package waffen

import charakter.Charakter

class Dolch: Waffe("Dolch") {
    val staerke: Int = (10..20).random()
    val intelligenz: Int = (10..20).random()
    val ausdauer: Int = ausdauer()

    private fun ausdauer(): Int {
        val ausdauer: Int = staerke + intelligenz
        return if (ausdauer < 30) {
            (5..10).random()
        } else {
            0
        }
    }

    override fun anlegen(charakter: Charakter){
        charakter.staerke += staerke
        charakter.intelligenz += intelligenz
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.staerke -= staerke
        charakter.intelligenz -= intelligenz
        charakter.ausdauer -= ausdauer
    }
}