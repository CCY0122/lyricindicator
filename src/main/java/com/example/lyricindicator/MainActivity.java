package com.example.lyricindicator;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private LyricIndicator lyricIndicator;
    String[] str = new String[]{"1111","22","333","asdasdasd","短","长长长长长长","CCY","qqqqqqqqq","wwwwwww","rrrr"};
    List<View> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < str.length; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.vp_items,null);
            TextView t = (TextView) v.findViewById(R.id.tv);
            t.setText(str[i]+"");
            data.add(v);
        }
        vp = (ViewPager) findViewById(R.id.vp);
        lyricIndicator = (LyricIndicator) findViewById(R.id.indicator);

        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(data.get(position));
                return data.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(data.get(position));
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return str[position];
            }
        });
        lyricIndicator.setupWithViewPager(vp);
    }
}
