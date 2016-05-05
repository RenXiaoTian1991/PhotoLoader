package com.example.admin.photoloader.model.local;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Created by admin on 2016/4/26.
 */
public class LoaderData extends AsyncTaskLoader {

    private String url;
    private Context mContext;
    private String firstImage;
    private HashSet<String> mDirPaths;
    private int totalCount;
    private List<ImageFolder> mImageFloders;
    public LoaderData(Context context, Bundle args) {
        super(context);
        this.mContext = context;
        url = args.getString("url");
        mDirPaths = new HashSet<String>();
        mImageFloders = new ArrayList<ImageFolder>();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e("晓天======", "onStartLoading");
    }

    @Override
    public Object loadInBackground() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor mCursor = contentResolver.query(uri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        while (mCursor.moveToNext())
        {
            // 获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));

            Log.e("TAG", path);
            // 拿到第一张图片的路径
            if (firstImage == null)
                firstImage = path;
            // 获取该图片的父路径名
            final File parentFile = new File(path).getParentFile();
            if (parentFile == null)
                continue;
            String dirPath = parentFile.getAbsolutePath();
            ImageFolder imageFloder = null;
            // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
            if (mDirPaths.contains(dirPath))
            {
                continue;
            } else
            {
                mDirPaths.add(dirPath);
                // 初始化imageFloder
                imageFloder = new ImageFolder();
                imageFloder.setDir(dirPath);
                ArrayList<String> childUrls = new ArrayList<String>();

                childUrls.add(path);
//                File[] childrens = new File(dirPath).listFiles(new FileFilter() {
//                    @Override
//                    public boolean accept(File pathname) {
//                        String fileName = pathname.getAbsolutePath();
//                        if (fileName.endsWith(".jpg")
//                                || fileName.endsWith(".png")
//                                || fileName.endsWith(".jpeg")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                int size = childrens.length;
//                for (int i=0;i<size;i++){
//                    if(!(i>=5)){
//                        childUrls.add(childrens[i].getAbsolutePath());
//                    }else {
//                        break;
//                    }
//                }
                imageFloder.setFirstImgPath(childUrls);
            }

            int picSize = parentFile.list(new FilenameFilter()
            {
                @Override
                public boolean accept(File dir, String filename)
                {
                    if (filename.endsWith(".jpg")
                            || filename.endsWith(".png")
                            || filename.endsWith(".jpeg"))
                        return true;
                    return false;
                }
            }).length;
            totalCount += picSize;

            imageFloder.setCount(picSize);
            mImageFloders.add(imageFloder);

//            if (picSize > mPicsSize)
//            {
//                mPicsSize = picSize;
//                mImgDir = parentFile;
//            }
        }

        mCursor.close();
        return mImageFloders;
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
