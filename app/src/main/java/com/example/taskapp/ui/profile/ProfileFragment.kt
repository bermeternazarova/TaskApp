package com.example.taskapp.ui.profile

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.taskapp.R
import com.example.taskapp.databinding.FragmentProfileBinding
import java.util.jar.Manifest
import android.widget.Toast.makeText as makeText1

class ProfileFragment : Fragment() {

    private lateinit var contentResolver: ContentResolver
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    var picked_image :Uri?=null
    var picked_bitmap :Bitmap?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return  binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     binding.circleImage.setOnClickListener {
         if (ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
             !=PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
         }else{
             val galeryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             startActivityForResult(galeryIntent,2)
//            registerForActivityResult(galeryIntent,2)
         }}}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            val galeryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeryIntent,2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode ==2 && resultCode==Activity.RESULT_OK && data != null){
            picked_image =data.data
            if (Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(this.contentResolver,picked_image!!)
                picked_bitmap = ImageDecoder.decodeBitmap(source)
                binding.circleImage.setImageBitmap(picked_bitmap)
            }else{
                picked_bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,picked_image)
                binding.circleImage.setImageBitmap(picked_bitmap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}