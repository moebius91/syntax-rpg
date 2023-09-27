package utils

import charakter.Charakter
import charakter.helden.Held
import statuseffekte.buffs.Buff
import statuseffekte.debuffs.Debuff

/**
 * Wendet alle aktiven Buffs auf einen bestimmten Helden an.
 * Die Funktion durchläuft die Liste der Buffs des Helden und führt deren Effekte aus.
 *
 * @param held Das Held-Objekt, auf das die Buffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsAnwenden(charakter: Charakter) {
    if (charakter.buffs.isNotEmpty()) {
        for (buff in charakter.buffs) {
            buff.effekt(charakter)
        }
    }
}

/**
 * Wendet alle aktiven Debuffs auf einen bestimmten Helden an.
 * Die Funktion durchläuft die Liste der Debuffs des Helden und führt deren Effekte aus.
 *
 * @param held Das Held-Objekt, auf das die Debuffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun debuffsAnwenden(charakter: Charakter) {
    if (charakter.debuffs.isNotEmpty()) {
        for (debuff in charakter.debuffs) {
            debuff.effekt(charakter)
        }
    }
}

/**
 * Wendet sowohl Buffs als auch Debuffs auf einen bestimmten Helden an.
 * Die Funktion ruft intern `buffsAnwenden` und `debuffsAnwenden` auf.
 *
 * @param held Das Held-Objekt, auf das Buffs und Debuffs angewendet werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsDebuffsAnwenden(charakter: Charakter) {
    buffsAnwenden(charakter)
    debuffsAnwenden(charakter)
}

/**
 * Reduziert die Dauer der Buffs eines Helden und entfernt abgelaufene Buffs.
 * Geht durch die Liste der aktiven Buffs und verringert ihre Dauer,
 * sammelt abgelaufene Buffs in einer temporären Liste und entfernt sie.
 *
 * @param held Der Held, dessen Buffs bearbeitet werden.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsEntfernen(charakter: Charakter) {
    if (charakter.buffs.isNotEmpty()) {
        val buffs: MutableList<Buff> = mutableListOf()
        for (buff in charakter.buffs) {
            buff.dauerRunde --
            if (buff.dauerRunde == 0) {
                buffs.add(buff)
                buff.aufheben(charakter)
            }
        }
        for (buff in buffs) {
            charakter.buffs.remove(buff)
        }
    }
}

/**
 * Reduziert die Dauer der Debuffs eines Helden und entfernt abgelaufene oder abgeklungene Debuffs.
 * Geht durch die Liste der aktiven Debuffs, verringert ihre Dauer und
 * sammelt abgelaufene oder abgeklungene Debuffs in einer temporären Liste, um sie dann zu entfernen.
 *
 * @param held Der Held, dessen Debuffs bearbeitet werden.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun debuffsEntfernen(charakter: Charakter) {
    if (charakter.debuffs.isNotEmpty()) {
        val debuffs: MutableList<Debuff> = mutableListOf()
        for (debuff in charakter.debuffs) {
            debuff.dauerRunde --
            if (debuff.dauerRunde == 0 || debuff.abgeklungen) {
                debuffs.add(debuff)
                debuff.aufheben(charakter)
            }
        }
        for (debuff in debuffs) {
            charakter.debuffs.remove(debuff)
        }
    }
}

/**
 * Entfernt Buffs und Debuffs für eine Liste von Helden.
 * Ruft die Funktionen `buffsEntfernen` und `debuffsEntfernen` für jeden Helden in der Liste auf.
 *
 * @param heldenliste Die Liste der Held-Objekte, deren Buffs und Debuffs entfernt werden sollen.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsDebuffsEntfernen(charakterliste: List<Charakter>) {
    for (charakter in charakterliste) {
        buffsEntfernen(charakter)
        debuffsEntfernen(charakter)
    }
}

/**
 * Überprüft, ob ein bestimmter Buff in der Buff-Liste des Helden vorhanden ist.
 *
 * @param held Das Held-Objekt, dessen Buffs überprüft werden sollen.
 * @param buffName Der Name des Buffs, nach dem gesucht wird.
 * @return Gibt `true` zurück, wenn der Buff vorhanden ist, sonst `false`.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun buffsVorAngriffPruefen(held: Held, buffName: String): Boolean {
    if (held.buffs.isNotEmpty()) {
        for (buff in held.buffs) {
            if (buff.name == buffName) {
                return true
            }
        }
    }
    return false
}

/**
 * Überprüft, ob ein bestimmter Debuff in der Debuff-Liste des Helden vorhanden ist.
 *
 * @param held Das Held-Objekt, dessen Debuffs überprüft werden sollen.
 * @param debuffName Der Name des Debuffs, nach dem gesucht wird.
 * @return Gibt `true` zurück, wenn der Debuff vorhanden ist, sonst `false`.
 *
 * @author Funktion: Jan-Nikolas Othersen | KDOC: Generiert mit ChatGPT
 */

fun debuffsVorAngriffPruefen(held: Held, debuffName: String): Boolean {
    if (held.debuffs.isNotEmpty()) {
        for (debuff in held.debuffs) {
            if (debuff.name == debuffName) {
                return true
            }
        }
    }
    return false
}