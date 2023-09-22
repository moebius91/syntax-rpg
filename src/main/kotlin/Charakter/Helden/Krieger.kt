package Charakter.Helden

import Charakter.Gegner.Gegner
import StatusEffekte.Buffs.SchildGebrauch
import Waffen.*
import ausgabeSchaden
import sichereEingabe

class Krieger(name: String): Held(name) {
    // Klassenspezifische Attribute
    var wut: Int = 0 // Bisher ungenutzt bzw.
    var schildVorhanden: Boolean = true

    // Lebenspunkte und maximale Lebenspunkte
    override var lp = 150
    override var maxlp = 150

    // Attribute für Schaden und Verteidigung
    override var staerke: Int = (20..30).random()
    override var ausdauer: Int = (20..30).random()

    override fun angreifen(gegner: Gegner): Boolean {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        var schaden: Int
        if (waffenTyp.name.lowercase() != "unbewaffnet") {
            schaden = (0..maxSchaden).random() + (wut / 2)
        } else {
            schaden = (0..maxSchaden).random()
        }

        if (schaden > 50) schaden = 50
        ausgabeSchaden(this, schaden, maxSchaden, gegner)
        return true
    }

    override fun verteidigen(): Boolean {
        if (schildVorhanden) {
            this.buffs.add(SchildGebrauch(this))
            println("${this.name} schützt sich mit seinem Schild.")
        } else {
            println("${this.name} reißt sich seine Hände vors Gesicht.")
        }

        return true
    }

    fun schildVerlieren() {
        schildVorhanden = false
    }

    fun schildAnlegen() {
        schildVorhanden = true
    }

    override fun waehleWaffe() {
        println("${this.name}, wähle Deine Waffe aus:")
        println("1. Axt")
        println("2. Schwert")
        var eingabe: Int = sichereEingabe(2)
        this.waffenTyp = if (eingabe == 1) Axt() else Schwert()
        waffenTyp.anlegen(this)
    }

    private fun kriegerMenueAusgabe(held: Krieger) {
        println("${held.name}: Wähle Deine Aktion:")
        println("1. Angreifen")
        println("2. Verteidigen")
        println("3. Status anzeigen")
        println("4. Item benutzen")
    }

    override fun menue(gegner: Gegner, heldenliste: List<Held>): Boolean {
        val held: Krieger = this
        kriegerMenueAusgabe(held)
        val eingabe = sichereEingabe(4)
        return when(eingabe) {
            1 -> {
                held.angreifen(gegner)
            }
            2 -> {
                held.verteidigen()
            }
            3 -> {
                held.statusAnzeigen()
            }
            else -> {
                held.itemBenutzen()
            }
        }
    }
}

