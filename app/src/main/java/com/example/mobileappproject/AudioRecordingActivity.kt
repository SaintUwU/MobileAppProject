package com.example.mobileappproject

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

class AudioRecordingActivity : AppCompatActivity() {

    private val REQUEST_CODE_RECORD_AUDIO = 1
    private lateinit var mediaRecorder: MediaRecorder
    private var outputFileUri: Uri? = null
    private lateinit var recordingIndicator: View
    private lateinit var pulseAnimator: ObjectAnimator
    private var isRecording = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_recording)

        recordingIndicator = findViewById(R.id.recordingIndicator)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            initializeRecording()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_RECORD_AUDIO
            )
        }

        // Stop button listener
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            stopRecordingAndSendWhatsApp()
        }

        // Back button listener
        findViewById<Button>(R.id.backButton).setOnClickListener {
            onBackPressed() // This will trigger the back navigation
        }
    }

    private fun initializeRecording() {
        val fileName = "audio_recording_${System.currentTimeMillis()}.m4a"
        val musicDirectory = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val audioFile = File(musicDirectory, fileName)

        outputFileUri = FileProvider.getUriForFile(this, "$packageName.provider", audioFile)

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile.absolutePath)
        }

        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording = true
            startRecordingAnimation()
            Toast.makeText(this, "Recording started: $fileName", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            isRecording = false
            Toast.makeText(this, "Failed to start recording: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun startRecordingAnimation() {
        pulseAnimator = ObjectAnimator.ofFloat(recordingIndicator, "scaleX", 1f, 1.5f, 1f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }
        ObjectAnimator.ofFloat(recordingIndicator, "scaleY", 1f, 1.5f, 1f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }.start()
        pulseAnimator.start()
    }

    private fun stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder.stop()
                mediaRecorder.reset()
                mediaRecorder.release()

                pulseAnimator.cancel()
                recordingIndicator.scaleX = 1f
                recordingIndicator.scaleY = 1f

                Toast.makeText(this, "Recording stopped.", Toast.LENGTH_SHORT).show()
                isRecording = false
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to stop recording: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Recording has not started yet.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecordingAndSendWhatsApp() {
        stopRecording()
        sendAudioToWhatsApp()
    }

    private fun sendAudioToWhatsApp() {
        if (outputFileUri == null) {
            Toast.makeText(this, "No audio file to send.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/m4a"
                putExtra(Intent.EXTRA_STREAM, outputFileUri)
                putExtra(Intent.EXTRA_TEXT, "Emergency: Please listen to this audio recording immediately.")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage("com.whatsapp")
            }

            startActivity(Intent.createChooser(shareIntent, "Send via WhatsApp"))
            Toast.makeText(this, "Audio sent to WhatsApp.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send audio: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_RECORD_AUDIO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeRecording()
            } else {
                Toast.makeText(this, "Audio recording permission is required.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (::mediaRecorder.isInitialized && isRecording) stopRecording()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaRecorder.isInitialized) mediaRecorder.release()
    }
}
