package com.han.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.han.myapplication.R;
import com.han.myapplication.bean.Constant;
import com.han.myapplication.bean.Content;
import com.han.myapplication.bean.People;
import com.han.myapplication.bean.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aaa on 2017/3/24.
 */

public class MenuFragment extends Fragment {
    @BindView(R.id.mess_ll)
    ListView ll;
    private ArrayList<Room> mroom;
    //选中条目的索引值
    public int currentPosition = 0;
    private ArrayList<People> mpeople;
    public MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.menu, null);
        ButterKnife.bind(this, view);
        initdata();
        return view;

    }



    private void initdata() {
        Content content = new Content();
        mroom = content.getRoom();
        mpeople = content.getPeople();

        adapter = new MyAdapter();
        ll.setAdapter(adapter);
    }

    /**
     * ListView的适配
     */
    public class MyAdapter extends BaseAdapter {

        ViewHolder holder;

        @Override
        public int getCount() {
            return mroom.size();
        }

        @Override
        public String getItem(int position) {
            return mroom.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.menu_item, null);
                holder.room = (TextView) convertView.findViewById(R.id.room);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置背景色
            if (position == currentPosition) {
                holder.room.setBackgroundColor(Color.WHITE);
            } else {
                holder.room.setBackgroundColor(Color.GRAY);

            }

            holder.setPosition(position);

            holder.room.setText(getItem(position));


            holder.room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainFragment main = (MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN);
                    //点击条目更换背景色，记住当前的位置
                    currentPosition = position;
                    notifyDataSetChanged();
                    int room_typeID = mroom.get(position).getTypeID();

                    for (int i = 0; i < mpeople.size(); i++) {
                        int people_typeID = mpeople.get(i).getTypeID();
                        if (room_typeID == people_typeID) {
                            //联动右侧的列表
                            main.shl.setSelection(i);
                            break;
                        }
                    }

                }
            });
            return convertView;
        }

        /**
         * 当右侧列表滚动时，左侧列表联动，显示背景色
         *
         * @param i
         */
        public void setBackgroud(int i) {

            currentPosition = i;

        }
    }

    static class ViewHolder {
        TextView room;
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }
    }


}
