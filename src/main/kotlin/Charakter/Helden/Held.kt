package Charakter.Helden

import Charakter.Charakter
import Charakter.Gegner.Gegner
import Items.*
import Waffen.*
import ausgabeSchaden
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

    open fun angreifen(gegner: Gegner) {
        println("${this.name} holt zum Angriff aus!")
        Thread.sleep(1000)
        var maxSchaden: Int = staerke
        var schaden: Int = (0..maxSchaden).random()
        ausgabeSchaden(this,schaden, maxSchaden, gegner)
    }

    open fun verteidigen() {
        println("${this.name} reißt sich seine Arme vors Gesicht.")
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

    fun statusAnzeigen() {
        println("${this.name} hat noch ${this.lp} Lebenspunkte.")
        println("Folgende Items befinden sich im Inventar:")
        if (!inventar.isEmpty()) {
            for (item in this.inventar) {
                println(item.name)
            }
        } else {
            println("Dein Inventar ist leer.")
        }
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
}