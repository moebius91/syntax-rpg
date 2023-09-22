import Charakter.Charakter
import Charakter.Gegner.*
import Charakter.Helden.*
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
            gegner.schadenNehmen(schaden)
            println("${angreifer.name} hat geringen Schaden verursacht.. ($schaden)")
        }
        in 11 .. zahl2 -> {
            gegner.schadenNehmen(schaden)
            println("${angreifer.name} hat Schaden verursacht. ($schaden)")
        }
        else -> {
            gegner.schadenNehmen(schaden)
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

fun heldenInventareAnlegen(heldenliste: List<Held>) {
    for (held in heldenliste) inventarAnlegen(held)
}

fun charakterMenue(held: Held, gegner: Gegner, heldenliste: List<Held>): Boolean {
    if (held.waffenTyp.name == "Unbewaffnet") {
        held.waehleWaffe()
    }
    if (held is Krieger) {
        return held.menue(gegner, heldenliste)
    } else if (held is Magier) {
        return held.menue(gegner, heldenliste)
    } else if (held is Druide) {
        return held.menue(gegner, heldenliste)
    }

    return false
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
        println("${gegner.name}: ${gegner.lebenspunkte()}")
    }
    for (held in heldenliste) {
        println("${held.name}: ${held.lebenspunkte()}")
    }
}

fun heldenInstanziieren(): Held {
    println("Welche Klasse soll Dein Charakter erhalten?")
    println("1. Krieger - 2. Druide - 3. Magier")
    val eingabeKlasse: Int = sichereEingabe(3)
    print("Wie soll Dein Charakter heißen: ")
    val eingabeName: String = readln()

    return when (eingabeKlasse) {
        1 -> {
            Krieger(eingabeName)
        }
        2 -> {
            Druide(eingabeName)
        }
        else -> {
            Magier(eingabeName)
        }
    }
}

fun heldenteamInstanziieren(): List<Held> {
    val heldenliste: MutableList<Held> = mutableListOf()
    repeat(3) {
        heldenliste.add(heldenInstanziieren())
    }

    return heldenliste.toList()
}

fun endgegnerInstanziieren(): MutableList<Gegner> {
    return mutableListOf(Drache())
}

fun toteSchwaermerEntfernen(gegnerliste: MutableList<Gegner>) {
    var speicher: MutableList<Gegner> = mutableListOf()
    for (gegner in gegnerliste) {
        if (gegner.lebenspunkte() < 0 && gegner is Schwaermer) {
            gegner.lebenspunkteSetzen(0)
            speicher.add(gegner)
        }
    }
    for (gegner in speicher) {
        gegnerliste.remove(gegner)
    }
}

fun heldenzug(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>) {
    val gegner: Gegner = gegnerliste.random()

    for (held in heldenliste) {
        buffsDebuffsAnwenden(held)

        var bedingung: Boolean = false
        while(!bedingung && held.lebenspunkte() > 0 && gegnerliste[0].lebenspunkte() > 0) {
            bedingung = charakterMenue(held,gegner,heldenliste)
        }
    }
}

fun buffsDebuffsAnwenden(held: Held) {
    if (held.buffs.isNotEmpty()) {
        for (buff in held.buffs) {
            buff.effekt(held)
        }
    }
    if (held.debuffs.isNotEmpty()) {
        for (debuff in held.debuffs) {
            debuff.effekt(held)
        }
    }
}

fun kampfrunde(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>){
    heldenzug(heldenliste,gegnerliste)
    toteSchwaermerEntfernen(gegnerliste)
    attackeGegner(gegnerliste, heldenliste)
    buffsDebuffsEntfernen(heldenliste)
}

fun attackeGegner(gegnerliste: MutableList<Gegner>, heldenliste: List<Held>) {
    val zufallszahl: Int = (1..5).random()
    var speicher: Gegner? = null

    var schwaermerGefressen: Boolean = false
    for (gegner in gegnerliste) {
        if (gegner is Drache && gegner !is Schwaermer) {
            when (zufallszahl) {
                1 -> {
                    gegner.feueratem(heldenliste)
                }

                2 -> {
                    gegner.fluchDesDrachen(heldenliste)
                }

                3 -> {
                    var schwaermerVorhanden: Boolean = false
                    for (gegner in gegnerliste) {
                        if (gegner is Schwaermer) schwaermerVorhanden = true
                    }
                    if (!schwaermerVorhanden) {
                        speicher = gegner.schwaermerBeschwoeren(gegnerliste)
                    } else {
                        if (gegnerliste[1] is Schwaermer) {
                            gegner.schwaermerFressen(gegnerliste[1] as Schwaermer)
                            schwaermerGefressen = true
                        }
                    }
                }
                4 -> {
                    gegner.angreifen(heldenliste.random())
                }
                else -> {
                    gegner.heilen()
                }
            }
        } else if (gegner is Schwaermer && !schwaermerGefressen) {
            listOf(
                {
                    println("Der Schwärmer versucht anzugreifen, es geht daneben.")
                },
                {
                    val held: Held = heldenliste.random()
                    println("Der Schwärmer greift ${held.name} an.")
                    gegner.angreifen(held)
                },
                {
                    gegner.feueratem(heldenliste)
                }
            ).random()
        }
    }

    if (speicher != null) {
        gegnerliste.add(speicher)
    }
}

fun spielrunde(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>) {
    val held1: Held = heldenliste[0]
    val held2: Held = heldenliste[1]
    val held3: Held = heldenliste[2]

    val drache: Gegner = gegnerliste[0]

    while (drache.lebenspunkte() > 0 && (held1.lebenspunkte() > 0 || held2.lebenspunkte() > 0 || held3.lebenspunkte() > 0)) {
        kampfrunde(heldenliste, gegnerliste)
        if (drache.lebenspunkte() < 0) {
            drache.lebenspunkteSetzen(0)
            if (gegnerliste.size == 2) gegnerliste[1].lebenspunkteSetzen(0)
            println("Der Drache wurde besiegt!")
            break
        }
        if (held1.lebenspunkte() < 0) {
            held1.lebenspunkteSetzen(0)
            println("${held1.name} ist gestorben")
        } else if (held2.lebenspunkte() < 0) {
            held2.lebenspunkteSetzen(0)
            println("${held2.name} ist gestorben")
        } else if (held3.lebenspunkte() < 0) {
            held3.lebenspunkteSetzen(0)
            println("${held3.name} ist gestorben")
        }

        if (held1.lebenspunkte() < 0) held1.lebenspunkteSetzen(0)
        if (held2.lebenspunkte() < 0) held2.lebenspunkteSetzen(0)
        if (held3.lebenspunkte() < 0) held3.lebenspunkteSetzen(0)
        if (held1.lebenspunkte() == 0 && held2.lebenspunkte() == 0 && held3.lebenspunkte() == 0) {
            println("Der Drache hat alle Helden besiegt.")
            break
        }

        lebenspunkteAusgabe(gegnerliste, heldenliste)
    }
}