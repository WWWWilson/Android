package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Tools> toolsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTools();
        RecyclerView recyclerView = findViewById(R.id.main_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        ToolAdapter adapter = new ToolAdapter(toolsList);
        recyclerView.setAdapter(adapter);
    }


    private void initTools() {
        for (int i=0;i<2;i++){
            Tools ershou = new Tools(R.mipmap.ershou,"ershou");
            toolsList.add(ershou);
            Tools jiangjin = new Tools(R.mipmap.jiangjin,"jiangjin");
            toolsList.add(jiangjin);
            Tools jieru = new Tools(R.mipmap.jieru,"jieru");
            toolsList.add(jieru);
            Tools lixi = new Tools(R.mipmap.lixi,"lixi");
            toolsList.add(lixi);
            Tools other = new Tools(R.mipmap.other,"other");
            toolsList.add(other);
            Tools shouzhai = new Tools(R.mipmap.shouzhai,"shouzhai");
            toolsList.add(shouzhai);
            Tools touzi = new Tools(R.mipmap.touzi,"touzi");
            toolsList.add(touzi);
            Tools xinzi = new Tools(R.mipmap.xinzi,"xinzi");
            toolsList.add(xinzi);

        }
    }

}


