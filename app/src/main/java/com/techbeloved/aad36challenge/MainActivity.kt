package com.techbeloved.aad36challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.scene_correct.*
import kotlinx.android.synthetic.main.scene_correct.button_continue
import kotlinx.android.synthetic.main.scene_default.*
import kotlinx.android.synthetic.main.scene_incorrect.*
import kotlinx.android.synthetic.main.scene_play.*
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
                    TransitionManager.go(defaultScene)

                    if (!button_play.hasOnClickListeners()) {
                        button_play.setOnClickListener {
                            if (editText_player_name.text.isNullOrBlank()) {
                                editText_player_name.error = "Please enter name"
                            } else {
                                val playerName = editText_player_name.text.toString()
                                viewModel.startNewGame(playerName)
                            }
                        }
                    }
                }
                is TriviaState.Play -> {
                    val question = triviaState.question
                    val playScene = Scene.getSceneForLayout(sceneRoot, R.layout.scene_play, this)
                    // Transition to scene
                    TransitionManager.go(playScene)
                    textView_play_question.text = question.question
                    textView_play_question_type.text = question.category
                    textView_play_counter.text =
                        getString(R.string.question_counter, triviaState.index, triviaState.total)

                    // Remove previous views that might still be visible in the linear layout
                    linearLayout_play_options.removeAllViews()
                    question.answers.forEach { answer ->
                        val button = layoutInflater.inflate(
                            R.layout.widget_button_choice,
                            linearLayout_play_options,
                            false
                        ) as MaterialButton
                        button.text = answer
                        button.setTag(R.id.answer_choice, answer)
                        button.setOnClickListener { viewModel.selectedAnswer(answer) }

                        linearLayout_play_options.addView(button)
                    }
                }
                is TriviaState.GameOn.Correct -> {
                    val correctScene =
                        Scene.getSceneForLayout(sceneRoot, R.layout.scene_correct, this)
                    TransitionManager.go(correctScene)
                    if (!triviaState.hasMore) {
                        button_continue.setText(R.string.see_summary)
                    }
                    if (!button_continue.hasOnClickListeners()) {
                        button_continue.setOnClickListener {
                            viewModel.next()
                        }
                    }
                }
                is TriviaState.GameOn.Incorrect -> {
                    val incorrectScene =
                        Scene.getSceneForLayout(sceneRoot, R.layout.scene_incorrect, this)
                    TransitionManager.go(incorrectScene)
                    if (!triviaState.hasMore) {
                        button_continue.setText(R.string.see_summary)
                    }
                    if (!button_continue.hasOnClickListeners()) {
                        button_continue.setOnClickListener {
                            viewModel.next()
                        }
                    }
                }
                is TriviaState.GameOn.Timeout -> {
                    // TODO
                }
                is TriviaState.Summary -> {
                    // TODO
                }
            }
        })
    }
}
