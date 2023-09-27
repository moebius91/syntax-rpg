
import charakter.gegner.Gegner
import charakter.helden.Held
import utils.*

fun main() {
    // Spielbegrüßung
    spielBegruessung()

    // Instanziiert die Heldengruppe
    // Der Spieler hat freie Auswahl über gewählte Klassen und Namen.
    val heldenliste: MutableList<Held> = heldenteamInstanziieren()

    // Instanziiert den Endgegner
    val gegnerliste: MutableList<Gegner> = endgegnerUndGegnerlisteInstanziieren()

    // Startet das Spiel bis entweder der Endgegner oder die Helden tot sind.
    spielrunde(heldenliste,gegnerliste)
}