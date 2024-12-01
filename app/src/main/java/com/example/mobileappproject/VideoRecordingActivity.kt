package com.example.mobileappproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoRecordingActivity : AppCompatActivity() {

    private val REQUEST_VIDEO_CAPTURE = 1
    private lateinit var videoView: VideoView
    private var videoUri: Uri? = null
    private val emergencyPhoneNumber = "+254716903198"  // Replace with the emergency WhatsApp number

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_recording)

        // Initialize the VideoView
        videoView = findViewById(R.id.videoView)

        // Start the video recording when the activity is created
        dispatchTakeVideoIntent()
    }

    // Start the video recording Intent
    private fun dispatchTakeVideoIntent() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)

        // Check if a camera app is available to handle the intent
        takeVideoIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
        } ?: run {
            Toast.makeText(this, "No camera app available", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle the result from the video capture Intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            // Get the URI of the captured video
            videoUri = data?.data

            // Show a message with the URI of the saved video
            videoUri?.let {
                Toast.makeText(this, "Video saved at: $it", Toast.LENGTH_LONG).show()

                // Set the URI to the VideoView and make it visible
                videoView.setVideoURI(it)
                videoView.visibility = android.view.View.VISIBLE
                videoView.start()  // Start playing the video

                // Send the video to the emergency number via WhatsApp
                shareVideoToWhatsApp(it)
            }
        } else {
            // Handle errors or if the user cancels the recording
            Toast.makeText(this, "Video capture failed or was canceled", Toast.LENGTH_SHORT).show()
        }
    }

    // Share the video to WhatsApp with a caption
    private fun shareVideoToWhatsApp(videoUri: Uri) {
        try {
            // Create a WhatsApp Intent
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "video/mp4"  // MIME type for video
                putExtra(Intent.EXTRA_STREAM, videoUri)  // Attach the video URI
                putExtra(Intent.EXTRA_TEXT, "Emergency: Please check the video attached for details.")  // Add caption
                putExtra(
                    "jid",
                    emergencyPhoneNumber.replace("+", "") + "@s.whatsapp.net" // WhatsApp-specific format
                )
                setPackage("com.whatsapp")  // Ensure it's sent to WhatsApp
            }

            // Start the WhatsApp activity
            startActivity(sendIntent)

            Toast.makeText(this, "Video sent via WhatsApp with caption.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send video via WhatsApp: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
