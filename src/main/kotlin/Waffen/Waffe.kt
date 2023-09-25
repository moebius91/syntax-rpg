package Waffen

import Charakter.Charakter
import Charakter.Helden.Held

open class Waffe(var name: String) {
    open fun anlegen(charakter: Charakter){
    }

    open fun ablegen(charakter: Charakter){
    }
}