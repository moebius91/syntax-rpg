package utils

/**
 * Erstellt einen Lauftext, indem es jedes Zeichen des gegebenen Strings `text` einzeln ausgibt.
 * Zwischen den einzelnen Zeichen gibt es eine kurze Verzögerung, um den Effekt eines "laufenden" Textes zu erzeugen.
 *
 * Achtung! Benötigt am Ende einen Zeilenumbruch bspw. mittels \n
 *
 * @param text Der String, der als Lauftext dargestellt werden soll.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */
fun erstelleLauftext(text: String) {
    val splittText: List<Char> = text.toList()

    splittText.forEach { char ->
        print(char)     // Original 200..300
        sleep(((150 .. 200).random()).toLong())
    }
}

/**
 * Lässt den Thread für eine Sekunde schlafen (1.000 Millisekunden).
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun sekundenschlaf() {
    Thread.sleep(1000)
}

/**
 * Lässt den Thread für eine spezifizierte Zeit schlafen.
 *
 * @param zeit Die Zeit in Millisekunden, für die der Thread schlafen soll.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun sleep(zeit: Long) {
    Thread.sleep(zeit)
}