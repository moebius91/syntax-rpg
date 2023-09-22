package Charakter.Helden

import Charakter.Gegner.Gegner
import StatusEffekte.Debuffs.FluchDesDrachen
import Waffen.*
import ausgabeSchaden
import sichereEingabe

class Magier(name: String): Held(name) {
    var mana: Int = 100
    override var maxlp = 90

    override var intelligenz: Int = (20..30).random()

    override fun angreifen(gegner: Gegner) {
        super.angreifen(gegner)
    }

    override fun verteidigen() {
        super.verteidigen()
    }

    fun zaubern(gegner: Gegner) {
        println("${this.name} wirkt einen Zauber.")
        var maxSchaden: Int = (intelligenz * 14.toDouble() / 10).toInt()
        var schaden: Int = (intelligenz * (7..14).random().toDouble() / 10).toInt()
        ausgabeSchaden(this,schaden,maxSchaden, gegner)
    }

    fun entfluchen(heldenliste: List<Held>) {
        println("${this.name}, wer soll entflucht werden?")
        var zaehler: Int = 0
        for (held in heldenliste) {
            zaehler++
            println("$zaehler. ${held.name}")
        }
        val eingabe: Int = sichereEingabe(zaehler) -1
        val held: Held = heldenliste[eingabe]
        held.debuffs.remove(FluchDesDrachen(held))
    }

    override fun waehleWaffe() {
        println("${this.name}, wähle Deine Waffe aus:")
        println("1. Schwert")
        println("2. Stab")
        println("3. Dolch")
        println("4. Zauberstab")
        var eingabe: Int = sichereEingabe(4)
        this.waffenTyp = if (eingabe == 1) {
            Schwert()
        } else if (eingabe == 2) {
            Stab()
        } else if (eingabe == 3) {
            Dolch()
        } else {
            Zauberstab()
        }
        waffenTyp.anlegen(this)
    }
}