package hotdrop.jp.mapmarkertest.repository

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hotdrop.jp.mapmarkertest.R
import hotdrop.jp.mapmarkertest.model.Place

class PlaceRepository {

    // サンプルでは面倒なのでDagger2導入していないがほんとはcontextはDIすべき
    fun findAll(context: Context) = loadData(context)

    /**
     * ここで読み込むplacedata.jsonは別途用意してください。
     * こんな感じで作っています。
     *  [
     *     {"title": "テスト1", "imageUrl":"XXX", "memo":"東京駅の右のほうです。",  "latitude": 35.683040, "longitude": 139.770254},
     *     {"title": "テスト2", "imageUrl":"XXX", "memo":"美術館の近くです。東京駅の左のほう。",  "latitude": 35.678194, "longitude": 139.763930},
     *     {"title": "テスト3", "imageUrl":"XXX", "memo":"東京駅の上の方の道です。",  "latitude": 35.685327, "longitude": 139.766804}
     *  ]
     */
    private fun loadData(context: Context): List<Place> {
        val rawJson = context.resources.openRawResource(R.raw.placedata).bufferedReader().readText()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Place::class.java)
        return moshi.adapter<List<Place>>(type).fromJson(rawJson)!!
    }
}