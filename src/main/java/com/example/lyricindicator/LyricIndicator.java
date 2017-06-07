package com.example.lyricindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by XMuser on 2017-06-06.
 */

public class LyricIndicator extends HorizontalScrollView {

    private static int DEFAULT_COLOR = 0xff000000;
    private static int CHANGED_COLOR = 0xffff0000;

    private Context context;
    private LinearLayout baseLinearLayout;
    private float textSize;
    private int defaultColor; //默认颜色
    private int changeColor; //渐变颜色
    private int padding;
    private int paddingL;
    private int paddingR;
    private int paddingT;
    private int paddingB;
    private ViewPager vp; //关联的viewpager
    private int currentPos;


    public LyricIndicator(Context context) {
        this(context, null);
    }

    public LyricIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LyricIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.LyricIndicator);
        textSize = t.getDimension(R.styleable.LyricIndicator_text_size, sp2px(14));
        defaultColor = t.getColor(R.styleable.LyricIndicator_default_color, DEFAULT_COLOR);
        changeColor = t.getColor(R.styleable.LyricIndicator_changed_color, CHANGED_COLOR);
        padding = (int) t.getDimension(R.styleable.LyricIndicator_item_padding, 0);
        paddingL = (int) t.getDimension(R.styleable.LyricIndicator_item_padding_l, padding);
        paddingR = (int) t.getDimension(R.styleable.LyricIndicator_item_padding_r, padding);
        paddingT = (int) t.getDimension(R.styleable.LyricIndicator_item_padding_t, padding);
        paddingB = (int) t.getDimension(R.styleable.LyricIndicator_item_padding_b, padding);
        t.recycle();

        addBaseView(context);
    }

    private void addBaseView(Context context) {
        baseLinearLayout = new LinearLayout(context);
        baseLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        baseLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        baseLinearLayout.setGravity(Gravity.CENTER_VERTICAL);

        addView(baseLinearLayout);
    }


    /**
     * 关联viewpager，
     * 请使用本view的addOnPagerChangeListener代替viewpager自身的addOnPagerChangeListener
     * @param vp
     */
    public void setupWithViewPager(final ViewPager vp) {
        this.vp = vp;
        if ( vp == null || vp.getAdapter() == null) {
            return;
        }
        addLyricTextViews();
        addClickEvent();
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                itemScroll(position, positionOffset);

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("ccy", "onPageSelected" + position);
                resetAllItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == ViewPager.SCROLL_STATE_IDLE){  //解决残影，不够完美，被这个问题烦死了！！！！
                    resetAllItem();
                }
            }
        });

    }

    private void resetAllItem() {
        for (int i = 0; i < baseLinearLayout.getChildCount(); i++) {
            LyricTextView ltv = (LyricTextView) baseLinearLayout.getChildAt(i);
            if (i == vp.getCurrentItem()) {
                ltv.setProgress(1f);
            } else {
                ltv.setProgress(0f);
            }
        }
        invalidate();
    }

    /**
     * 添加所有item
     */
    private void addLyricTextViews() {
        currentPos = vp.getCurrentItem();
        for (int i = 0; i < vp.getAdapter().getCount(); i++) {
            LyricTextView ltv = new LyricTextView(context);
            ltv.setAll(0f, vp.getAdapter().getPageTitle(i)+"", textSize, defaultColor, changeColor, LyricTextView.LEFT);
            ltv.setPadding(paddingL, paddingT, paddingR, paddingB);
            ltv.setTag(i);
            baseLinearLayout.addView(ltv);
            if (i == currentPos) {
                ltv.setProgress(1);
            }
        }
    }

    private void addClickEvent() {
        for (int i = 0; i < baseLinearLayout.getChildCount(); i++) {
            LyricTextView ltv = (LyricTextView) baseLinearLayout.getChildAt(i);
            ltv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    vp.setCurrentItem(pos);
                }
            });
        }
    }

    private void itemScroll(int position, float positionOffset) {
        if (positionOffset > 0 && position + 1 <= vp.getAdapter().getCount()) {
            LyricTextView left = (LyricTextView) baseLinearLayout.getChildAt(position);
            LyricTextView right = (LyricTextView) baseLinearLayout.getChildAt(position + 1);
            left.setDirection(LyricTextView.RIGHT);
            left.setProgress(1 - positionOffset);
            right.setDirection(LyricTextView.LEFT);
            right.setProgress(positionOffset);

            invalidate();
            layoutScroll(position, positionOffset);
        }
    }


    private void layoutScroll(int pos, float positionOffset) {
        scrollTo(calculateScrollXForTab(pos, positionOffset), 0);
    }

    private int calculateScrollXForTab(int pos, float positionOffset) {
        LyricTextView selectedChild = (LyricTextView) baseLinearLayout.getChildAt(pos);
        LyricTextView nextChild = (LyricTextView) baseLinearLayout.getChildAt(pos + 1);
        final int selectedWidth = selectedChild != null ? selectedChild.getWidth() : 0;
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;
        // base scroll amount: places center of tab in center of parent
        int scrollBase = selectedChild.getLeft() + (selectedWidth / 2) - (getWidth() / 2);
        // offset amount: fraction of the distance between centers of tabs
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);
        return (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR)
                ? scrollBase + scrollOffset
                : scrollBase - scrollOffset;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    //工具
    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


}
