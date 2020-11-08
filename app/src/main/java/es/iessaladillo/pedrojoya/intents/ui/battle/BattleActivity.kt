package es.iessaladillo.pedrojoya.intents.ui.battle

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.BattleActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.selection.SelectionActivity
import es.iessaladillo.pedrojoya.intents.ui.winner.WinnerActivity

class BattleActivity : AppCompatActivity() {

    companion object {
        const val EXTRAPOKE: String = "EXTRAPOKE"

        fun newIntent(context: Context, pokemonId: Long): Intent {
            return Intent(context, BattleActivity::class.java).putExtra(EXTRAPOKE, pokemonId)
        }
    }

    private lateinit var binding: BattleActivityBinding
    private val pokedex: LongArray = longArrayOf(0,0)
    private lateinit var topContender: ActivityResultLauncher<Intent>
    private lateinit var botContender: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BattleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFields()
        setListeners()

    }

    private fun setFields() {
        topContender = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (checkResult(result.resultCode, result.data)) { sendPoke(result.data, binding.topPokemonImg, binding.topPokemonName,0)}}
        botContender = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (checkResult(result.resultCode, result.data)) { sendPoke(result.data, binding.botPokemonImg, binding.botPokemonName,1)}}
    }

    private fun checkResult(resultCode: Int, data: Intent?): Boolean {
        return resultCode == RESULT_OK && data != null
    }

    private fun sendPoke(data: Intent?, pokeImg: ImageView, pokeName: TextView, i: Int) {
        if (data == null) {
            throw IllegalArgumentException("La información recibida no puede ser nula")
        }

        pokedex[i] = data.getLongExtra(EXTRAPOKE, 0)
            setPokemon(pokeImg, pokeName, pokedex[i]
        )
    }

    private fun setPokemon(pokemonImg: ImageView, pokemonName: TextView, id: Long) {
        val poke: Pokemon = Database.getById(id)
        pokemonImg.setImageDrawable(getDrawable(poke.drawable))
        pokemonName.text = getText(poke.name)
    }

    private fun setListeners() {
        setStartContenders()
        binding.topContenderPack.setOnClickListener{
            goToTeamScreen(pokedex[0], topContender)
        }
        binding.botContenderPack.setOnClickListener{
            goToTeamScreen(pokedex[1], botContender)
        }
        binding.battleButton.setOnClickListener {
            val winnerId: Long = letsBattle()
            goToWinnerScreen(winnerId)
        }
    }

    // -----------------
    // METHODS TO CHANGE THE CONTENDERS
    // -----------------
    private fun goToTeamScreen(pokemonId: Long, contender: ActivityResultLauncher<Intent>) {
        val intentSelection: Intent = SelectionActivity.newIntent(this, pokemonId)
        contender.launch(intentSelection)
    }

    // -----------------
    // METHODS THAT SETS THE INITIAL CONTENDERS
    // -----------------
    private fun setStartContenders() {
        setRndContender(binding.topPokemonImg, binding.topPokemonName, binding.topContenderPack)
        setRndContender(binding.botPokemonImg, binding.botPokemonName, binding.botContenderPack)
    }

    private fun setRndContender(pokemonImg: ImageView, pokemonName: TextView, contenderPack: LinearLayout) {
        val pokemon: Pokemon = Database.getRndPokemon()
        pokemonImg.setImageDrawable(getDrawable(pokemon.drawable))
        pokemonName.text = getText(pokemon.name)
    }
    // -----------------



    // -----------------
    // METHODS THAT DEFINE THE WINNER AND SHOWS
    // -----------------
    private fun goToWinnerScreen(winnerId: Long) {
        val intentWinner = WinnerActivity.newIntent(this, winnerId)
    }

    private fun letsBattle(): Long {
        val poke1: Pokemon = Database.getById(pokedex[0])
        val poke2: Pokemon = Database.getById(pokedex[1])

        if(poke1.power > poke2.power){
            return poke1.id
        } else {
            return poke2.id
        }
    }

    // -----------------


}