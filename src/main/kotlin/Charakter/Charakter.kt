package Charakter

import StatusEffekte.Buffs.*
import StatusEffekte.Debuffs.*

open class Charakter {
    // Name
    open var name: String = "Charakter"

    // Lebenspunkte und maximale Lebenspunkte
    open var lp: Int = 100
    open var maxlp: Int = 100

    // Attribute für Heilung, Schaden und Verteidigung
    open var staerke: Int = (10..20).random()
    open var ausdauer: Int = (10..20).random()
    open var intelligenz: Int = (10..20).random()

    // Buff- und Debuffmap
    val buffs: MutableList<Buff> = mutableListOf()
    val debuffs: MutableList<Debuff> = mutableListOf()
}