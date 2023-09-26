package waffen

import charakter.Charakter

class Zauberstab: Waffe("Zauberstab") {
    val intelligenz: Int = (10..20).random()
    val ausdauer: Int = intelligenz / 2

    override fun anlegen(charakter: Charakter){
        charakter.intelligenz += intelligenz
        charakter.ausdauer += ausdauer
    }

    override fun ablegen(charakter: Charakter){
        charakter.intelligenz -= intelligenz
        charakter.ausdauer -= ausdauer
    }
}