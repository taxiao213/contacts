package com.han.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.han.myapplication.R;
import com.han.myapplication.bean.Content;
import com.han.myapplication.bean.People;
import com.han.myapplication.bean.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aaa on 2017/3/27.
 */

public class MenuExpandFragment extends Fragment{

    @BindView(R.id.exlist)
    ExpandableListView exlist;
    private ArrayList<Room> mroom;
    //选中条目的索引值
    public int currentPosition = 0;
    private ArrayList<People> mpeople;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.menu_expand, null);
        ButterKnife.bind(this, view);
        initdata();
        return view;

    }


    private void initdata() {
        Content content = new Content();
        mroom = content.getRoom();
        mpeople = content.getPeople();

        //为一级条目提供数据
        //Groups  分组,其中定义了17个组，可以直接在这里增加组的名字，功能已测试
        List<Map<String, Object>> Group = new ArrayList<>();
        String[] str = {"河东区", "河西区"};

        for (int i = 0; i < str.length; i++) {
            Map<String, Object> group = new HashMap<>();
            group.put("GroupName", str[i]);
            Group.add(group);
        }

        //定义两个List<Map<String,Object>>  child1和child2
        //为二级条目提供数据
        //child1
        List<List<Map<String, Object>>> Childs = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            List<Map<String, Object>> child1 = new ArrayList<>();

            for (int j = 0; j < mroom.size(); j++) {
                Map<String, Object> child2 = new HashMap<>();
                child2.put("ChildName", mroom.get(j).getName());
                child1.add(child2);
            }

            Childs.add(child1);
        }
        /**
         * 使用SimpleExpandableListAdapter显示ExpandableListView
         * 参数1.上下文对象Context
         * 参数2.一级条目目录集合
         * 参数3.一级条目对应的布局文件
         * 参数4.fromto，就是map中的key，指定要显示的对象
         * 参数5.与参数4对应，指定要显示在groups中的id
         * 参数6.二级条目目录集合
         * 参数7.二级条目对应的布局文件
         * 参数8.fromto，就是map中的key，指定要显示的对象
         * 参数9.与参数8对应，指定要显示在childs中的id
         */
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(getActivity(),
                Group, R.layout.group, new String[]{"GroupName"}, new int[]{R.id.group},
                Childs, R.layout.child, new String[]{"ChildName"}, new int[]{R.id.child});

        exlist.setAdapter(adapter);


    }


}
