# lyricindicator
仿今日头条颜色渐变导航栏指示器
本控件是对[lyricTextView](https://github.com/CCY0122/lyrictextview)的封装应用<br/>
详解：[自定义View之仿今日头条颜色渐变指示器导航栏](http://blog.csdn.net/ccy0122/article/details/72902977)<br/>
**代码很少，建议直接复制**<br/>

## 效果图

![image](https://github.com/CCY0122/lyricindicator/blob/master/image.gif)
## 使用方法

第一步：<br/>
```xml
<com.example.lyricindicator.LyricIndicator
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#11000000"
        app:item_padding="7dp"
        app:text_size="20sp"
        app:default_color="#000000"
        app:changed_color="#ff0000">
        </com.example.lyricindicator.LyricIndicator><br/>
可使用的属性有：
        text_size 字体大小
        default_color默认颜色
        changed_color渐变颜色
        字体的左右上下padding：
        item_padding_l
        item_padding_r
        item_padding_t
        item_padding_b
        item_padding<br/>
```
        **'注意':IDE可能还会列出text、progress、direction这些属性，这些属性属于[lyricTextView](https://github.com/CCY0122/lyrictextview)，设置了也是无效的。**<br/>
       
第二步：与viewpager进行关联:<br/>
```java
 lyricIndicator = (LyricIndicator) findViewById(R.id.indicator);
 lyricIndicator.setupWithViewPager(mViewPager);
 ```
 **注意：ViewPager的adapter要实现 `public CharSequence getPageTitle(int position)`作为每一页对应的title**<br/>

