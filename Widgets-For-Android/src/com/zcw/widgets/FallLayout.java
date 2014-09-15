package com.zcw.widgets;

import com.zcw.widgets_for_android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FallLayout extends ViewGroup {
	
	int colums = 2;
	int columPadding = 0;
	int rowPadding = 0;
	
	public FallLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(attrs);		
	}
	public FallLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(attrs);
	}
	public FallLayout(Context context) {
		super(context);
	}
	public void setup(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FallLayout);
		colums = typedArray.getInt(R.styleable.FallLayout_fall_colums, colums);
		columPadding = typedArray.getDimensionPixelSize(R.styleable.FallLayout_fall_columPadding, columPadding);
		rowPadding = typedArray.getDimensionPixelSize(R.styleable.FallLayout_fall_rowPadding, rowPadding);
		typedArray.recycle();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
//		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    
	    int childWidthMax = (widthSize - getPaddingLeft() - getPaddingRight() - (colums - 1) * columPadding)
	    		/colums;
	    
	    int cHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.UNSPECIFIED);
	    final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	final MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
	    	int cWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthMax - layoutParams.leftMargin - layoutParams.rightMargin, MeasureSpec.EXACTLY);
	    	
	    	int childWidthMeasureSpec = getChildMeasureSpec(cWidthMeasureSpec, 0, layoutParams.width);
	        int childHeightMeasureSpec = getChildMeasureSpec(cHeightMeasureSpec, 0, layoutParams.height);
	    	childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
	    
	    
	    int columHeights[] = new int [colums];
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	final MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
	    	final int measureHeight = childView.getMeasuredHeight();  
	        int minColum = 0, minHeight = columHeights[0];
	        for (int j = 1; j < columHeights.length; j++) {
	        	if(minHeight > columHeights[j]){
	        		minColum = j;
	        		minHeight = columHeights[j];
	        	}
			}
	    	final int top = getPaddingTop() + columHeights[minColum] + layoutParams.topMargin + rowPadding;
			columHeights[minColum] = top + measureHeight + layoutParams.bottomMargin + rowPadding;
	    }
	    
	    int height = 0;
	    for (int j = 0; j < columHeights.length; j++) {
	    	height = Math.max(height, columHeights[j]);
		}
	    
	    setMeasuredDimension(widthSize, height - rowPadding);
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int childWidthMax = (getWidth() - getPaddingLeft() - getPaddingRight() - (colums - 1) * columPadding)
	    		/colums;
		int columHeights[] = new int [colums];
		
		final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	final MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
	    	final int measureHeight = childView.getMeasuredHeight();  
	    	final int measuredWidth = childView.getMeasuredWidth();
	    	
	        int minColum = 0, minHeight = columHeights[0];
	        for (int j = 1; j < columHeights.length; j++) {
	        	if(minHeight > columHeights[j]){
	        		minColum = j;
	        		minHeight = columHeights[j];
	        	}
			}
	        
	        final int left = getPaddingLeft() + (childWidthMax + columPadding) * minColum + layoutParams.leftMargin;
	        int right = left + measuredWidth;
	        if(measuredWidth > childWidthMax - layoutParams.rightMargin){
	        	right = left + childWidthMax - layoutParams.rightMargin;
	        }
	    	final int top = getPaddingTop() + columHeights[minColum] + layoutParams.topMargin + rowPadding;
	    	final int bottom = top + measureHeight;
	    	
			childView.layout(left, top, right, bottom);
			
			columHeights[minColum] = bottom;
	    }
		
	}

	
	@Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
	
}
