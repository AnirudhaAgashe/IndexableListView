/*
 * Copyright 2011 woozzu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.woozzu.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

public class IndexableListView extends ListView {

	private static final String TAG = IndexableListView.class.getSimpleName();

	private boolean mIsFastScrollEnabled = false;
	// private AttributeSet mAttrs;
	private int mDefStyle;
	private IndexScroller mScroller = null;
	private GestureDetector mGestureDetector = null;

	public IndexableListView(Context context) {
		super(context);
		Log.d(TAG, "IndexableListView(Context context)");
	}

	public IndexableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new IndexScroller(context, this, attrs, mDefStyle);
		// mAttrs = attrs;
	}

	public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new IndexScroller(context, this, attrs, defStyle);
		// mAttrs = attrs;
		mDefStyle = defStyle;
	}

	@Override
	public boolean isFastScrollEnabled() {
		return mIsFastScrollEnabled;
	}

	@Override
	public void setFastScrollEnabled(boolean enabled) {
		mIsFastScrollEnabled = enabled;
		if (mIsFastScrollEnabled) {
			// if (mScroller == null)
			// mScroller = new IndexScroller(getContext(),
			// this,mAttrs,mDefStyle);
		} else {
			if (mScroller != null) {
				mScroller.hide();
				mScroller = null;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		// Overlay index bar
		if (mScroller != null)
			mScroller.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Intercept ListView's touch event
		if (mScroller != null && mScroller.onTouchEvent(ev))
			return true;

		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getContext(),
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onFling(MotionEvent e1, MotionEvent e2,
								float velocityX, float velocityY) {
							// If fling happens, index bar shows
							if (mIsFastScrollEnabled) {
								mScroller.show();
							}
							return super.onFling(e1, e2, velocityX, velocityY);
						}

					});
		}
		mGestureDetector.onTouchEvent(ev);

		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	/**
	 * @return the index bar width
	 */
	public float getIndexbarWidth() {
		if (mScroller != null) {
			return mScroller.getIndexbarWidth();
		}
		return 0;
	}

	/**
	 * Sets the index bar width
	 * 
	 * @param indexbarWidth
	 *            in dp
	 */
	public void setIndexbarWidth(float indexbarWidth) {
		this.mIndexbarWidth = indexbarWidth * mDensity;
	}

	/**
	 * @return the index bar margin
	 */
	public float getIndexbarMargin() {
		return mIndexbarMargin;
	}

	/**
	 * Sets the index bar margin
	 * 
	 * @param indexbarMargin
	 *            in dp
	 */
	public void setIndexbarMargin(float indexbarMargin) {
		this.mIndexbarMargin = indexbarMargin * mDensity;
	}

	/**
	 * @return index bar text color
	 */
	public int getIndexbarTextColor() {
		return mIndexbarTextColor;
	}

	/**
	 * Sets the index bar text color
	 * 
	 * @param indexbarTextColor
	 */
	public void setIndexbarTextColor(int indexbarTextColor) {
		this.mIndexbarTextColor = indexbarTextColor;
	}

	/**
	 * @return the index bar background color
	 */
	public int getIndexbarBgColor() {
		return mIndexbarBgColor;
	}

	/**
	 * Sets the index bar background color
	 * 
	 * @param indexbarBgColor
	 */
	public void setIndexbarBgColor(int indexbarBgColor) {
		this.mIndexbarBgColor = indexbarBgColor;
	}

	/**
	 * @return index bar user high lighted text color
	 */
	public int getIndexbarSelectedTextColor() {
		return mIndexbarSelectedTextColor;
	}

	/**
	 * Sets the user highlighted text color
	 * 
	 * @param indexbarSelectedTextColor
	 */
	public void setIndexbarSelectedTextColor(int indexbarSelectedTextColor) {
		this.mIndexbarSelectedTextColor = indexbarSelectedTextColor;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (mScroller != null)
			mScroller.setAdapter(adapter);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mScroller != null)
			mScroller.onSizeChanged(w, h, oldw, oldh);
	}

}
