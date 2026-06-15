package com.apptest.game2048.utils;

import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.List;

import com.apptest.game2048.R;
import com.apptest.game2048.models.CardData;


public class ExampleDataset {

    private List<ECCardData> dataset;

    public ExampleDataset() {
        dataset = new ArrayList<>(8);

        CardData item1 = new CardData();
        item1.setMainBackgroundResource(R.mipmap.board_x3_back);
        item1.setHeadBackgroundResource(R.mipmap.board_x3);
        item1.setHeadTitle("3 x 3");
        dataset.add(item1);

        CardData item2 = new CardData();
        item2.setMainBackgroundResource(R.mipmap.board_x4_back);
        item2.setHeadBackgroundResource(R.mipmap.board_x4);
        item2.setHeadTitle("4 x 4");
        dataset.add(item2);

        CardData item3 = new CardData();
        item3.setMainBackgroundResource(R.mipmap.board_x5_back);
        item3.setHeadBackgroundResource(R.mipmap.board_x5);
        item3.setHeadTitle("5 x 5");
        dataset.add(item3);

        CardData item4 = new CardData();
        item4.setMainBackgroundResource(R.mipmap.board_x6_back);
        item4.setHeadBackgroundResource(R.mipmap.board_x6);
        item4.setHeadTitle("6 x 6");
        dataset.add(item4);
    }

    public List<ECCardData> getDataset() {
//        Collections.shuffle(dataset);
        return dataset;
    }
}