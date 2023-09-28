
import charakter.helden.Held
import level.Level
import utils.heldenteamInstanziieren
import utils.spielBegruessung

fun main() {
    // Spielbegrüßung
    spielBegruessung()

    // Instanziiert die Heldengruppe
    // Der Spieler hat freie Auswahl über gewählte Klassen und Namen.
    val heldenliste: MutableList<Held> = heldenteamInstanziieren()

    // Levelaufruf Verlies + enthält Gegnergruppe
    val level: Level = Level(heldenliste)
    level.spielrunde()
}