package com.zcw.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**<b>FlowLayout流动布局</b>
 * <br>
 * 布局内的控件依次排列，超出布局宽度时自动换行
 * @author ThinkPad
 *
 */
public class FlowLayout extends ViewGroup{

	/** 控件最大宽度*/
	private int childViewMaxWidth;
	
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
//		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    
	    childViewMaxWidth = widthSize - getPaddingLeft() - getPaddingRight();
	    
	    int cWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childViewMaxWidth, MeasureSpec.EXACTLY);
		int cHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.UNSPECIFIED);
	    final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	final LayoutParams layoutParams = childView.getLayoutParams();
	    	int childWidthMeasureSpec = getChildMeasureSpec(cWidthMeasureSpec, 0, layoutParams.width);
	        int childHeightMeasureSpec = getChildMeasureSpec(cHeightMeasureSpec, 0, layoutParams.height);
	    	childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int cleft = getPaddingLeft();
		int ctop = getPaddingTop();
		
		int cMaxHeight = 0;
		
		final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	int measureHeight = childView.getMeasuredHeight();  
	        int measuredWidth = childView.getMeasuredWidth();
	    	
	    	final int right = cleft + measuredWidth;
	    	if(right > getWidth() - getPaddingRight()){
	    		//换行
	    		cleft = getPaddingLeft();
	    		ctop += cMaxHeight;
	    		cMaxHeight = 0;
	    	}
			childView.layout(cleft, ctop, cleft + measuredWidth, ctop + measureHeight);
			cMaxHeight = Math.max(cMaxHeight, measureHeight);
			
			cleft += measuredWidth;
	    }
		
	}
	
}
