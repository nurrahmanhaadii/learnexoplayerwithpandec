package id.haadii.learn.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ui.PlayerView
import id.haadii.learn.exoplayer.databinding.ActivityMainBinding
import id.haadii.learn.exoplayer.manager.PlayerManager

class MainActivity : AppCompatActivity() {

    private var playerView: PlayerView? = null
    private var playerManager: PlayerManager? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playerView = binding.videoView

        playerManager = playerView?.let { PlayerManager(this, it) }
    }

    override fun onStart() {
        super.onStart()
        playerManager?.onContextStart()
    }

    override fun onPause() {
        super.onPause()
        playerManager?.onContextPause()
    }

    override fun onResume() {
        super.onResume()
        playerManager?.onContextResume()
    }

    override fun onStop() {
        super.onStop()
        playerManager?.onContextStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerManager?.onContextDestroy()
    }
}