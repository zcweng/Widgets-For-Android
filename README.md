Widgets-For-Android
===================
<b>TabletLayout方格布局</b>
类似于GridView ，布局内 的各个控件按照<b>方格</b>排列，可以控制列数和控件间间距。

<b>FlowLayout流动布局</b>
<br>
布局内的控件依次排列，超出布局宽度时自动换行
<br>

        <com.zcw.widgets.FlowLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content" >
          
                <android widgets.../>
          
        </com.zcw.widgets.FlowLayout>

<br>
<img src="https://github.com/zcweng/Widgets-For-Android/blob/master/Widgets-For-Android/flow.png"/>


<b>FallLayout瀑布布局</b>
<br>
布局内的控件分列排列，每列依次排列
<br>

        <com.zcw.widgets.FallLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:fall_columPadding="5dp"
                app:fall_colums="2"
                app:fall_rowPadding="5dp" >
                
                <android widgets.../>
                
        </com.zcw.widgets.FallLayout>
        
<br>
<img src="https://github.com/zcweng/Widgets-For-Android/blob/master/Widgets-For-Android/fall.png"/>
