package id.haadii.learn.exoplayer.manager

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import id.haadii.learn.exoplayer.PlaybackStateListener
import id.visionplus.vpplayer.utils.drm.DrmUtils
import id.visionplus.vpplayer.utils.mediasource.MediaSourceCreator
import kotlin.math.max

/**
 * Created by nurrahmanhaadii on 26,May,2020
 */

class PlayerManager(
    private val context: Context,
    private val playerView: PlayerView
) : PlaybackPreparer, Player.EventListener {

    private var trackSelector: DefaultTrackSelector? = null
    private var player: SimpleExoPlayer? = null

    private var playWhenReady: Boolean = true
    private var startAutoPlay: Boolean? = false
    private var startWindow: Int? = 0
    private var startPosition: Long? = 0

    private var playbackStateListener: PlaybackStateListener
    private var userAgent: String
    private var dataSourceFactory: DataSource.Factory
    private lateinit var mediaSource: MediaSource

    init {
        clearStartPosition()
        userAgent = Util.getUserAgent(context, "VPPlayer")
        dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
        playbackStateListener = PlaybackStateListener(context)
    }

    private fun initializePlayer() {
        if (player == null) {
            val renderersFactory = DefaultRenderersFactory(context)
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
            val trackSelectionFactory = AdaptiveTrackSelection.Factory()
            trackSelector = DefaultTrackSelector(context, trackSelectionFactory)

            player = ExoPlayerFactory.newSimpleInstance(context, renderersFactory, trackSelector!!)
            playerView.player = player
        }
        createMediaSource()

        player?.playWhenReady = this.startAutoPlay!!
        player?.addListener(playbackStateListener)
        player?.prepare(mediaSource, false, false)
    }

    private fun createMediaSource() {
        val uriSimple =
            Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
        val uriAudio = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        val uri =
            Uri.parse("https://liveanevia.mncnow.id/vod/eds/boy-choir-apl-anv6-atv/_/sa_dash_vmx/boy-choir-apl-anv6-atv.mpd")
        val addTagUri =
            Uri.parse("https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpost&cmsid=496&vid=short_onecue&correlator=")
        val mediaSourceCreator = MediaSourceCreator(context, dataSourceFactory)
        val drmSession = DrmUtils.createDrmSession(
            userAgent,
            ""
        )

        val adsLoader = ImaAdsLoader.Builder(context)
            .buildForAdTag(addTagUri)
        adsLoader.setPlayer(player)

        val plainMediaSource = mediaSourceCreator.createSimpleMediaSource(uriSimple)
        val drmMediaSource = mediaSourceCreator.createDrmMediaSource(uri, drmSession)
        val audioMediaSource = mediaSourceCreator.createSimpleMediaSource(uriAudio)
        val mediaSourceAds =
            mediaSourceCreator.createAdsMediaSource(plainMediaSource, adsLoader, playerView)
        mediaSource = plainMediaSource
    }

    private fun releasePlayer() {
        if (player != null) {
            updateStartPosition()
            playWhenReady = player?.playWhenReady!!
            player?.removeListener(playbackStateListener)
            player?.release()
            player = null
            trackSelector = null
        }
    }

    private fun resetAll() {
        releasePlayer()
        clearStartPosition()
    }

    private fun updateStartPosition() {
        if (player != null) {
            startAutoPlay = player?.playWhenReady
            startWindow = player?.currentWindowIndex
            startPosition = player?.contentPosition?.let { max(0, it) }
        }
    }

    private fun clearStartPosition() {
        startAutoPlay = true
        startWindow = C.INDEX_UNSET
        startPosition = C.TIME_UNSET
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    fun onContextStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer()
            playerView.onResume()
        }
    }

    fun onContextResume() {
        if (Util.SDK_INT <= 23 || player == null) {
            hideSystemUi()
            initializePlayer()
            playerView.onResume()
        }
    }

    fun onContextPause() {
        if (Util.SDK_INT <= 23) {
            playerView.onPause()
            releasePlayer()
        }
    }

    fun onContextStop() {
        if (Util.SDK_INT > 23) {
            playerView.onPause()
            releasePlayer()
        }
    }

    fun onContextDestroy() {
        playerView.player = null
    }

    override fun preparePlayback() {
        player?.retry()
    }

}