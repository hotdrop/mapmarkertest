package jp.hotdrop.mapmarkertest.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MyClusterItem constructor(
        private val location: LatLng,
        private val title: String,
        private val snippet: String? = null,
        val place: Place? = null
): ClusterItem {
    override fun getPosition(): LatLng = location
    override fun getTitle(): String = title
    override fun getSnippet(): String? = snippet
}