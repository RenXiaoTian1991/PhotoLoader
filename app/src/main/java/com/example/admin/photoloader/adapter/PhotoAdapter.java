package com.example.admin.photoloader.adapter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.photoloader.R;
import com.example.admin.photoloader.model.ImgData;
import com.example.admin.photoloader.model.local.ImageFolder;
import com.example.admin.photoloader.model.local.LoaderData;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/4/26.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyHolder> implements LoaderManager.LoaderCallbacks<List<ImageFolder>>{

    private List<ImageFolder> mDatas;
    private Context mContext;
    private static ColorFilter mColorFilter;

    public PhotoAdapter(List<ImgData> mDatas,Context context) {
//        this.mDatas = mDatas;
        this.mDatas = new ArrayList<ImageFolder>();
        mContext = context;

        float[]array = new float[]{
                1,0,0,0,-70,
                0,1,0,0,-70,
                0,0,1,0,-70,
                0,0,0,1,0,
        };
        mColorFilter = new ColorMatrixColorFilter(new ColorMatrix(array));
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_img,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
         holder.bindData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name) TextView name;
        @Bind(R.id.grid) NineGridImageView gridImageView;

        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Log.e("晓天====","图片路径是"+s);
                Picasso.with(mContext)
                        .load(Uri.fromFile(new File(s)))
                        .placeholder(R.mipmap.ic_default_image)
                        .into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                Log.e("晓天",list.get(index));
                Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
            }
        };

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            gridImageView.setAdapter(mAdapter);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "你点击了相册标题", Toast.LENGTH_SHORT).show();
                }
            });
        }


        public void bindData(ImageFolder imgData) {
            gridImageView.setImagesData(imgData.getFirstImgPath());
            name.setText(imgData.getName());
        }
    }


    @Override
    public Loader<List<ImageFolder>> onCreateLoader(int id, Bundle args) {
        return new LoaderData(mContext,args);
    }

    @Override
    public void onLoadFinished(Loader<List<ImageFolder>> loader, List<ImageFolder> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<ImageFolder>> loader) {

    }


}
