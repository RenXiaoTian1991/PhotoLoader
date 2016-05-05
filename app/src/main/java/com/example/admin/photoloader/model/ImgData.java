package com.example.admin.photoloader.model;

import java.util.List;

/**
 * Created by admin on 2016/4/26.
 */
public class ImgData {
    private String name;
    private List<String> mImgUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getmImgUrls() {
        return mImgUrls;
    }

    public void setmImgUrls(List<String> mImgUrls) {
        this.mImgUrls = mImgUrls;
    }

    public ImgData(String name, List<String> mImgUrls) {

        this.name = name;
        this.mImgUrls = mImgUrls;
    }
}
