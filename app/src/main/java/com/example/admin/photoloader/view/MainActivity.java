package com.example.admin.photoloader.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.admin.photoloader.R;
import com.example.admin.photoloader.adapter.PhotoAdapter;
import com.example.admin.photoloader.model.ImgData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    RecyclerView listView;
    private PhotoAdapter mAdapter;

    private String[] IMG_URL_LIST = {
            "http://ac-QYgvX1CC.clouddn.com/36f0523ee1888a57.jpg",
            "http://ac-QYgvX1CC.clouddn.com/07915a0154ac4a64.jpg",
            "http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg",
            "http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg",
            "http://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg",
            "http://ac-QYgvX1CC.clouddn.com/15c5c50e941ba6b0.jpg",
            "http://ac-QYgvX1CC.clouddn.com/10762c593798466a.jpg",
            "http://ac-QYgvX1CC.clouddn.com/eaf1c9d55c5f9afd.jpg",
            "http://ac-QYgvX1CC.clouddn.com/ad99de83e1e3f7d4.jpg",
            "http://ac-QYgvX1CC.clouddn.com/233a5f70512befcc.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PhotoAdapter(null, this);
        listView.setAdapter(mAdapter);

        initLoader();
    }

    private void initLoader() {
        Random random = new Random();
        int loaderId = random.nextInt(10);
        getLoaderManager().destroyLoader(loaderId);
        Bundle bundle = new Bundle();
        bundle.putString("url",IMG_URL_LIST[2]);
        getLoaderManager().initLoader(loaderId,bundle,mAdapter);
    }

    private List<ImgData> getData() {
        ArrayList<ImgData> mDatas = new ArrayList<ImgData>();
        for (int i = 0; i < 18; i++) {
            List<String> imgUrls = new ArrayList<>();
            imgUrls.addAll(Arrays.asList(IMG_URL_LIST).subList(0, i % 9));
            ImgData post = new ImgData("相册名称", imgUrls);
            mDatas.add(post);
        }
        return mDatas;
    }
}
