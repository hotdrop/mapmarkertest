package jp.hotdrop.mapmarkertest.repository

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.hotdrop.mapmarkertest.R
import jp.hotdrop.mapmarkertest.model.Place

class PlaceRepository {

    fun findAll(context: Context) = loadData(context)

    /**
     * ここで読み込むplacedata.jsonは別途用意してください。形式はREADMEを参照。
     */
    private fun loadData(context: Context): List<Place> {
        val rawJson = context.resources.openRawResource(R.raw.placedata).bufferedReader().readText()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, Place::class.java)
        return moshi.adapter<List<Place>>(type).fromJson(rawJson)!!
    }
}