package jp.hotdrop.mapmarkertest.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.Marker
import jp.hotdrop.mapmarkertest.GlideApp
import jp.hotdrop.mapmarkertest.databinding.CustomInfoContentsBinding
import jp.hotdrop.mapmarkertest.model.MyClusterItem

class CustomInfoContentsAdapter constructor(
        private val context: Context,
        private val imageWidth: Int,
        private val imageHeight: Int
): CustomAdapter {

    private val thumbnails = HashMap<Marker, Bitmap>()
    private val targets = HashMap<Marker, Target<Bitmap>>()
    override var selectedItem: MyClusterItem? = null

    /**
     * 中身のコンテンツをカスタムする場合、getInfoContentsを実装してgetInfoWindowはnullを返す。
     */
    override fun getInfoContents(p0: Marker?): View? {
        val binding = CustomInfoContentsBinding.inflate(LayoutInflater.from(context))

        p0 ?: return null
        val place = selectedItem?.place ?: return null

        if (place.imageUrl.isNullOrEmpty()) {
            binding.title.text = place.title
            binding.memo.text = place.memo
            return binding.root
        }

        val image = thumbnails[p0]
        if (image == null) {
            // イメージが未取得の場合はGlide経由でイメージを取得してメモリに保持しレンダリングはしないのでnullを返す
            val target = targets[p0] ?: ClusterItemTarget(p0).apply { targets[p0] = this }
            GlideApp.with(context)
                    .asBitmap()
                    .load(place.imageUrl)
                    .dontAnimate()
                    .into(target)
            return null
        }

        binding.imageView.setImageBitmap(image)
        binding.title.text = place.title
        binding.memo.text = place.memo

        return binding.root
    }

    override fun getInfoWindow(p0: Marker?): View? = null

    inner class ClusterItemTarget constructor(
            private val marker: Marker
    ): SimpleTarget<Bitmap>(imageWidth, imageHeight) {
        override fun onLoadCleared(placeholder: Drawable?) {
            thumbnails.remove(marker)
        }
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            // リソースが取得できたら再度showInfoWindowを呼ぶ。getInfoContentsが呼ばれるので今度はimageがnullでなくなる。
            thumbnails[marker] = resource
            marker.showInfoWindow()
        }
    }
}