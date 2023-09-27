
import charakter.gegner.Gegner
import charakter.helden.Druide
import charakter.helden.Held
import charakter.helden.Krieger
import charakter.helden.Magier
import level.Level
import level.Verlies
import utils.heldenteamInventareAnlegen
import utils.inventarAnlegen

fun main() {
    // Verpacken der Charaktere in jeweilige Listen
    val heldenliste: MutableList<Held> = testHelden()

    // Testen der Levelklasse
    val level: Level = Verlies(heldenliste)
    level.spielrunde()
}

fun testHelden(): MutableList<Held> {
    val held1: Krieger = Krieger("Krieger")
    val held2: Druide = Druide("Druide")
    val held3: Magier = Magier("Magier")
    val heldenliste = mutableListOf(held1, held2, held3)
    heldenteamInventareAnlegen(heldenliste)
    return heldenliste
}


fun heldenTest(held: Held, gegner: Gegner) {
    inventarAnlegen(held)
    held.waehleWaffe()
    println("Lebenspunkte vor dem ersten Angriff:")

    println("Heldenlebenspunkte: ${held.lebenspunkte()}")
    println("Endgegnerlebenspunkte: ${gegner.lebenspunkte()}\n")
    Thread.sleep(1000)
    held.angreifen(gegner)
    println("Heldenlebenspunkte: ${held.lebenspunkte()}")
    println("Endgegnerlebenspunkte: ${gegner.lebenspunkte()}\n")
}