package com.techbeloved.aad36challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TriviaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[TriviaViewModel::class.java]

        viewModel.triviaState.observe(this, Observer { triviaState ->
            when (triviaState) {
                TriviaState.Default -> {
                    val defaultScene =
                        Scene.getSceneForLayout(sceneRoot, R.layout.scene_default, this)
                    TransitionManager.go(defaultScene)
                }
                is TriviaState.Play -> TODO()
                is TriviaState.Correct -> TODO()
                is TriviaState.Incorrect -> TODO()
                is TriviaState.Timeout -> TODO()
                is TriviaState.Summary -> TODO()
            }
        })
    }
}
