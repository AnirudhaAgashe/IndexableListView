package com.woozzu.android.indexablelistview;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

import com.woozzu.android.widget.IndexableListView;

/**
 * Example of indexable list view backed by cursor adapter
 *
 */
public class CursorBasedIndexableListView extends Activity {

	private MatrixCursor mCursor;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cursor_based);
		mContext = CursorBasedIndexableListView.this;
		initComponents();
	}

	private void initComponents() {
		String[] countries = getResources()
				.getStringArray(R.array.country_list);
		mCursor = new MatrixCursor(new String[] { "_id", "country" });
		int length = countries.length;
		//add entries to cursor
		for (int i = 0; i < length; i++) {
			mCursor.addRow(new Object[] { i, countries[i] });
		}

		IndexableListView list = (IndexableListView) findViewById(R.id.list);
		list.setFastScrollEnabled(true);
		
		String[] from = { "country" };
		int[] to = { android.R.id.text1 };
		MyCursorAdapter myCursorAdapter = new MyCursorAdapter(mContext,
				android.R.layout.simple_dropdown_item_1line, mCursor, from, to,
				0);
		list.setAdapter(myCursorAdapter);

	}

	public class MyCursorAdapter extends SimpleCursorAdapter implements
			SectionIndexer {

		private AlphabetIndexer mAlphabetIndexer;

		public MyCursorAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to, int flags) {
			super(context, layout, c, from, to, flags);
			mAlphabetIndexer = new AlphabetIndexer(mCursor,
					mCursor.getColumnIndex("country"),
					"#ABCDEFGHIJKLMNOPQRTSUVWXYZ");
		}

		@Override
		public Object[] getSections() {
			return mAlphabetIndexer.getSections();
		}

		@Override
		public int getPositionForSection(int sectionIndex) {
			return mAlphabetIndexer.getPositionForSection(sectionIndex);
		}

		@Override
		public int getSectionForPosition(int position) {
			return mAlphabetIndexer.getSectionForPosition(position);
		}

	}

}
