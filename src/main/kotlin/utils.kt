import Charakter.Charakter
import Charakter.Gegner.Gegner
import Charakter.Helden.Druide
import Charakter.Helden.Held
import Charakter.Helden.Krieger
import Charakter.Helden.Magier
import Items.Heiltrank
import Items.Vitamine
import StatusEffekte.Buffs.Buff
import StatusEffekte.Debuffs.Debuff
import kotlin.Exception

fun sichereEingabe(eintraege:Int): Int {
    return try {
        val eingabe: Int = readln().toInt()
        if(eingabe < 0 || eingabe > eintraege) throw IllegalArgumentException("Du hast eine ungültige Zahl eingegeben! '$eingabe'")
        eingabe
    } catch (e: Exception) {
        println(e.message)
        sichereEingabe(eintraege)
    }
}

fun ausgabeSchaden(angreifer: Charakter, schaden: Int, maxSchaden:Int, gegner: Charakter) {
    val zahl1: Int = maxSchaden / 3
    val zahl2: Int = maxSchaden / 3 * 2

    when (schaden) {
        0 -> {
            println("${angreifer.name} hat nicht getroffen..")
        }
        in 1.. zahl1 -> {
            gegner.lp -= schaden
            println("${angreifer.name} hat geringen Schaden verursacht.. ($schaden)")
        }
        in 11 .. zahl2 -> {
            gegner.lp -= schaden
            println("${angreifer.name} hat Schaden verursacht. ($schaden)")
        }
        else -> {
            gegner.lp -= schaden
            println("${angreifer.name} hat großen Schaden verursacht! ($schaden)")
        }
    }
}

fun inventarAnlegen(held: Held) {
    held.inventar.add(Heiltrank())
    held.inventar.add(Heiltrank())
    held.inventar.add(Heiltrank())
    held.inventar.add(Vitamine())
}

fun charakterMenue(held: Held, gegner: Gegner, heldenliste: List<Held>): Boolean {
    if (held.waffenTyp.name == "Unbewaffnet") {
        held.waehleWaffe()
    }
    if (held is Krieger) {
        return kriegerMenue(held, gegner)
    } else if (held is Magier) {
        return magierMenue(held, gegner, heldenliste)
    } else if (held is Druide) {
        return druideMenue(held,gegner, heldenliste)
    }

    return false
}

fun kriegerMenue(held: Krieger, gegner: Gegner): Boolean {
    println("${held.name}: Wähle Deine Aktion:")
    println("1. Angreifen")
    println("2. Verteidigen")
    println("3. Status anzeigen")
    println("4. Item benutzen")
    val eingabe = sichereEingabe(4)
    return when(eingabe) {
        1 -> {
            held.angreifen(gegner)
            true
        }
        2 -> {
            held.verteidigen()
            true
        }
        3 -> {
            held.statusAnzeigen()
            false
        }
        else -> {
            held.itemBenutzen()
        }
    }
}

fun magierMenue(held: Magier, gegner: Gegner, heldenliste: List<Held>): Boolean {
    println("${held.name}: Wähle Deine Aktion:")
    println("1. Angreifen")
    println("2. Zauberangriff")
    println("3. Verteidigen")
    println("4. Entfluchen")
    println("5. Status anzeigen")
    println("6. Item benutzen")
    val eingabe = sichereEingabe(6)
    return when(eingabe) {
        1 -> {
            held.angreifen(gegner)
            true
        }
        2 -> {
            held.zaubern(gegner)
            true
        }
        3 -> {
            held.verteidigen()
            true
        }
        4 -> {
            held.entfluchen(heldenliste)
            true
        }
        5 -> {
            held.statusAnzeigen()
            false
        }
        else -> {
            held.itemBenutzen()
        }
    }
}

fun druideMenue(held: Druide, gegner: Gegner, heldenliste: List<Held>): Boolean {
    println("${held.name}: Wähle Deine Aktion:")
    println("1. Angreifen")
    println("2. Schadzauber")
    println("3. Verteidigen")
    println("4. Heilzauber")
    println("5. Status anzeigen")
    println("6. Item benutzen")
    val eingabe = sichereEingabe(6)
    return when(eingabe) {
        1 -> {
            held.angreifen(gegner)
            true
        }
        2 -> {
            held.schadenszauber(gegner)
            true
        }
        3 -> {
            held.verteidigen()
            true
        }
        4 -> {
            held.heilzauber(heldenliste)
            true
        }
        5 -> {
            held.statusAnzeigen()
            false
        }
        else -> {
            held.itemBenutzen()
        }
    }
}

fun buffsDebuffsEntfernen(heldenliste: List<Held>) {
    for (held in heldenliste) {
        if (held.buffs.isNotEmpty()) {
            var buffs: MutableList<Buff> = mutableListOf()
            for (buff in held.buffs) {
                buff.dauerRunde --
                if (buff.dauerRunde == 0) {
                    buffs.add(buff)
                }
            }
            for (buff in buffs) {
                held.buffs.remove(buff)
            }

        }

        if (held.debuffs.isNotEmpty()) {
            var debuffs: MutableList<Debuff> = mutableListOf()
            for (debuff in held.debuffs) {
                debuff.dauerRunde --
                if (debuff.dauerRunde == 0 || debuff.abgeklungen) {
                    debuffs.add(debuff)
                }
            }
            for (debuff in debuffs) {
                held.debuffs.remove(debuff)
            }
        }
    }
}

fun lebenspunkteAusgabe(gegnerliste: MutableList<Gegner>, heldenliste: List<Held>) {
    for (gegner in gegnerliste) {
        println("${gegner.name}: ${gegner.lp}")
    }
    for (held in heldenliste) {
        println("${held.name}: ${held.lp}")
    }
}