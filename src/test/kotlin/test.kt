import charakter.gegner.*
import charakter.helden.*

fun main() {
    // Instanziieren der Helden + Endgegner
    val held1: Krieger = Krieger("Krieger")
    val held2: Druide = Druide("Druide")
    val held3: Magier = Magier("Magier")

    // Verpacken der Charaktere in jeweilige Listen
    val heldenliste: MutableList<Held> = mutableListOf(held1, held2, held3)
    val gegnerliste: MutableList<Gegner> = endgegnerInstanziieren()
    gegnerliste.add(Ork())
    gegnerliste.add(Schwaermer())

    // Inventar für die Helden anlegen
    heldenteamInventareAnlegen(heldenliste)
    spielrunde(heldenliste,gegnerliste)
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