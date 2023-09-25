package Charakter.Gegner

import Waffen.*

class Ork: Gegner() {
    // Name
    override var name: String = "Orkkrieger"

    // Lebenspunkte unt maximale Lebenspunkte
    override var maxlp: Int = 120
    override var lp: Int = 120

    override var intelligenz: Int = (1..10).random()

    init {
        this.waehleWaffe()
        waffenTyp.anlegen(this)
    }
    fun waehleWaffe() {
        this.waffenTyp = listOf(Schwert(),Dolch(),Axt(),Streitkolben()).random()
    }

    override fun verteidigen() {
        println("${this.name} sieht aus, als würde er einen Angriff erwarten!")
        // TODO("Buff hinzufügen der die Verteidigung kurzzeitig erhöht.")
    }
}