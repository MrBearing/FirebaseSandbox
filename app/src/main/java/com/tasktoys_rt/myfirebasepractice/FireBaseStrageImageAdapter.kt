package com.tasktoys_rt.myfirebasepractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Created by takum on 2018/03/04.
 */
class FireBaseStrageImageAdapter(context: Context, fileNames: List<String>) : ArrayAdapter<String>(context, RESOURCE_ID, fileNames) {

    companion object {
        private val RESOURCE_ID = R.layout.grid_item
    }
    private val mFirebaseStorageReference: StorageReference
    private val mInflater: LayoutInflater

    init {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mFirebaseStorageReference = FirebaseStorage.getInstance().getReference().child("imgs")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = mInflater.inflate(RESOURCE_ID, null)
        }
        val imageView = convertView!!.findViewById(R.id.imageView1) as ImageView
        val imageFileName = getItem(position)
        val imageFileReference = mFirebaseStorageReference.child(imageFileName)
        Glide.with(context)
                .using(FirebaseImageLoader())
                .load(imageFileReference)
                .into(imageView)
        return convertView
    }


}