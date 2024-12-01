package com.example.mobileappproject

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val backArrow = findViewById<ImageView>(R.id.backArrowIcon)
        backArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
        // Card 1 setup (gradient overlay)
        setupCardWithGradient(
            findViewById(R.id.cardImage1),
            findViewById(R.id.cardOverlay1),
            R.drawable.card, // Image for card 1
            intArrayOf(Color.parseColor("#A82AE3"), Color.parseColor("#ABABAB00")), // Gradient colors
            0.4f // Overlay opacity
        )

        // Card 2 setup (solid overlay color with rounded corners)
        val cardContainer2 = findViewById<View>(R.id.cardContainer2)
        setupCardWithSolidOverlay(
            findViewById(R.id.cardImage2),
            findViewById(R.id.cardOverlay2),
            R.drawable.card1, // Image for card 2
            Color.argb((0.40f * 255).toInt(), 174, 195, 248), // Solid overlay color
            1.0f, // Full overlay opacity
            20f // Corner radius
        )
        cardContainer2.elevation = 39f // Set elevation for Card 2

        // Card 3 setup (solid overlay color with rounded corners)
        val cardContainer3 = findViewById<View>(R.id.cardContainer3)
        setupCardWithSolidOverlay(
            findViewById(R.id.cardImage3),
            findViewById(R.id.cardOverlay3),
            R.drawable.card2, // Image for card 3
            Color.argb((0.40f * 255).toInt(), 174, 195, 248), // Solid overlay color
            1.0f, // Full overlay opacity
            20f // Corner radius
        )
        cardContainer3.elevation = 39f // Set elevation for Card 3

        // Click listeners
        cardContainer2.setOnClickListener { openCameraForVideoRecording() }
        cardContainer3.setOnClickListener { openAudioRecording() }
    }

    // Open CameraActivity for Video Recording
    private fun openCameraForVideoRecording() {
        val intent = Intent(this, VideoRecordingActivity::class.java)
        startActivity(intent)
    }

    // Open AudioRecordingActivity to trigger Audio Recording
    private fun openAudioRecording() {
        val intent = Intent(this, AudioRecordingActivity::class.java)
        startActivity(intent)
    }

    // Set up Card with Gradient Overlay
    private fun setupCardWithGradient(
        imageView: ImageView,
        overlay: View,
        imageRes: Int,
        gradientColors: IntArray,
        overlayOpacity: Float
    ) {
        imageView.setImageResource(imageRes)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            gradientColors
        )
        gradientDrawable.cornerRadius = 20f
        overlay.background = gradientDrawable
        overlay.alpha = overlayOpacity
    }

    // Set up Card with Solid Overlay
    private fun setupCardWithSolidOverlay(
        imageView: ImageView,
        overlay: View,
        imageRes: Int,
        overlayColor: Int,
        overlayOpacity: Float,
        cornerRadius: Float
    ) {
        imageView.setImageResource(imageRes)

        val roundedOverlay = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setColor(overlayColor)
            this.cornerRadius = cornerRadius
        }
        overlay.background = roundedOverlay
        overlay.alpha = overlayOpacity
    }
}
