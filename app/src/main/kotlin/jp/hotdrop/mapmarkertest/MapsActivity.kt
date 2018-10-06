package jp.hotdrop.mapmarkertest

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import jp.hotdrop.mapmarkertest.adapter.Type
import jp.hotdrop.mapmarkertest.databinding.ActivityMapsBinding
import jp.hotdrop.mapmarkertest.repository.PlaceRepository

class MapsActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap
    private var clusterMarkers: MapClusterMarkers? = null
    private val repository = PlaceRepository()
    private val adapterType by lazy { intent.getSerializableExtra(EXTRA_TAG) as Type }

    companion object {
        private val EXTRA_TAG = MapsActivity::class.java.simpleName
        private const val defaultZoomLevel = 15f
        // 東京駅の緯度軽度を初期位置
        private val DEFAULT_LOCATION = LatLng(35.681167, 139.767052)
        fun start(context: Context, type: Type) =
                context.startActivity(Intent(context, MapsActivity::class.java).apply {
                    putExtra(EXTRA_TAG, type)
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)

        initView()
    }

    private fun initView() {
        SupportMapFragment.newInstance().let { supportMapFragment ->
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.map_content_view, supportMapFragment)
                    .commit()
            supportMapFragment.getMapAsync { googleMap ->
                mMap = googleMap
                // 室内空間の構造図表示はfalseにする
                googleMap.isIndoorEnabled = false

                // カメラ初期位置。これがないとアフリカの左下あたりの海が初期位置になってしまう
                moveCamera(DEFAULT_LOCATION, defaultZoomLevel)

                // 地図表示するデータ読み込む
                val places = repository.findAll(this)

                clusterMarkers = MapClusterMarkers(this, mMap, adapterType) {
                    Toast.makeText(this, "マーカーをタップしました。", Toast.LENGTH_SHORT).show()
                }.apply {
                    this.showOnMap(places)
                }
            }
        }
    }

    private fun moveCamera(location: LatLng, zoomLevel: Float) {
        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder()
                                .target(location)
                                .zoom(zoomLevel)
                                .build()
                )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        clusterMarkers?.clear()
    }
}
