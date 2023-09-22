package Items

import Charakter.Helden.Held

class Heiltrank() : Item("Heiltrank") {
    override fun benutzen(held: Held): Boolean{
        if (held.lebenspunkte() < held.maxLebenspunkte()) {
            var heilung: Int = (held.maxLebenspunkte() - held.lebenspunkte()) / 2
            held.heilungErfahren(heilung)
            println("${held.name} wurde um $heilung Lebenspunkte geheilt.")
            return true
        }
        return false
    }

    override fun beschreibungAnzeigen() {
        println("Heiltrank: Füllt die Hälfte Deiner verloren LP auf.")
    }
}
