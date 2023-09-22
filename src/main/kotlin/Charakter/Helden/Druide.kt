package Charakter.Helden

import Charakter.Gegner.Gegner
import Waffen.*
import ausgabeSchaden
import sichereEingabe

class Druide(name: String): Held(name) {
    var mana: Int = 100
    override var maxlp = 120

    var tierForm: Boolean = false

    override var staerke: Int = (15..25).random()
    override var ausdauer: Int = (15..25).random()
    override var intelligenz: Int = (20..30).random()

    override fun angreifen(gegner: Gegner) {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        if (tierForm) maxSchaden = (maxSchaden * 15) / 10
        val schaden: Int = (0..maxSchaden).random()
        ausgabeSchaden(this,schaden, maxSchaden, gegner)
    }

    override fun verteidigen() {
        super.verteidigen()
    }

    fun schadenszauber(gegner: Gegner) {
        if (tierForm) {
            println("${this.name} stellt sich auf seine Hinterläufe und wirkt einen Zauber.")
            Thread.sleep(1000)
            val maxSchaden: Int = (intelligenz * 15) / 10
            val schaden: Int = (0..maxSchaden).random()
            ausgabeSchaden(this,schaden, maxSchaden, gegner)
        } else {
            println("${this.name} baut sich auf und wirkt einen Zauber.")
            Thread.sleep(1000)
            val maxSchaden: Int = intelligenz
            val schaden: Int = (0..maxSchaden).random()
            ausgabeSchaden(this,schaden, maxSchaden, gegner)
        }
    }

    fun heilzauber(heldenliste: List<Held>) {
        println("${this.name}, wen möchtest Du heilen?")
        var zaehler: Int = 0
        for (held in heldenliste) {
            zaehler++
            println("$zaehler. ${held.name}")
        }
        val eingabe: Int = sichereEingabe(zaehler) -1
        val held: Held = heldenliste[eingabe]
        held.lp += held.maxlp / 10
        if (held.lp > held.maxlp) held.lp = held.maxlp
        TODO("Ausgabe wie viele Punkte aufgefüllt wurden")
    }

    fun formWechseln(gegner: Gegner) {
        this.tierForm = !tierForm
        if (tierForm) {
            println("${this.name} verwandelt sich in ein Tier.")
            angreifen(gegner)
        }
    }

    override fun waehleWaffe() {
        println("${this.name}, wähle Deine Waffe aus:")
        println("1. Stab")
        println("2. Streitkolben")
        var eingabe: Int = sichereEingabe(2)
        this.waffenTyp = if (eingabe == 1) Stab() else Streitkolben()
        waffenTyp.anlegen(this)
    }
}