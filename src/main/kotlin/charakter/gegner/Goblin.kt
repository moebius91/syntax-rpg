package charakter.gegner

import waffen.Dolch
import waffen.Schwert

class Goblin: Gegner() {
    // Name
    override var name: String = "Goblin"

    // Lebenspunkte unt maximale Lebenspunkte
    override var maxlp: Int = 70
    override var lp: Int = 70

    override var staerke: Int = (10..30).random()
    override var ausdauer: Int = (5..20).random()
    override var intelligenz: Int = (1..5).random()

    init {
        this.waehleWaffe()
        waffenTyp.anlegen(this)
    }
    fun waehleWaffe() {
        this.waffenTyp = listOf(Schwert(), Dolch()).random()
    }

    override fun verteidigen() {
        println("${this.name} sieht aus, als würde er einen Angriff erwarten!\n")
        // TODO("Buff hinzufügen der die Verteidigung kurzzeitig erhöht.")
    }
}