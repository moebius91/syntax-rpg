package items

import charakter.helden.Held

open class Item(var name: String = "Unbekanntes Item", val verbrauchbar: Boolean = true) {
    val zahl: Int = (0..6).random()
    open var beschreibung: String = "Ein Dir unbekanntes Item."
    open fun benutzen(held: Held): Boolean {
        when (zahl) {
            0 -> {
                println("Nichts passiert.\n")
            }
            1 -> {
                println("Deine Schuhe sind jetzt grün.\n")
            }
            2 -> {
                println("Du weißt nicht, wie man dieses Item benutzt.\n")
            }
            3 -> {
                println("Dies gehört Dir nicht.\n")
            }
            4 -> {
                println("Der Himmel färbt sich rot.\n")
            }
            5 -> {
                println("Es ist kaputt.\n")
            }
            6 -> {
                println("Ein Huhn sitzt auf Deinem Kopf.\n")
            }
        }
        return true
    }

    open fun beschreibungAnzeigen() {
        println(beschreibung)
    }

    fun istVerbrauchbar(): Boolean {
        return this.verbrauchbar
    }

    fun zeigeName(){
        println(this.name)
    }

}