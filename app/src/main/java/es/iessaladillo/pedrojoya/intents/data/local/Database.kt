package es.iessaladillo.pedrojoya.intents.data.local

import es.iessaladillo.pedrojoya.intents.data.local.model.Pokedex
import kotlin.random.Random
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon

// TODO: Haz que Database implemente DataSource
object Database {

    val pokedex: List<Pokemon> = Pokedex.pokedex()
    val rnd: Random = Random.Default

    fun getRndPokemon(): Pokemon {
        return pokedex[rnd.nextInt(6)]
    }

    fun getMyPokedex(): List<Pokemon>{
        return pokedex
    }

    fun getById(id: Long): Pokemon{
        val thisPoke = pokedex.filter { thisPoke -> thisPoke.id == id }

        if (thisPoke.isEmpty()){
            throw IllegalArgumentException("You are looking for a not registered pokemon :(")
        }

        return pokedex[0]
    }

}