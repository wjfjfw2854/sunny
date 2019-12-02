package com.example.wujunfeng.nestedscrollingapp.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wujunfeng.nestedscrollingapp.R;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends Fragment
{
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // mTextView = (TextView) view.findViewById(R.id.id_info);
        // mTextView.setText(mTitle);
        mDatas.clear();
        for (int i = 0; i < 50; i++)
        {
            mDatas.add(mTitle + " -> " + i);
        }
//        mRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item, mDatas)
//        {
//            @Override
//            public void convert(RecyclerView.ViewHolder holder, String o)
//            {
//                holder.setText(R.id.id_info, o);
//            }
//        });
        mRecyclerView.setAdapter(new CommonAdapter());

        return view;

    }

    public static TabFragment newInstance(String title)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    public class CommonAdapter extends RecyclerView.Adapter<Vh>{
        @Override
        public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item,null);
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
