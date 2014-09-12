package com.zcw.widgets;

import com.zcw.widgets_for_android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**<b>TabletLayout方格布局</b>
 * <br>
 * 类似于GridView ，布局内 的各个控件按照<b>方格</b>排列，可以控制列数和控件间间距。
 * @author ThinkPad
 *
 */
public class TabletLayout extends ViewGroup {
	
	/** 默认列数*/
	private int colums = 1;
	/** 列间距*/
	private int colPadding;
	/** 行间距*/
	private int rowPadding;
	/** 列宽度*/
	private int columWidth;
	
	public TabletLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(attrs);
	}
	
	public TabletLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(attrs);
	}
	
	public TabletLayout(Context context) {
		super(context);
	}
	
	/**
	 * @param attrs
	 */
	public void setup(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabletLayout);
		
		colums = typedArray.getInt(R.styleable.TabletLayout_colums, colums);
		colPadding = typedArray.getDimensionPixelSize(R.styleable.TabletLayout_colPadding, colPadding);
		rowPadding = typedArray.getDimensionPixelSize(R.styleable.TabletLayout_rowPadding, rowPadding);
		
		typedArray.recycle();
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
	    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
	    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
	    
	    columWidth = (widthSize - getPaddingLeft() - getPaddingRight() - colPadding * (colums - 1)) / colums;
		int cWidthMeasureSpec = MeasureSpec.makeMeasureSpec(columWidth, MeasureSpec.EXACTLY);
		int cHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
		
	    final int childCount = getChildCount();
	    for (int i = 0; i < childCount; i++) {
	    	View childView = getChildAt(i);
	    	final LayoutParams layoutParams = childView.getLayoutParams();
	    	
	    	int childWidthMeasureSpec = getChildMeasureSpec(cWidthMeasureSpec, 0, layoutParams.width);
	        int childHeightMeasureSpec = getChildMeasureSpec(cHeightMeasureSpec, 0, layoutParams.height);
	    	
	    	childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
		}
	    
		
	    switch (widthMode) {
		case MeasureSpec.EXACTLY:
		case MeasureSpec.UNSPECIFIED:
		case MeasureSpec.AT_MOST:
			setMeasuredDimension(widthSize, heightSize);
			break;
		default:
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			break;
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		int cleft = getPaddingLeft();
		int ctop = getPaddingTop();
		
		int cMaxHeight = 0;
		
		final int childCount = getChildCount();
		
//		List<View> specified = new ArrayList<View>(childCount);
//		List<View> unspecified = new ArrayList<View>(childCount);
//		
//		for (int i = 0; i < childCount; i++) {
//	        View childView = getChildAt(i);
//	        final TabletLayoutParams layoutParams = (TabletLayoutParams) childView.getLayoutParams();
//	        if(layoutParams.col == -1 || layoutParams.row == -1){
//	        	unspecified.add(childView);
//	        	continue;
//	        }
//	        specified.add(childView);
//		}
//		
//		Collections.sort(specified, new Comparator<View>(){
//			@Override
//			public int compare(View v1, View v2) {
//				final TabletLayoutParams layoutParams1 = (TabletLayoutParams) v1.getLayoutParams();
//				final TabletLayoutParams layoutParams2 = (TabletLayoutParams) v2.getLayoutParams();
//				if(layoutParams1.row > layoutParams2.row)
//					return -1;
//				else if(layoutParams1.row < layoutParams2.row)
//					return 1;
//				
//				if(layoutParams1.col > layoutParams2.col)
//					return -1;
//				else if(layoutParams1.col < layoutParams2.col)
//					return 1;
//				
//				return 0;
//			}
//		});
		
		for (int i = 0; i < childCount; i++) {
			
	        View childView = getChildAt(i);
	        int measureHeight = childView.getMeasuredHeight();  
	        int measuredWidth = childView.getMeasuredWidth();
	        
	        final TabletLayoutParams layoutParams = (TabletLayoutParams) childView.getLayoutParams();
//	        final int paddingLeft = childView.getPaddingLeft();
//	        final int paddingRight = childView.getPaddingRight();
	        
	        int childViewRight = measuredWidth ;
	        if(childViewRight > columWidth - layoutParams.rightMargin){
	        	childViewRight = columWidth;
	        }
	        
	        childView.layout(cleft + layoutParams.leftMargin, 
	        		ctop + layoutParams.topMargin,
	        		cleft + childViewRight,
	        		ctop + measureHeight - layoutParams.bottomMargin);
	        
	        cMaxHeight = Math.max(cMaxHeight, measureHeight);
	        
	        if(i % colums == colums - 1){
				cleft = getPaddingLeft();
				ctop += cMaxHeight + rowPadding;
				cMaxHeight = 0;
			}else{
				cleft += columWidth + colPadding;
			}
		}
	}
	
	
	@Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new TabletLayoutParams(getContext(), attrs);
    }
	
	/**
	 * @author ThinkPad
	 *
	 */
	class TabletLayoutParams extends MarginLayoutParams{
		
		public int col = -1;
		public int row = -1;
		
		public TabletLayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
			
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabletLayout);
			col = typedArray.getInt(R.styleable.TabletLayout_col, col);
			row = typedArray.getInt(R.styleable.TabletLayout_row, row);
			typedArray.recycle();
			
		}

		public TabletLayoutParams(int width, int height) {
			super(width, height);
		}

		public TabletLayoutParams(LayoutParams source) {
			super(source);
		}
	}
}
