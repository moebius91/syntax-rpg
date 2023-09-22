package Items

import Charakter.Helden.Held

class Heiltrank() : Item("Heiltrank") {
    override fun benutzen(held: Held): Boolean{
        if (held.lp < held.maxlp) {
            var heilung: Int = (held.maxlp - held.lp) / 2
            held.lp += heilung
            println("${held.name} wurde um $heilung Lebenspunkte geheilt.")
            return true
        }
        return false
    }

    override fun beschreibungAnzeigen() {
        println("Heiltrank: Füllt die Hälfte Deiner verloren LP auf.")
    }
}
