import Charakter.Gegner.*
import Charakter.Helden.*

fun main() {
    // Spielbegrüßung
    spielBegruessung()

    // Instanziiert die Heldengruppe
    // Der Spieler hat freie Auswahl über gewählte Klassen und Namen.
    // Inklusive anlegen des Inventars für jeden Helden.
    val heldenliste: List<Held> = heldenteamInstanziieren()
    heldenteamInventareAnlegen(heldenliste)

    // Instanziiert den Endgegner
    val gegnerliste: MutableList<Gegner> = endgegnerInstanziieren()

    // Startet das Spiel bis entweder der Endgegner oder die Helden tot sind.
    spielrunde(heldenliste,gegnerliste)

}