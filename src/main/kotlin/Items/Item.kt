package Items

import Charakter.Helden.Held

open class Item(var name: String = "Unbekanntes Item", val verbrauchbar: Boolean = true) {
    val zahl: Int = (0..6).random()
    open fun benutzen(held: Held): Boolean {
        when (zahl) {
            0 -> {
                println("Nichts passiert.")
            }
            1 -> {
                println("Deine Schuhe sind jetzt grün.")
            }
            2 -> {
                println("Du weißt nicht, wie man dieses Item benutzt.")
            }
            3 -> {
                println("Dies gehört Dir nicht.")
            }
            4 -> {
                println("Der Himmel färbt sich rot.")
            }
            5 -> {
                println("Es ist kaputt.")
            }
            6 -> {
                println("Ein Huhn sitzt auf Deinem Kopf.")
            }
        }
        return true
    }

    open fun beschreibungAnzeigen() {
        println("Ein Dir unbekanntes Item.")
    }

    fun istVerbrauchbar(): Boolean {
        return this.verbrauchbar
    }

    fun zeigeName(){
        println(this.name)
    }
}