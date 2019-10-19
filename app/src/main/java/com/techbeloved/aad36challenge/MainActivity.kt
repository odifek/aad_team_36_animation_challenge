package com.techbeloved.aad36challenge

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.scene_default.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TriviaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        viewModel = ViewModelProviders.of(this)[TriviaViewModel::class.java]

        viewModel.triviaState.observe(this, Observer { triviaState ->
            Timber.i("New State: $triviaState")
            when (triviaState) {
                TriviaState.Default -> {
                    val defaultScene =
                        Scene.getSceneForLayout(sceneRoot, R.layout.scene_default, this)

                    defaultScene.setEnterAction {
                        findViewById<Button>(R.id.button_play).setOnClickListener {
                            if (editText_player_name.text.isNullOrBlank()) {
                                editText_player_name.error = "Please enter name"
                            } else {
                                val playerName = editText_player_name.text.toString()
                                viewModel.startNewGame(playerName)
                            }
                        }
                    }
                    // TODO: add some transition animation
                    TransitionManager.go(defaultScene)

                }
                is TriviaState.Play -> {
                    val question = triviaState.question
                    val playView = layoutInflater.inflate(R.layout.scene_play, sceneRoot, false)
                    val playScene = Scene(sceneRoot, playView)
                    // Transition to scene

                    playScene.setEnterAction {
                        findViewById<TextView>(R.id.textView_play_question).text = question.question
                        findViewById<TextView>(R.id.textView_play_question_type).text =
                            question.category
                        findViewById<TextView>(R.id.textView_play_counter).text =
                            getString(
                                R.string.question_counter,
                                triviaState.index,
                                triviaState.total
                            )

                        // Remove previous views that might still be visible in the linear layout
                        val choicesLayout =
                            findViewById<LinearLayout>(R.id.linearLayout_play_options)
                        choicesLayout.removeAllViews()
                        question.answers.forEach { answer ->
                            val button = layoutInflater.inflate(
                                R.layout.widget_button_choice,
                                choicesLayout,
                                false
                            ) as MaterialButton
                            button.text = answer
                            button.setTag(R.id.answer_choice, answer)
                            button.setOnClickListener { viewModel.selectedAnswer(answer) }

                            choicesLayout.addView(button)
                        }
                    }
                    // TODO: Add some transition animation
                    TransitionManager.go(playScene)

                }
                is TriviaState.GameOn.Correct -> {
                    val correctView =
                        layoutInflater.inflate(R.layout.scene_correct, sceneRoot, false)
                    val correctScene = Scene(sceneRoot, correctView)
                    correctScene.setEnterAction {
                        val continueButton = findViewById<Button>(R.id.button_continue)
                        if (!triviaState.hasMore) {
                            continueButton.setText(R.string.see_summary)
                        }
                        continueButton.setOnClickListener {
                            viewModel.next()
                        }
                    }
                    // TODO: Add some transition animation
                    TransitionManager.go(correctScene)

                }
                is TriviaState.GameOn.Incorrect -> {
                    val incorrectView =
                        layoutInflater.inflate(R.layout.scene_incorrect, sceneRoot, false)
                    val incorrectScene = Scene(sceneRoot, incorrectView)
                    incorrectScene.setEnterAction {
                        val continueButton = findViewById<Button>(R.id.button_continue)
                        if (!triviaState.hasMore) {
                            continueButton.setText(R.string.see_summary)
                        }
                        continueButton.setOnClickListener {
                            viewModel.next()
                        }
                    }
                    // TODO: Add some transition animation

                    TransitionManager.go(incorrectScene)

                }
                is TriviaState.GameOn.Timeout -> {
                    // TODO: Create time out logic and enable timer
                }
                is TriviaState.Summary -> {
                    // TODO: create summary screen and navigate to it
                }
            }
        })
    }
}
