package Charakter.Helden

import Charakter.Charakter
import Charakter.Gegner.Gegner
import Items.*
import Waffen.*
import anwendenUndBerichtenSchaden
import sichereEingabe

open class Held(override var name:String): Charakter() {
    // Erfahrungspunkte
    var ep: Int = 0

    // Lebenspunkte und maximale Lebenspunkte
    override var lp: Int = 100
    override var maxlp: Int = 100

    // Attribute für Heilung, Schaden und Verteidigung
    override var staerke: Int = (10..20).random()
    override var ausdauer: Int = (10..20).random()
    override var intelligenz: Int = (10..20).random()

    // Ausrüstung
    var waffenTyp: Waffe = Waffe("Unbewaffnet")
    val inventar: MutableList<Item> = mutableListOf()

    open fun angreifen(gegner: Gegner): Boolean {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        var schaden: Int = (0..maxSchaden).random()
        anwendenUndBerichtenSchaden(this,schaden, maxSchaden, gegner)
        return true
    }

    open fun verteidigen(): Boolean {
        println("${this.name} reißt sich seine Arme vors Gesicht.")
        return true
    }

    fun itemBenutzen(): Boolean {
        println("Welches Item willst Du benutzen?")
        return if (!inventar.isEmpty()) {
            var zaehler: Int = 0
            for (item in this.inventar) {
                zaehler++
                println("$zaehler. ${item.name}")
            }
            var eingabe: Int = sichereEingabe(zaehler) -1
            inventar[eingabe].benutzen(this)
            inventar.removeAt(eingabe)
            true
        } else {
            println("Dein Inventar ist leer.")
            false
        }
    }

    fun statusAnzeigen(): Boolean {
        println("${this.name} hat noch ${this.lp} Lebenspunkte.")
        println("Folgende Items befinden sich im Inventar:")
        if (!inventar.isEmpty()) {
            for (item in this.inventar) {
                println(item.name)
            }
        } else {
            println("Dein Inventar ist leer.")
        }
        return false
    }

    open fun waehleWaffe() {
        println("Wähle Deine Waffe aus:")
        println("1. Dolch")
        println("2. Schwert")
        var eingabe: Int = sichereEingabe(2)
        this.waffenTyp = if (eingabe == 1) Dolch() else Schwert()
        waffenTyp.anlegen(this)
    }

    fun entwaffnen() {
        waffenTyp.ablegen(this)
        waffenTyp = Waffe("Unbewaffnet")
        waffenTyp.anlegen(this)
    }

    open fun menue(gegner: Gegner, heldenliste: List<Held>): Boolean {
        return false
    }
}