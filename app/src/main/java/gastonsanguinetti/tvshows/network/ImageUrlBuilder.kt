package gastonsanguinetti.tvshows.network

import gastonsanguinetti.tvshows.common.repository.ConfigurationRepository

class ImageUrlBuilder {

    fun build(imageRelativePath :String) :String {
        ConfigurationRepository().getConfiguration()?.let {
            return "${it.images.baseUrl}$DEFAULT_IMAGE_SIZE$imageRelativePath"
        }
        return String()
    }

    companion object {
        private const val DEFAULT_IMAGE_SIZE = "w300"
    }
}