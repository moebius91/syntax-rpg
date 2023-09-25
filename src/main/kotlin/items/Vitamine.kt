package items

import charakter.helden.Held

class Vitamine: Item("Vitamine") {
    override var beschreibung: String = "Vitamine: Sie erhöhen Deine gesamten Werte um 10%."
    override fun benutzen(held: Held): Boolean{
        held.staerke += held.staerke /10
        held.intelligenz += held.intelligenz /10
        held.ausdauer += held.ausdauer /10
        return true
    }

    override fun beschreibungAnzeigen() {
        println(beschreibung)
    }
}