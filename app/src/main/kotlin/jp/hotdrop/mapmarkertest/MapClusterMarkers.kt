package jp.hotdrop.mapmarkertest

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import jp.hotdrop.mapmarkertest.adapter.AdapterFactory
import jp.hotdrop.mapmarkertest.adapter.Type
import jp.hotdrop.mapmarkertest.model.MyClusterItem
import jp.hotdrop.mapmarkertest.model.Place

class MapClusterMarkers constructor(
        private val context: Context,
        private var map: GoogleMap,
        adapterType: Type,
        tapEvent: ((place: Place) -> Unit)? = null
) {

    private var clusterManager: ClusterManager<MyClusterItem>? = null
    private val adapter = AdapterFactory.create(
            context,
            adapterType,
            context.resources.getDimension(R.dimen.image_width).toInt(),
            context.resources.getDimension(R.dimen.image_height).toInt()
    )

    init {
        initClusterManager(map, tapEvent)
    }

    private fun initClusterManager(newMap: GoogleMap, tapEvent: ((place: Place) -> Unit)?) {
        map = newMap
        clusterManager = ClusterManager(context, map)
        clusterManager?.let { manager ->
            manager.setOnClusterItemInfoWindowClickListener { item ->
                item.place?.let { place ->
                    tapEvent?.let { event -> event(place) }
                }
            }

            manager.markerCollection.setOnInfoWindowAdapter(adapter)
            // Map上に設置するMarkerのInfoWindowを作るため、タップしたplace(ClusterItemのフィールドで持っている)の情報が必要。
            // でもInfoWindowAdapterのoverrideメソッドたちは引数がMarkerなのでplaceを取ることができない。
            // そのため、仕方ないのでタップした時にclusterItemをadapterに渡しています。
            manager.setOnClusterItemClickListener { item ->
                adapter.selectedItem = item
                false
            }
            map.setInfoWindowAdapter(manager.markerManager)
            map.setOnCameraIdleListener(manager)
            map.setOnMarkerClickListener(manager)
            map.setOnInfoWindowClickListener(manager)
        }
    }

    fun showOnMap(places: List<Place>) {
        places.forEach { setMarker(it) }
        clusterManager?.cluster()
    }

    fun clear() {
        clusterManager?.clearItems()
        map.clear()
    }

    private fun setMarker(place: Place) {
        clusterManager?.addItem(
                MyClusterItem(location = place.createLatLng(), title = place.title, place = place)
        )
    }
}

