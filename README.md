# lyricindicator
仿今日头条颜色渐变导航栏指示器
本控件是对[lyricTextView](https://github.com/CCY0122/lyrictextview)的封装应用<br/>
代码很少，建议直接复制<br/>
使用方法<br/><br/>
<com.example.lyricindicator.LyricIndicator<br/>
        android:id="@+id/indicator"<br/>
        android:layout_width="match_parent"<br/>
        android:layout_height="wrap_content"<br/>
        android:background="#11000000"<br/>
        app:item_padding="7dp"<br/>
        app:text_size="20sp"<br/>
        app:default_color="#000000"<br/>
        app:changed_color="#ff0000"><br/>
        </com.example.lyricindicator.LyricIndicator><br/><br/>
可使用的属性有：<br/>
        text_size 字体大小<br/>
        default_color默认颜色<br/>
        changed_color渐变颜色<br/>
        字体的左右上下padding：<br/>
        item_padding_l<br/>
        item_padding_r<br/>
        item_padding_t<br/>
        item_padding_b<br/>
        item_padding<br/><br/>
        '注意':IDE可能还会列出text、progress、direction这些属性，这是属性属于lyricTextView，设置了也是无效的。<br/>
        
在代码中,需要与viewpager进行关联:<br/>
 lyricIndicator = (LyricIndicator) findViewById(R.id.indicator);<br/>
 lyricIndicator.setupWithViewPager(mViewPager);<br/>
 ViewPager的adapter要实现 public CharSequence getPageTitle(int position)作为每一页对应的title<br/>

<br/>
![image](https://github.com/CCY0122/lyricindicator/blob/master/device-2017-06-07-102626~1.gif)
