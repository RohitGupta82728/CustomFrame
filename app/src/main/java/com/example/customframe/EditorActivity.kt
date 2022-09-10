package com.example.customframe

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.customframe.databinding.ActivityEditorBinding
import com.impulsive.zoomimageview.ZoomImageView
import java.lang.Exception

class EditorActivity : AppCompatActivity(),OnClick {

//    private lateinit var userImage:ZoomImageView
//    private lateinit var button: Button
 //   private lateinit var frameContainer:ImageView
 //   private lateinit var recyclerView: RecyclerView
  //  private lateinit var screenShot:RelativeLayout
    private lateinit var list: MutableList<FrameList>
    private lateinit var binding: ActivityEditorBinding

    private val draw= arrayOf(R.drawable.frame_0,R.drawable.frame_1,R.drawable.frame_2
        ,R.drawable.frame_3, R.drawable.frame_4,R.drawable.frame_5,R.drawable.frame_6
        ,R.drawable.frame_7,R.drawable.frame_8,R.drawable.frame_9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditorBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        //binding.userImage
       // userImage.findViewById(R.id.userImage)
        //button.findViewById<Button>(R.id.saveBtn)
       // frameContainer.findViewById<ImageView>(R.id.frameContainer)
        //recyclerView.findViewById<RecyclerView>(R.id.frameRecyclerView)

        val path = intent.getStringExtra("path")

        Glide.with(this).load(path).into(binding.userImage)
        list=ArrayList()

        initList()

        binding.frameRecyclerView.adapter=FrameAdapter(this,list,this)

        binding.saveBtn.setOnClickListener {
            store(getScreenShot(binding.screenshot))
        }
    }

    private fun initList() {
        for(j in draw){
            list.add(FrameList(j))
        }
    }

    override fun frameClick(position: Int) {
        Glide.with(this).load(list[position].drawable).into(binding.frameContainer)
    }

    private fun getScreenShot(view:View):Bitmap{

        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }
    private fun store(bitmap: Bitmap){
        var uri:Uri?=null

        if(SDK_INT>= Build.VERSION_CODES.R){
            uri=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }else{
            uri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValue = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME,"displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg")
            put(MediaStore.Images.Media.WIDTH,bitmap.width)
            put(MediaStore.Images.Media.HEIGHT,bitmap.height)
        }

        val u = contentResolver.insert(uri,contentValue)

        try {
            val outputStream = contentResolver.openOutputStream(u!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
            Toast.makeText(this,"Image Saved",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            Toast.makeText(this, "Image Not Saved", Toast.LENGTH_SHORT).show()
        }
    }
}