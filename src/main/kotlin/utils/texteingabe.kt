package utils

/**
 * Liest eine Ganzzahl vom Benutzer ein und stellt sicher, dass sie innerhalb eines bestimmten Bereichs liegt.
 *
 * @param eintraege Die Obergrenze für die akzeptierte Eingabe (die Untergrenze ist 0).
 * @return Die eingelesene und validierte Ganzzahl.
 * @throws IllegalArgumentException Wenn die Eingabe außerhalb des vorgegebenen Bereichs liegt.
 * @throws Exception Wenn bei der Konvertierung der Eingabe ein Fehler auftritt.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */
fun sichereEingabe(eintraege:Int): Int {
    return try {
        val eingabe = readln().replace(".", "").toInt()
        if(eingabe < 0 || eingabe > eintraege) throw IllegalArgumentException("Du hast eine ungültige Zahl eingegeben! '$eingabe'")
        eingabe
    } catch (ex: Exception) {
        println(ex.message)
        sichereEingabe(eintraege)
    }
}