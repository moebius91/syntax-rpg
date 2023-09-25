import charakter.gegner.*
import charakter.helden.*

fun main() {
    // Spielbegrüßung
    spielBegruessung()

    // Instanziiert die Heldengruppe
    // Der Spieler hat freie Auswahl über gewählte Klassen und Namen.
    val heldenliste: MutableList<Held> = heldenteamInstanziieren()
    // Anlegen der Heldeninventare
    heldenteamInventareAnlegen(heldenliste)

    // Instanziiert den Endgegner
    val gegnerliste: MutableList<Gegner> = endgegnerInstanziieren()


    // Startet das Spiel bis entweder der Endgegner oder die Helden tot sind.
    spielrunde(heldenliste,gegnerliste)

}