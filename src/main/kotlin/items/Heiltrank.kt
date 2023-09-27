package items

import charakter.helden.Held

class Heiltrank() : Item("Heiltrank") {
    override fun benutzen(held: Held): Boolean{
        if (held.lebenspunkte() < held.maxLebenspunkte()) {
            var heilung: Int = held.maxLebenspunkte() / 2
            heilung = held.heilungErfahren(heilung)
            println("${held.name} wurde um $heilung Lebenspunkte geheilt.\n")
            return true
        }
        return false
    }

    override fun beschreibungAnzeigen() {
        println("Heiltrank: Füllt die Hälfte Deiner LP auf.")
    }
}
