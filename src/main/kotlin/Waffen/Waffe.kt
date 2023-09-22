package Waffen

import Charakter.Helden.Held

open class Waffe(var name: String) {
    open fun anlegen(held: Held){
    }

    open fun ablegen(held: Held){
    }
}