package com.apptest.game2048.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apptest.game2048.backups.SaveManager;
import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import com.apptest.game2048.R;
import com.apptest.game2048.adapters.CommentArrayAdapter;
import com.apptest.game2048.models.CardData;
import com.apptest.game2048.utils.ExampleDataset;
import com.apptest.game2048.utils.ItemsCountView;

import static com.apptest.game2048.utils.MainGame.numSquaresX;
import static com.apptest.game2048.utils.MainGame.numSquaresY;

public class HomeActivity extends AppCompatActivity {

    private ECPagerView ecPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Create adapter for pager
        ECPagerViewAdapter adapter = new ECPagerViewAdapter(this, new ExampleDataset().getDataset()) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, final ListView list, final ECCardData data) {
                final CardData cardData = (CardData) data;

                // Create adapter for list inside a card and set adapter to card content
                CommentArrayAdapter commentArrayAdapter = new CommentArrayAdapter(HomeActivity.this, cardData.getListItems());
                list.setAdapter(commentArrayAdapter);
                list.setDivider(getResources().getDrawable(R.drawable.list_divider));
                list.setDividerHeight((int) pxFromDp(HomeActivity.this, 0.5f));
                list.setSelector(R.color.transparent);
                list.setBackgroundColor(Color.WHITE);
                list.setCacheColorHint(Color.TRANSPARENT);

                // Add gradient to root header view
                View gradient = new View(HomeActivity.this);
                gradient.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                gradient.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_head_gradient));
                head.addView(gradient);

                // Inflate main header layout and attach it to header root view
                inflaterService.inflate(R.layout.simple_head, head);

                // Set header data from data object
                TextView title = (TextView) head.findViewById(R.id.title);
                title.setText(cardData.getHeadTitle());

                // Add onclick listener to card header for toggle card state
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
//                        ecPagerView.toggle();
                        if (cardData.getHeadTitle().equals("3 x 3")) {
                            numSquaresX = 3;    numSquaresY = 3;
                            startActivity(new Intent(getApplicationContext(), GameBoardActivity.class));
                            overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
                            finish();
                        } else  if (cardData.getHeadTitle().equals("4 x 4")) {
                            numSquaresX = 4;    numSquaresY = 4;
                            startActivity(new Intent(getApplicationContext(), GameBoardActivity.class));
                            overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
                            finish();
                        } else  if (cardData.getHeadTitle().equals("5 x 5")) {
                            numSquaresX = 5;    numSquaresY = 5;
                            startActivity(new Intent(getApplicationContext(), GameBoardActivity.class));
                            overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
                            finish();
                        } else  if (cardData.getHeadTitle().equals("6 x 6")) {
                            numSquaresX = 6;    numSquaresY = 6;
                            startActivity(new Intent(getApplicationContext(), GameBoardActivity.class));
                            overridePendingTransition(R.anim.fab_open, R.anim.fab_close);
                            finish();
                        }
                    }
                });
            }
        };

        ecPagerView.setPagerViewAdapter(adapter);
        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) findViewById(R.id.ec_bg_switcher_element));

        final ItemsCountView itemsCountView = (ItemsCountView) findViewById(R.id.items_count_view);
        ecPagerView.setOnCardSelectedListener(new ECPagerView.OnCardSelectedListener() {
            @Override
            public void cardSelected(int newPosition, int oldPosition, int totalElements) {
                itemsCountView.update(newPosition, oldPosition, totalElements);
            }
        });

        /*new Code*/
        SaveManager.build(this);
        SaveManager.getInstance().loadBackup(null);
    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}