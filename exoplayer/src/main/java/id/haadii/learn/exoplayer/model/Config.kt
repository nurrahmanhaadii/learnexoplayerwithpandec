package id.visionplus.vpplayer.model

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

data class PlayerConfig(
    var playerData: PlayerData,
    var contentMetaData: ContentMetaData
)

data class ContentMetaData(
    var title: String?,
    var subtitle: String?,
    var desc: String?,
    var tags: List<String>?,
    var imgPosterUrl: String?,
    var customMetaData: HashMap<String, String>?
) {

    fun title(title: String?): ContentMetaData {
        this.title = title
        return this
    }

    fun subtitle(subtitle: String?): ContentMetaData {
        this.subtitle = subtitle
        return this
    }

    fun description(desc: String?): ContentMetaData {
        this.desc = desc
        return this
    }

    fun tags(tags: List<String>?): ContentMetaData {
        this.tags = tags
        return this
    }

    fun imgPosterUrl(imgPosterUrl: String?): ContentMetaData {
        this.imgPosterUrl = imgPosterUrl
        return this
    }

    fun customMetaData(customMetaData: HashMap<String, String>?): ContentMetaData {
        this.customMetaData = customMetaData
        return this
    }
}

data class PlayerData(
    var playUrl: String?,
    var drmUrl: String?,
    var adsUrl: String?,
    var startPosition: Long?
)