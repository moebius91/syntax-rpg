import Charakter.Gegner.*
import Charakter.Helden.*
import StatusEffekte.Buffs.*
import StatusEffekte.Debuffs.*
fun main() {
    // Instanziieren der Helden + Endgegner
    val held1: Krieger = Krieger("Krieger")
    val held2: Druide = Druide("Druide")
    val held3: Magier = Magier("Magier")
    val drache: Drache = Drache()

    // Verpacken der Charaktere in jeweilige Listen
    val heldenliste: List<Held> = listOf(held1, held2, held3)
    val gegnerliste: MutableList<Gegner> = mutableListOf(drache)

    // Inventar für die Helden anlegen
    for (held in heldenliste) inventarAnlegen(held)

    while (drache.lp > 0 && (held1.lp > 0 || held2.lp > 0 || held3.lp > 0)) {
        spielrunde(heldenliste, gegnerliste)
        if (drache.lp < 0) {
            drache.lp = 0
            gegnerliste[1].lp = 0
            println("Der Drache wurde besiegt!")
        }
        if (held1.lp < 0) {
            held1.lp = 0
            println("${held1.name} ist gestorben")
        } else if (held2.lp < 0) {
            held2.lp = 0
            println("${held2.name} ist gestorben")
        } else if (held3.lp < 0) {
            held3.lp = 0
            println("${held3.name} ist gestorben")
        }

        if (held1.lp < 0) held1.lp = 0
        if (held2.lp < 0) held2.lp = 0
        if (held3.lp < 0) held3.lp = 0
        if (held1.lp == 0 && held2.lp == 0 && held3.lp == 0) {
            println("Der Drache hat alle Helden besiegt.")
        }

        lebenspunkteAusgabe(gegnerliste, heldenliste)
    }

    readln()
}


fun heldenTest(held: Held, gegner: Gegner) {
    inventarAnlegen(held)
    held.waehleWaffe()
    println("Lebenspunkte vor dem ersten Angriff:")

    println("Heldenlebenspunkte: ${held.lp}")
    println("Endgegnerlebenspunkte: ${gegner.lp}\n")
    Thread.sleep(1000)
    held.angreifen(gegner)
    println("Heldenlebenspunkte: ${held.lp}")
    println("Endgegnerlebenspunkte: ${gegner.lp}\n")
}

fun spielrunde(heldenliste: List<Held>, gegnerliste: MutableList<Gegner>){
    val gegner: Gegner = gegnerliste.random()

    for (held in heldenliste) {
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

        var bedingung: Boolean = false
        while(!bedingung && held.lp > 0) {
            bedingung = charakterMenue(held,gegner,heldenliste)
        }
    }

    attackeGegner(gegnerliste, heldenliste)
    buffsDebuffsEntfernen(heldenliste)
    toteSchwaermerEntfernen(gegnerliste)
}

fun attackeGegner(gegnerliste: MutableList<Gegner>, heldenliste: List<Held>) {
    val zufallszahl: Int = (1..5).random()
    var speicher: Gegner? = null

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
                    }
                }
                4 -> {
                    gegner.angreifen(heldenliste.random())
                }
                else -> {
                    gegner.heilen()
                }
            }
        } else if (gegner is Schwaermer) {
            println("Der Schwärmer versucht anzugreifen, es geht daneben.")
        }
    }

    if (speicher != null) {
        gegnerliste.add(speicher)
    }
}

fun toteSchwaermerEntfernen(gegnerliste: MutableList<Gegner>) {
    var speicher: MutableList<Gegner> = mutableListOf()
    for (gegner in gegnerliste) {
        if (gegner.lp < 0 && gegner is Schwaermer) {
            gegner.lp = 0
            speicher.add(gegner)
        }
    }
    for (gegner in speicher) {
        gegnerliste.remove(gegner)
    }
}
