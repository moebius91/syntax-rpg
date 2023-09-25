package charakter.helden

import charakter.gegner.Gegner
import statuseffekte.debuffs.FluchDesMagiers
import waffen.*
import schadenAnwendenUndBerichten
import sichereEingabe

class Magier(name: String): Held(name) {
    // Klassenspezifische Attribute
    var mana: Int = 100 // Bisher noch quasi ungenutzt

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = 90
    override var maxlp = 90

    // Attribute für Schaden und Verteidigung
    override var intelligenz: Int = (20..30).random()

    override fun angreifen(gegner: Gegner): Boolean {
        return super.angreifen(gegner)
    }

    override fun verteidigen(): Boolean {
        return super.verteidigen()
    }

    fun zaubern(gegner: Gegner): Boolean {
        println("${this.name} wirkt einen starken Zauber.")
        var maxSchaden: Int = (intelligenz * 14.toDouble() / 10).toInt()
        var schaden: Int = (intelligenz * (7..14).random().toDouble() / 10).toInt()
        schadenAnwendenUndBerichten(this,schaden,maxSchaden, gegner)
        return true
    }

    fun verfluchen(gegner: Gegner): Boolean {
        var vorhanden: Boolean = false

        for (debuff in gegner.debuffs) {
            if (debuff.name == "FluchDesMagiers") {
                debuff.dauerRunde = 3
                vorhanden = true
                println("Der Fluch des Magiers hält wieder 3 Runden.")
            }
        }

        if (!vorhanden) {
            println("${gegner.name} wird mit dem Fluch des Magiers belegt für 3 Runden.")
            gegner.debuffs.add(FluchDesMagiers(gegner))
        }

        return true
    }

    fun entfluchen(heldenliste: List<Held>): Boolean {
        println("${this.name}, wer soll entflucht werden?")
        var zaehler: Int = 0
        for (held in heldenliste) {
            zaehler++
            println("$zaehler. ${held.name}")
        }
        val eingabe: Int = sichereEingabe(zaehler) -1
        val held: Held = heldenliste[eingabe]
        held.debuffs.removeIf { debuff ->
            if (debuff.name == "FluchDesDrachen") {
                debuff.abgeklungen = true
                true
            } else false
        }
        return true
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

    override fun menue(gegner: Gegner, heldenliste: List<Held>): Boolean {
        var held = this

        magierMenueAusgabe(held)
        val eingabe = sichereEingabe(7)
        return when(eingabe) {
            1 -> {
                held.angreifen(gegner)
            }
            2 -> {
                held.zaubern(gegner)
            }
            3 -> {
                held.verteidigen()
            }
            4 -> {
                held.entfluchen(heldenliste)
            }
            5 -> {
                held.verfluchen(gegner)
            }
            6 -> {
                held.statusAnzeigen()
            }
            else -> {
                held.itemBenutzen()
            }
        }
    }

    private fun magierMenueAusgabe(held: Magier) {
        println("${held.name}: Wähle Deine Aktion:")
        println("1. Angreifen")
        println("2. Zauberangriff")
        println("3. Verteidigen")
        println("4. Entfluchen")
        println("5. Verfluchen")
        println("6. Status anzeigen")
        println("7. Item benutzen")
    }
}