package jp.hotdrop.mapmarkertest.adapter

import com.google.android.gms.maps.GoogleMap
import jp.hotdrop.mapmarkertest.model.MyClusterItem

interface CustomAdapter: GoogleMap.InfoWindowAdapter {
    var selectedItem: MyClusterItem?
}