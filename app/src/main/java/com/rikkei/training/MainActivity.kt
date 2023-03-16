package com.rikkei.training

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.VideoView
import com.rikkei.training.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private lateinit var binding: ActivityMainBinding





class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addApp()
        binding.btnA.setOnClickListener{
            startCamera()
        }

    }

    private fun addApp() {
        val imageView: ImageView = findViewById(R.id.im_1)
        val videoView: VideoView = findViewById(R.id.vv_1)
        val uri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri
        if (uri != null) {
            if (uri.toString().contains("image")) {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                if (inputStream != null) {
                    val file = File(getExternalFilesDir(null), "shared_image.jpg")
                    val outputStream = FileOutputStream(file)
                    inputStream.copyTo(outputStream)
                    outputStream.close()
                    inputStream.close()
                    val bitmap: Bitmap = BitmapFactory.decodeFile(file.path)
                    imageView.setImageBitmap(bitmap)
                }
            } else if (uri.toString().contains("video")) {
                videoView.setVideoURI(uri)
                videoView.start()
            }
        }
    }

    private fun startCamera() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10)
        startActivity(takeVideoIntent)
    }


}