package jp.hotdrop.mapmarkertest.model

import com.google.android.gms.maps.model.LatLng

data class Place (
        val title: String,
        val imageUrl: String?,
        val memo: String,
        private val latitude: Double,
        private val longitude: Double
) {
    fun createLatLng() = LatLng(latitude, longitude)
}