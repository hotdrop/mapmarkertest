package hotdrop.jp.mapmarkertest

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hotdrop.jp.mapmarkertest.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.normalContent.setOnClickListener {
            MapsActivity.start(this)
        }

        binding.customWindow.setOnClickListener {
            MapsActivity.start(this)
        }

        binding.customWindowUseImage.setOnClickListener {
            MapsActivity.start(this)
        }
    }
}