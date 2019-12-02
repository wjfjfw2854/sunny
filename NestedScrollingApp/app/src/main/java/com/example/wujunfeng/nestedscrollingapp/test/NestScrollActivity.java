package com.example.wujunfeng.nestedscrollingapp.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wujunfeng.nestedscrollingapp.R;
import com.example.wujunfeng.nestedscrollingapp.test.view.SimpleViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;


public class NestScrollActivity extends AppCompatActivity/*FragmentActivity*/
{
//	private String[] mTitles = new String[] {"下部RecycleV", "相关TextView" ,"相关WebView","相关WebView2"};
	private String[] mTitles = new String[] {"下部RecycleV", "相关TextView" ,"相关WebView2"};
	private SimpleViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
//	private FragmentPagerAdapter mAdapter;
	private FragMentPagerAdapterExt mAdapter;
	private Fragment[] mFragments = new Fragment[mTitles.length];
	private RecyclerView recyclerVTop;
	private FragmentManager mFragmentManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainnextscroll);

		mFragmentManager = getSupportFragmentManager();
		initViews();
		initDatas();
		initEvents();
	}

	private void initEvents()
	{
		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				mIndicator.scroll(position, positionOffset);
			}

			@Override
			public void onPageScrollStateChanged(int state)
			{

			}
		});

	}

	private void initDatas()
	{
		mIndicator.setTitles(mTitles);

		for (int i = 0; i < mTitles.length; i++)
		{
//			if(i == 0) {
//				mFragments[i] = TabFragment.newInstance(mTitles[i]);
//			}
//			else if(i == 1)
//			{
//				mFragments[i] =  CompleteFragment.newInstance(mTitles[i]);
//			}
//			else if(i == 2)
//			{
//				mFragments[i] = WebViewFrag.newInstance(mTitles[i]);
//			}
//			else if(i == 3)
//			{
//				mFragments[i] = WebViewCorporationFrag.newInstance(mTitles[i]);
//			}

			if(i == 0) {
				mFragments[i] = TabFragment.newInstance(mTitles[i]);
			}
			else if(i == 1)
			{
				mFragments[i] =  CompleteFragment.newInstance(mTitles[i]);
			}
			else if(i == 2)
			{
				mFragments[i] = WebViewCorporationFrag.newInstance(mTitles[i]);
			}
		}

//		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
//		{
//			@Override
//			public int getCount()
//			{
//				return /*mTitles.length*/2;
//			}
//
//			@Override
//			public Fragment getItem(int position)
//			{
//				if(position == 0)
//				{
//					return  mFragments[0];
//				}
//				else
//				{
//
//				}
//				return mFragments[updateIndex];
//			}
//
//		};

		 mAdapter = new FragMentPagerAdapterExt(getSupportFragmentManager());

		mViewPager.setAdapter(mAdapter);
		mViewPager.setCurrentItem(0);
	}

	public class FragMentPagerAdapterExt extends FragmentPagerAdapter{
		public FragMentPagerAdapterExt(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments[position];
		}

		@Override
		public int getCount() {
			return mFragments.length;
		}

		@Override
		public int getItemPosition(@NonNull Object object) {
			return POSITION_NONE;
		}

		@Override
		public long getItemId(int position) {
			return mFragments[position].hashCode();
		}
	}

	private void initViews()
	{
		mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
		mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
		
		/*
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.id_stickynavlayout_topview);
		TextView tv = new TextView(this);
		tv.setText("我的动态添加的");
		tv.setBackgroundColor(0x77ff0000);
		ll.addView(tv, new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 600));
		*/

		recyclerVTop = findViewById(R.id.recyclerVTop);
		recyclerVTop.setLayoutManager(new LinearLayoutManager(NestScrollActivity.this));
		CommonAdapter commonAdapter= new CommonAdapter();
		for (int i = 0; i < 50; i++)
		{
			commonAdapter.mDatas.add("头部无夏卡拉卡顿滚" + " -> " + i);
		}
		recyclerVTop.setAdapter(commonAdapter);
	}


	public class CommonAdapter extends RecyclerView.Adapter<Vh>{
		private List<String> mDatas = new ArrayList<String>();
		@Override
		public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(NestScrollActivity.this).inflate(R.layout.item,null);
			return new Vh(view);
		}

		@Override
		public void onBindViewHolder(Vh holder, int position) {
			holder.setText(mDatas.get(position));
		}

		@Override
		public int getItemCount() {
			return mDatas.size();
		}
	}

	public class Vh extends RecyclerView.ViewHolder{

		private TextView tv;
		public Vh(View view) {
			super(view);
			tv = itemView.findViewById(R.id.id_info);
		}

		public void setText(String str)
		{
			tv.setText(str);
		}
	}
}
