package com.tempestgf.threep

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.PathInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tempestgf.threep.ui.theme._3PTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        // Animación de salida premium (estilo Awwwards)
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Curva de aceleración suave (Cubic Bezier) para un efecto muy fluido
            val interpolator = PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f)
            
            // 1. Animación del icono: se reduce ligeramente y se desvanece
            val iconView = splashScreenView.iconView
            if (iconView != null) {
                val iconScaleX = ObjectAnimator.ofFloat(iconView, View.SCALE_X, 1f, 0.85f)
                val iconScaleY = ObjectAnimator.ofFloat(iconView, View.SCALE_Y, 1f, 0.85f)
                val iconAlpha = ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)
                
                listOf(iconScaleX, iconScaleY, iconAlpha).forEach {
                    it.duration = 600L
                    it.interpolator = interpolator
                    it.start()
                }
            }

            // 2. Animación del fondo: se desvanece y hace un sutil zoom in
            val splashAlpha = ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1f, 0f)
            val splashScaleX = ObjectAnimator.ofFloat(splashScreenView.view, View.SCALE_X, 1f, 1.05f)
            val splashScaleY = ObjectAnimator.ofFloat(splashScreenView.view, View.SCALE_Y, 1f, 1.05f)
            
            listOf(splashAlpha, splashScaleX, splashScaleY).forEach {
                it.duration = 800L
                it.interpolator = interpolator
                if (it == splashAlpha) {
                    it.addListener(object : android.animation.AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: android.animation.Animator) {
                            splashScreenView.remove()
                        }
                    })
                }
                it.start()
            }
        }

        enableEdgeToEdge()
        setContent {
            _3PTheme {
                com.tempestgf.threep.ui.navigation.AppNavigation()
            }
        }
    }
}