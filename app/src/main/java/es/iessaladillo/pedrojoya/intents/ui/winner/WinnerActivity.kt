package es.iessaladillo.pedrojoya.intents.ui.winner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.intents.data.local.Database
import es.iessaladillo.pedrojoya.intents.data.local.model.Pokemon
import es.iessaladillo.pedrojoya.intents.databinding.WinnerActivityBinding
import es.iessaladillo.pedrojoya.intents.ui.battle.BattleActivity

class WinnerActivity : AppCompatActivity() {

    companion object {
        const val EXTRAWINNER: String = "EXTRAWINNER"

        fun newIntent(context: Context, pokemonId: Long): Intent {
            return Intent(context, WinnerActivity::class.java).putExtra(EXTRAWINNER, pokemonId)
        }
    }

    private lateinit var binding: WinnerActivityBinding
    private lateinit var poke: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WinnerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWinnerData()
        setScreen()
    }

    private fun getWinnerData() {
        poke = Database.getById(intent.getLongExtra(EXTRAWINNER, 0))
    }

    private fun setScreen() {
        binding.winnerImage.setImageDrawable(getDrawable(poke.drawable))
        binding.winnerName.text = getString (poke.name)
    }

}