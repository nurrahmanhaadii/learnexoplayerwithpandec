package id.visionplus.vpplayer.utils.drm

import com.google.android.exoplayer2.drm.*
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * Created by nurrahmanhaadii on 29,May,2020
 */
object DrmUtils {

    private val drmScheme = "widevine"

    fun createDrmSession(userAgent: String, drmUrl: String?): DrmSessionManager<ExoMediaCrypto> {
        val drmSessionManager: DrmSessionManager<ExoMediaCrypto>?
        drmSessionManager = if (drmUrl == null) {
            DrmSessionManager.getDummyDrmSessionManager()
        } else {
            val mediaDrmCallback: MediaDrmCallback =
                createMediaDrmCallback(userAgent, drmUrl)
            DefaultDrmSessionManager.Builder()
                .setUuidAndExoMediaDrmProvider(
                    Util.getDrmUuid(drmScheme)!!,
                    FrameworkMediaDrm.DEFAULT_PROVIDER
                )
                .setMultiSession(false)
                .build(mediaDrmCallback)
        }
        return drmSessionManager
    }

    private fun createMediaDrmCallback(userAgent: String, drmUrl: String): HttpMediaDrmCallback {
        val licenseDataSourceFactory =
            DefaultHttpDataSourceFactory(userAgent)

        return HttpMediaDrmCallback(drmUrl, licenseDataSourceFactory)
    }
}