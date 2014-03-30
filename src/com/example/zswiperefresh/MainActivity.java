package com.example.zswiperefresh;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private SwipeRefreshLayout srl1;
    private SwipeRefreshLayout srl2;
    private ListView lv;
    private ArrayAdapter<Integer> adapter;
    private List<Integer> data = new ArrayList<Integer>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // init ListView
        lv = (ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<Integer>(this, R.layout.listview_item, R.id.tv, data);
        lv.setAdapter(adapter);

        // init left SwipeRefreshLayout
        srl1 = (SwipeRefreshLayout) findViewById(R.id.srl1);
        srl1.setColorScheme(android.R.color.holo_blue_light,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light);
        srl1.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                srl1.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                       "content view feeling refreshed!",
                                       Toast.LENGTH_SHORT).show();
                        srl1.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        // init right SwipeRefreshLayout
        srl2 = (SwipeRefreshLayout) findViewById(R.id.srl2);
        srl2.setColorScheme(android.R.color.holo_blue_dark,
                            android.R.color.holo_green_dark,
                            android.R.color.holo_orange_dark,
                            android.R.color.holo_red_dark);
        srl2.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                srl2.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getSomeData();
                        srl2.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "ListView feeling refreshed!",
                                       Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Refresh!");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            srl1.setRefreshing(true);
            srl2.setRefreshing(true);
            srl1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    getSomeData();
                    srl1.setRefreshing(false);
                    srl2.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "So refreshing!",
                                   Toast.LENGTH_SHORT).show();
                }
            }, 2000);
        }

        return true;
    }

    /**
     * Populate ListView with data.
     */
    private void getSomeData() {
        if (data.isEmpty()) {
            for (int i = 0; i < 20; i++) {
                data.add(i);
            }
        } else {
            int start = data.get(data.size() - 1) + 1;
            for (int i = 0; i < 20; i++) {
                data.set(i, start + i);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
