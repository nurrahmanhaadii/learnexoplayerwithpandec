package id.visionplus.vpplayer.utils.mediasource

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.ads.AdsLoader
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.util.Util

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class MediaSourceCreator(
    private val context: Context,
    private val dataSourceFactory: DataSource.Factory
) {

    fun createSimpleMediaSource(uri: Uri): MediaSource {
        @C.ContentType val type: Int =
            Util.inferContentType(uri)

        return when (type) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            else -> throw IllegalStateException("Unsupported type: $type")
        }
    }

    fun createDrmMediaSource(uri: Uri, drmSessionManager: DrmSessionManager<*>): MediaSource {
        @C.ContentType val type: Int =
            Util.inferContentType(uri)

        return when (type) {
            C.TYPE_DASH -> DashMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_SS -> SsMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(uri)
            else -> throw java.lang.IllegalStateException("Unsupported type: $type")
        }
    }

    fun createAdsMediaSource(
        mediaSource: MediaSource,
        adsLoader: AdsLoader,
        playerView: PlayerView
    ): MediaSource {

        val adsMediaSourceFactory = object : MediaSourceFactory {
            override fun createMediaSource(uri: Uri): MediaSource {
                return createSimpleMediaSource(uri)
            }

            override fun getSupportedTypes(): IntArray {
                return intArrayOf(C.TYPE_DASH, C.TYPE_SS, C.TYPE_HLS, C.TYPE_OTHER)
            }

            override fun setDrmSessionManager(drmSessionManager: DrmSessionManager<*>?): MediaSourceFactory {
                return this
            }
        }

        return AdsMediaSource(mediaSource, adsMediaSourceFactory, adsLoader, playerView)
    }
}