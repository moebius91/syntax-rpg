package Items

import Charakter.Helden.Held

class Vitamine: Item("Vitamine") {
    override fun benutzen(held: Held): Boolean{
        held.staerke += held.staerke /10
        held.intelligenz += held.intelligenz /10
        held.ausdauer += held.ausdauer /10
        return true
    }

    override fun beschreibungAnzeigen() {
        println("Vitamine: Sie erhöhen Deine gesamten Werte um 10%.")
    }
}