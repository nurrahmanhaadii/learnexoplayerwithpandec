package id.visionplus.vpplayer.listener

import android.net.Uri
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.analytics.AnalyticsListener.EventTime
import id.visionplus.vpplayer.model.PlayerConfig

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface PlayerAnalyticListener {
    /**
     * On play.
     * Interface untuk capture event on play player dari content ke module app
     *
     * @param playerConfig Konfigurasi Player
     */
    fun onPlay(playerConfig: PlayerConfig?)

    /**
     * On pause.
     * Interface untuk capture event on Pause player dari content ke module app
     *
     * @param playerConfig Konfigurasi Player
     * @param pos          posisi content (seekbar time) ketika pause
     */
    fun onPause(playerConfig: PlayerConfig?, pos: Long)

    /**
     * On stop.
     * Interface untuk capture event on stop player dari content ke module app
     *
     * @param playerConfig   Konfigurasi Player
     * @param isContentEnded the is content ended or not
     * @param pos            posisi berhenti nonton
     */
    fun onStop(
        playerConfig: PlayerConfig?,
        isContentEnded: Boolean,
        contentLength: Long,
        pos: Long
    )


    /**
     * On error.
     * Interface untuk capture event ketika player error playing dari player ke module app
     *
     * @param playerConfig Konfigurasi Player
     * @param errorMsg     pesan error dari player
     */
    fun onError(playerConfig: PlayerConfig?, errorMsg: String?)


    /**
     * On buffering.
     * Interface untuk capture event ketika player sedang buffering dari player ke module app
     *
     * @param playerConfig Konfigurasi Player
     * @param pos          posisi ketika buffer
     * @param isBuffering  state buffering
     */
    fun onBufferStatusChanged(
        playerConfig: PlayerConfig?,
        isBuffering: Boolean,
        pos: Long
    )


    /**
     * On Ads Playing
     * Interface untuk capture event ketika player memainkan url vast
     *
     * @param adsUri       Vast Url
     * @param adType       Ad Type ( preroll, midroll, postroll)
     * @param adIndex      Vast Url
     * @param adGroupIndex Vast Url
     * @param adDuration   Vast Url
     */
    fun onAdsPlaying(
        adsUri: Uri?,
        adType: String?,
        adIndex: Int,
        adGroupIndex: Int,
        adDuration: Long
    )

    /**
     * Interface saat memutar konten selajutnya
     *
     * @param playerConfig Content yang sedang diputar
     */
    fun onNextContent(playerConfig: PlayerConfig?)

    /**
     * Interface saat memutar konten sebelumnya
     *
     * @param playerConfig Content yang sedang diputar
     */
    fun onPreviousContent(playerConfig: PlayerConfig?)


    /**
     * interface that run every seconds to observe player common data
     *
     * @param playerConfig  player config content
     * @param contentLength panjang total content. ketika live akan 0
     * @param pos posisi user pada konten per detik
     */
    fun onPlayerPositionTicked(
        playerConfig: PlayerConfig?, contentLength: Long?, pos: Long?
    )

    /**
     * @param trackType tipe dari track yang berubah audio, video, text
     * @param format format metadata dari player
     * @param currentConfig
     */
    fun onDecoderInputFormatChanged(
        trackType: Int,
        format: Format?,
        eventTime: EventTime?,
        currentConfig: PlayerConfig?
    )

    /**
     * @param currentConfig
     * @param contentLength panjang total content. ketika live akan 0
     * @param pos posisi user pada konten per detik
     */
    fun onSeekPlayer(currentConfig: PlayerConfig?, contentLength: Long?, pos: Long?)
}