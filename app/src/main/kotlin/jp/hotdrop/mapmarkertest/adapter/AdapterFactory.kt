package jp.hotdrop.mapmarkertest.adapter

import android.content.Context
import java.io.Serializable

object AdapterFactory {
    fun create(context: Context, type: Type, imageWidth: Int, imageHeight: Int): CustomAdapter =
            when (type) {
                is Type.CustomContents -> CustomInfoContentsAdapter(context, imageWidth, imageHeight)
                is Type.CustomWindow -> CustomInfoWindowAdapter(context, imageWidth, imageHeight)
                is Type.CustomWindowWithArrow -> CustomInfoWindowArrowAdapter(context, imageWidth, imageHeight)
            }
}

sealed class Type: Serializable {
    class CustomContents: Type()
    class CustomWindow: Type()
    class CustomWindowWithArrow: Type()
}