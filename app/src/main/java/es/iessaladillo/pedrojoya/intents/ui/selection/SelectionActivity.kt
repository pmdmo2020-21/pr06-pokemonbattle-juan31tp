package es.iessaladillo.pedrojoya.intents.ui.selection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.SelectionActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.battle.BattleActivity

class SelectionActivity : AppCompatActivity() {

    companion object {
        const val SELECTEDPOKE: String = "SELECTEDPOKE"

        fun newIntent(context: Context, pokemonId: Long): Intent {
            return Intent(context, BattleActivity::class.java).putExtra(SELECTEDPOKE, pokemonId)
        }
    }

    private lateinit var binding: SelectionActivityBinding
    private lateinit var pokemon: Pokemon
    private lateinit var radioOptions: Array<RadioButton>
    private lateinit var images: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SelectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        takeData()
        setArrays()
        setListeners()

    }

    private fun setListeners() {

        for(i in radioOptions.indices){
            radioOptions[i].tag = Database.getById(i.toLong()+1)
        }

        for(i in images.indices){
            images[i].tag = radioOptions[i]
        }

        for (i in radioOptions.indices) {
            radioOptions[i].setOnClickListener { view: View -> checkSelectedRadio(view) }
        }

        for (i in images.indices) {
            images[i].setOnClickListener { view: View -> imgListener(view) }
        }
    }

    private fun imgListener(view: View) {
        val radio: RadioButton=view.tag as RadioButton
        radio.isChecked=true

        checkSelectedRadio(radio)
    }

    private fun checkSelectedRadio(view: View) {
        val selectedPokemon: RadioButton=view as RadioButton
        val pokemon: Pokemon = view.tag as Pokemon

        for (radioButton in radioOptions) {
            if (selectedPokemon != radioButton) {
                radioButton.isChecked = false
            }
        }
        this.pokemon=pokemon
    }

    private fun setArrays() {
        radioOptions = arrayOf(binding.optionBulbasur, binding.optionGiratina, binding.optionCubone, binding.optionGyarados, binding.optionFeebas, binding.optionPikachu)
        images = arrayOf(binding.selectBulbasur, binding.selectGiratina, binding.selectCubone, binding.selectGyarados, binding.selectFeebas, binding.selectPikachu)
    }

    private fun takeData() {
        val pokemon: Pokemon

        pokemon = getById(intent.getLongExtra(SELECTEDPOKE, 0))
        this.pokemon=pokemon
    }

    private fun getById(pokeId: Long): Pokemon {
        val pokemon: Pokemon = Database.getById(pokeId)
        return pokemon
    }

    override fun onBackPressed() {
        setResult()
        super.onBackPressed()
    }

    private fun setResult() {
        val intent: Intent = BattleActivity.newIntent(this, pokemon.id)
        setResult(RESULT_OK, intent)
    }

}