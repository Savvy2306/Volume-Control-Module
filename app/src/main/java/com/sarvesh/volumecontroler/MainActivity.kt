package com.sarvesh.volumecontroler

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sarvesh.volumecontroler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the saved volume level from SharedPreferences
        //val savedVolume = sharedPreferences.getInt(PREF_KEY_VOLUME, 50) // Default to 50 if not set
        // binding.tvVolumeLevel.text = "$savedVolume"

        setupSeekBar(50)
        initializeMediaPlayer()
    }

    private fun setupSeekBar(savedVolume: Int) {
        binding.seekBar.max = 100
        binding.seekBar.progress = savedVolume

        // Set the initial volume level
        setVolume(savedVolume)

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Set the volume level based on the seekbar progress
                setVolume(progress)
                binding.tvVolumeLevel.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Handle seekbar touch start
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Save the current volume level in preferences when the user stops dragging
                saveVolumeLevel(binding.seekBar.progress)
            }
        })
    }

    private fun setVolume(volumeLevel: Int) {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val newVolume = (maxVolume * (volumeLevel / 100f)).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
    }

    private fun saveVolumeLevel(volumeLevel: Int) {
        //sharedPreferences.edit().putInt(PREF_KEY_VOLUME, volumeLevel).apply()
    }

    private fun initializeMediaPlayer() {
        // Initialize MediaPlayer with the audio file in res/raw/test_audio.mp3
        mediaPlayer = MediaPlayer.create(this, R.raw.test_audio)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

}