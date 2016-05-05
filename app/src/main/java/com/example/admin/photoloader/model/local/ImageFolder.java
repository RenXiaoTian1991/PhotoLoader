package com.example.admin.photoloader.model.local;

import java.util.ArrayList;

/**
 * Created by admin on 2016/4/26.
 */
public class ImageFolder {
    //文件夹路径
    private String dir;
    //文件夹名称
    private String name;
    //第一场图片的路径
    private ArrayList<String> firstImgPath;
    //图片数量
    private int count;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndex = dir.lastIndexOf("/");
        this.name = dir.substring(lastIndex+1);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(ArrayList<String> firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
