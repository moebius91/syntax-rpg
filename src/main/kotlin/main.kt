import Charakter.Gegner.*
import Charakter.Helden.*

fun main() {
    // Spielbegrüßung
    spielBegruessung()
    //TODO("Heldenliste als MutableListe um tote Spieler zu entfernen.")

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