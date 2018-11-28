package truckpad.com.marvelheros.remote.model

import com.google.gson.annotations.SerializedName
data class MarvelModel(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Character>,
    val total: Int
)

data class Character(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

data class Url(
    val type: String,
    val url: String
)

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<ComicsItems>,
    val returned: Int
)

data class ComicsItems(
    val name: String,
    val resourceURI: String
)

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<StoriesItems>,
    val returned: Int
)

data class StoriesItems(
    val name: String,
    val resourceURI: String,
    val type: String
)

data class Thumbnail(
    val extension: String,
    val path: String
)

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<StoriesItems>,
    val returned: Int
)

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Int
)


class CharactersResponseModel (
    @SerializedName("results")
    val items : List<Characters>) {
}