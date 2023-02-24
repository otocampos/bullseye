package com.company.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.company.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sliderValue = 0
    private var targetValue = randomValue()
    private var totalScore = 0
    private var totalRound = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        startNewGame()
        binding.hitMeButton.setOnClickListener {
            Log.i("Button Click Event", "You clicker the Hit me Button")
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()

        }
        binding.startOverButton?.setOnClickListener{

            startNewGame()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }


        })
    }

    private fun differenceAmount() = abs(targetValue - sliderValue)
    private fun randomValue() = Random.nextInt(1, 100)

    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val points = maxScore - differenceAmount()
        val editedPoint: Int = when {
            points == 100 -> points + 100
            points == 99 -> points + 50

            else -> {
                points
            }
        }

        return editedPoint
    }

    private fun startNewGame() {
        totalScore = 0
        totalRound = 1
        sliderValue = 50
        targetValue = randomValue()

        binding.gameRoundTextView?.text = totalRound.toString()
        binding.gameScoreTextView?.text = totalScore.toString()
        binding.idTargetText.text = targetValue.toString()
        binding.seekBar.progress = sliderValue
    }

    private fun showResult() {
        val dialogTitle = getString(R.string.result_dialog_title)
        //  val dialogMessage = "${getString(R.string.result_dialog_message)} $sliderValue"
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())

        val builder = AlertDialog.Builder(this)
        builder.setTitle(alertTitle())
        builder.setMessage(dialogMessage)
        builder.setPositiveButton(R.string.confirm_button) { dialog, _ ->
            dialog.dismiss()
            manageRound()
            targetValue = randomValue()
        }
        builder.show()
    }

    private fun manageRound() {

        totalRound += 1;
        binding.gameRoundTextView?.text = totalRound.toString()

    }


    private fun alertTitle(): String {
        val difference = abs(targetValue - sliderValue)
        val title: String = when {
            difference == 0 -> {
                getString(R.string.alert_title_1)
            }
            difference < 5 -> {
                getString(R.string.alert_title_2)
            }
            difference <= 10 -> {
                getString(R.string.alert_title_3)
            }
            else -> {
                getString(R.string.alert_title_4)
            }
        }
        return title
    }
}