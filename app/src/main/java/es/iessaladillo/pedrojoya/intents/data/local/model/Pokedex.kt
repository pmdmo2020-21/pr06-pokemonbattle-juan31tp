package es.iessaladillo.pedrojoya.intents.data.local.model

import es.iessaladillo.pedrojoya.intents.R

class Pokedex private constructor(){

    companion object {
        fun pokedex() : List<Pokemon> {
            return listOf(
                Pokemon(1, R.drawable.bulbasur, R.string.nameBulbasur, 35),
                Pokemon(2, R.drawable.giratina, R.string.nameGiratina, 165),
                Pokemon(3, R.drawable.cubone, R.string.nameCubone, 50),
                Pokemon(4, R.drawable.gyarados, R.string.nameGyarados, 130),
                Pokemon(5, R.drawable.feebas, R.string.nameFeebas, 15),
                Pokemon(6, R.drawable.pikachu, R.string.namePikachu, 100),
            )
        }
    }
}