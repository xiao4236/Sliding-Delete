package com.xiao.swipedelete;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiao.swipedelete.SwipeLayout.OnSwipeStateChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new MyAdapter());
		
		//设置滚动监听器
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
					//当ListView滚动的时候，关闭已经打开的item
					closeSwipeLayout();
				}
			}
			/**
			 * 当ListView滚动的时候执行
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
//				Log.e("tag", "onScroll");
			}
		});
	}
	//用来存放打开的swipeLayout
	private ArrayList<SwipeLayout> swipeLayouts = new ArrayList<SwipeLayout>();
	class MyAdapter extends BaseAdapter implements OnSwipeStateChangeListener{
		@Override
		public int getCount() {
			return 30;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = View.inflate(MainActivity.this, R.layout.adapter_list, null);
			}
			MyHolder myHolder = MyHolder.getHolder(convertView);
			myHolder.tv_name.setText("呵呵 - "+position);
			
			myHolder.swipeLayout.setOnSwipeStateChangeListener(this);
			
			return convertView;
		}
		@Override
		public void onClose(SwipeLayout swipeLayout) {
			//当swipeLayout关闭的时候，应该从swipeLayouts清除
			if(swipeLayouts.contains(swipeLayout)){
				swipeLayouts.remove(swipeLayout);
			}
		}
		@Override
		public void onOpen(SwipeLayout swipeLayout) {
			//当每次打开一个swipeLayout的时候，都需要存放swipeLayout
			if(!swipeLayouts.contains(swipeLayout)){
				swipeLayouts.add(swipeLayout);
			}
		}
		@Override
		public void onStartClose(SwipeLayout swipeLayout) {
			
		}
		@Override
		public void onStartOpen(SwipeLayout swipeLayout) {
			//当开始打开一个的时候，让swipeLayouts所有打开的layout都关闭
			closeSwipeLayout();
		}
	}
	
	private void closeSwipeLayout(){
		for(SwipeLayout swipeLayout : swipeLayouts){
			swipeLayout.close();
		}
	}
	
	static class MyHolder{
		TextView tv_name;
		TextView tv_delete;
		SwipeLayout swipeLayout;
		public MyHolder(View convertView){
			tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
			swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipeLayout);
		}
		public static MyHolder getHolder(View convertView){
			MyHolder myHolder = (MyHolder) convertView.getTag();
			if(myHolder==null){
				myHolder = new MyHolder(convertView);
				convertView.setTag(myHolder);
			}
			return myHolder;
		}
	}

}
