package Charakter

import StatusEffekte.Buffs.*
import StatusEffekte.Debuffs.*
import Waffen.Waffe

open class Charakter {
    // Name
    open var name: String = "Charakter"

    // Lebenspunkte und maximale Lebenspunkte
    protected open var maxlp: Int = 100
    protected open var lp: Int = 100

    // Attribute für Heilung, Schaden und Verteidigung
    open var staerke: Int = (10..20).random()
    open var ausdauer: Int = (10..20).random()
    open var intelligenz: Int = (10..20).random()

    // Buff- und Debuffliste
    val buffs: MutableList<Buff> = mutableListOf()
    val debuffs: MutableList<Debuff> = mutableListOf()

    // Ausrüstung
    var waffenTyp: Waffe = Waffe("Unbewaffnet")

    open fun schadenNehmen(schaden: Int): Int {
        var gesamtschaden: Int = schaden-(ausdauer / 10)
        if (gesamtschaden < 0) gesamtschaden = 0
        if (gesamtschaden > lp) gesamtschaden = lp
        lp -= gesamtschaden
        return gesamtschaden
    }

    open fun heilungErfahren(heilwert: Int): Int {
        var fehlendeLp: Int = maxlp - lp
        var heilwertFinal: Int = heilwert
        if (heilwert > fehlendeLp) heilwertFinal = fehlendeLp
        lp += heilwertFinal
        return heilwertFinal
    }

    fun lebenspunkteSetzen(wert: Int) {
        this.lp = wert
        if (lp > maxlp) lp = maxlp
    }

    fun lebenspunkte(): Int {
        return this.lp
    }

    fun maxLebenspunkteSetzen(wert:Int) {
        this.maxlp = wert
    }

    fun maxLebenspunkte(): Int {
        return this.maxlp
    }
}