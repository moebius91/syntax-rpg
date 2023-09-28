# Abschlußprojekt RPG von Jan-Nikolas Othersen
Abschlußprojekt beim Syntax Institut - Modul "Grundlagen der Programmierung". 
## Kurze Beschreibung

Dieses Projekt ist ein einfaches, rundenbasiertes RPG-Spiel, entwickelt als Abschlussprojekt für das Modul "Grundlagen der Programmierung" am Syntax Institut. Spieler können aus drei verschiedenen Heldenklassen wählen und gegen eine Vielzahl von Gegnern antreten, inklusive eines Endgegners.

## Features

- 3 Heldenklassen: Krieger, Druide und Magier
- Verschiedene Gegnerklassen wie Orks, Goblins und Drachen
- Unterstützung für Buffs und Debuffs
- Verschiedene Waffen und Items
- Rundenbasierte Kämpfe

## Installation und Abhängigkeiten

Das Projekt wurde in Kotlin geschrieben und in IntelliJ entwickelt. Öffnen Sie das Projekt in IntelliJ und führen Sie die `main.kt` aus, um das Spiel zu starten.

## Dateistruktur

```
.
├── README.md
└── src
    ├── main
    │   ├── kotlin
    │   │   ├── charakter
    │   │   │   ├── Charakter.kt
    │   │   │   ├── gegner
    │   │   │   │   ├── Drache.kt
    │   │   │   │   ├── Gegner.kt
    │   │   │   │   ├── Goblin.kt
    │   │   │   │   ├── Ork.kt
    │   │   │   │   └── Schwaermer.kt
    │   │   │   └── helden
    │   │   │       ├── Druide.kt
    │   │   │       ├── Held.kt
    │   │   │       ├── Krieger.kt
    │   │   │       └── Magier.kt
    │   │   ├── items
    │   │   │   ├── Heiltrank.kt
    │   │   │   ├── Item.kt
    │   │   │   └── Vitamine.kt
    │   │   ├── level
    │   │   │   ├── Level.kt
    │   │   │   └── Verlies.kt
    │   │   ├── main.kt
    │   │   ├── statuseffekte
    │   │   │   ├── buffs
    │   │   │   │   ├── Buff.kt
    │   │   │   │   └── SchildGebrauch.kt
    │   │   │   └── debuffs
    │   │   │       ├── Debuff.kt
    │   │   │       ├── FluchDesDrachen.kt
    │   │   │       ├── FluchDesMagiers.kt
    │   │   │       └── SchildVerlieren.kt
    │   │   ├── utils
    │   │   │   ├── kampfmechaniken.kt
    │   │   │   ├── spielmechaniken.kt
    │   │   │   ├── statuseffekte.kt
    │   │   │   ├── textausgabe.kt
    │   │   │   └── texteingabe.kt
    │   │   └── waffen
    │   │       ├── Axt.kt
    │   │       ├── Dolch.kt
    │   │       ├── Schwert.kt
    │   │       ├── Stab.kt
    │   │       ├── Streitkolben.kt
    │   │       ├── Waffe.kt
    │   │       └── Zauberstab.kt
    │   └── resources
    └── test
        ├── kotlin
        │   └── test.kt
        └── resources

```

## Verwendung

Nach dem Start des Spiels können Sie aus drei Heldenklassen auswählen. Der Kampf verläuft rundenbasiert. Zuerst führen die Helden ihre Aktionen aus, gefolgt von den Aktionen der Gegner.

## Autoren und Mitwirkende

- Jan-Nikolas Othersen
- README.md, KDOCs und eine Funktion (ist kommentiert) mit der Hilfe von ChatGPT