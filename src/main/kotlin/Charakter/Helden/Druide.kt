package Charakter.Helden

import Charakter.Gegner.Gegner
import Waffen.*
import anwendenUndBerichtenSchaden
import sichereEingabe

class Druide(name: String): Held(name) {
    // Klassenspezifische Attribute
    var mana: Int = 100 // Bisher noch quasi ungenutzt
    var tierForm: Boolean = false

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = 120
    override var maxlp: Int = 120

    // Attribute für Schaden und Verteidigung
    override var staerke: Int = (15..25).random()
    override var ausdauer: Int = (15..25).random()
    override var intelligenz: Int = (20..30).random()

    override fun angreifen(gegner: Gegner): Boolean {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        if (tierForm) maxSchaden = (maxSchaden * 15) / 10
        val schaden: Int = (0..maxSchaden).random()
        anwendenUndBerichtenSchaden(this,schaden, maxSchaden, gegner)
        return true
    }

    override fun verteidigen(): Boolean {
        return super.verteidigen()
    }

    fun schadenszauber(gegner: Gegner): Boolean {
        if (tierForm) {
            println("${this.name} stellt sich auf seine Hinterläufe und wirkt einen Zauber.")
            Thread.sleep(1000)
            val maxSchaden: Int = (intelligenz * 15) / 10
            val schaden: Int = (0..maxSchaden).random()
            anwendenUndBerichtenSchaden(this,schaden, maxSchaden, gegner)
        } else {
            println("${this.name} baut sich auf und wirkt einen Zauber.")
            Thread.sleep(1000)
            val maxSchaden: Int = intelligenz
            val schaden: Int = (0..maxSchaden).random()
            anwendenUndBerichtenSchaden(this,schaden, maxSchaden, gegner)
        }

        return true
    }

    fun heilzauber(heldenliste: List<Held>): Boolean {
        println("${this.name}, wen möchtest Du heilen?")
        var zaehler: Int = 0
        for (held in heldenliste) {
            zaehler++
            println("$zaehler. ${held.name}")
        }
        val eingabe: Int = sichereEingabe(zaehler) -1
        val held: Held = heldenliste[eingabe]
        held.heilungErfahren(maxLebenspunkte() / 10)
        if (lebenspunkte() > maxLebenspunkte()) held.lebenspunkteSetzen(maxLebenspunkte())
        println("${held.name} wurde um mindestens ${maxLebenspunkte() / 10} Lebenspunkte geheilt.")
        return true
    }

    fun formWechseln(gegner: Gegner): Boolean {
        this.tierForm = !tierForm
        return if (tierForm) {
            println("${this.name} verwandelt sich in ein Tier.")
            angreifen(gegner)
        } else {
            println("${this.name} hat sich zurückverwandelt.")
            false
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

    private fun druideMenueAusgabe(held: Druide) {
        println("${held.name}: Wähle Deine Aktion:")
        println("1. Angreifen")
        println("2. Schadzauber")
        println("3. Verteidigen")
        println("4. Heilzauber")
        println("5. Form wechseln")
        println("6. Status anzeigen")
        println("7. Item benutzen")
    }

    override fun menue(gegner: Gegner, heldenliste: List<Held>): Boolean {
        val held: Druide = this
        druideMenueAusgabe(held)
        val eingabe = sichereEingabe(6)
        return when(eingabe) {
            1 -> {
                held.angreifen(gegner)
            }
            2 -> {
                held.schadenszauber(gegner)
            }
            3 -> {
                held.verteidigen()
            }
            4 -> {
                held.heilzauber(heldenliste)
            }
            5 -> {
                held.formWechseln(gegner)
            }
            6 -> {
                held.statusAnzeigen()
                false
            }
            else -> {
                held.itemBenutzen()
            }
        }
    }
}