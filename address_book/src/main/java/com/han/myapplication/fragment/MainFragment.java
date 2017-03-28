package com.han.myapplication.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.han.myapplication.R;
import com.han.myapplication.bean.Constant;
import com.han.myapplication.bean.Content;
import com.han.myapplication.bean.People;
import com.han.myapplication.bean.Room;
import com.han.myapplication.utils.PinYinUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by aaa on 2017/3/24.
 */

public class MainFragment extends Fragment {

    @BindView(R.id.mess_search)
    TextView messSearch;

   /* @BindView(R.id.mess_slhlv)
    StickyListHeadersListView shl;*/


    private ArrayList<People> mPeople;
    private ArrayList<Room> mroom;
    //标签记录 侧滑菜单打开或者关闭的状态
    public boolean isMain = false;
    private PopupWindow mpopupWindow;
    private EditText etSearch;
    public StickyListHeadersListView shl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.main, null);
        shl = (StickyListHeadersListView) view.findViewById(R.id.mess_slhlv);
        ButterKnife.bind(this, view);
        initdata();
        return view;

    }


    /**
     * 填充数据
     */
    private void initdata() {
        Content content = new Content();
        mPeople = content.getPeople();
        mroom = content.getRoom();

        //右侧列表的数据适配器
        StickyListHeadersAdapter myStick = new sticklistview();
        shl.setAdapter(myStick);

        shl.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //MenuFragment menu = (MenuFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);

                // TODO: 2017/3/27 修改为expandlistview
                //MenuExpandFragment menu = (MenuExpandFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);

                //MenuTreeViewFragment menu = (MenuTreeViewFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);
                //TwoDScrollingFragment menu = (TwoDScrollingFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);


                //listview数据适配器滚动  smoothToPosition
                int stickpeople_typeID = mPeople.get(firstVisibleItem).getTypeID();

                /*for (int i = 0; i < mroom.size(); i++) {
                    //当typeID一样时，滚动
                    int stickroom_typeID = mroom.get(i).getTypeID();

                    if (stickpeople_typeID == stickroom_typeID) {
                        //listview设置滑动
                        menu.ll.smoothScrollToPosition(i);

                        //给adapter设置背景色，
                        //当右侧列表滚动时，左侧列表联动，显示背景色
                        menu.adapter.setBackgroud(i);
                        menu.adapter.notifyDataSetChanged();
                        break;
                    }
                }*/

                //通过标签获取对应的fragment
                TwoDScrollingFragment menu = (TwoDScrollingFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);
                for (int i = 0; i < mroom.size(); i++) {
                    //当typeID一样时，滚动
                    int stickroom_typeID = mroom.get(i).getTypeID();

                    if (stickpeople_typeID == stickroom_typeID) {
                        //标签记录 侧滑菜单打开或者关闭的状态
                        if (isMain) {
                            //listview设置滑动
                            menu.setExpandLevel(i + 1);
                            //给adapter设置背景色，
                            //当右侧列表滚动时，左侧列表联动，显示背景色
                        }
                        break;
                    }
                }


            }
        });

    }

    @OnClick(R.id.mess_search)
    public void onClick() {
        messSearch.setBackground(getResources().getDrawable(R.drawable.search_selector));

        //弹出一个popupwindow
        showPopupWindow();


    }

    /**
     * 显示弹出一个popupwindow
     */
    private void showPopupWindow() {
        View popView = View.inflate(getActivity(), R.layout.search, null);
        etSearch = (EditText) popView.findViewById(R.id.et_search);

        if (mpopupWindow == null) {
            mpopupWindow = new PopupWindow(popView, messSearch.getWidth(), messSearch.getHeight(), true);

            //点击popupwindow外部消失
            mpopupWindow.setOutsideTouchable(true);
            mpopupWindow.setBackgroundDrawable(new ColorDrawable());
            mpopupWindow.setBackgroundDrawable(new ColorDrawable());

        }

        //展示popuowindow  参数1 在哪个view下面  轴偏移量
        mpopupWindow.showAsDropDown(messSearch, 0, -messSearch.getHeight());

        searchItem();

    }

    /**
     * 搜索显示相应的条目
     */
    private void searchItem() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = s.toString();

                if (text.length() > 0) {

                    for (int i = 0; i < mPeople.size(); i++) {
                        String number = mPeople.get(i).getNumber();
                        String name = mPeople.get(i).getName();
                        //正则匹配数字，以0-9开头的数字，^开头，$结尾
                        if (text.matches("^([0-9]).*")) {
                            if (number.contains(text)) {
                                //sticklistheadview滚动
                                shl.setSelection(i);
                                break;
                            }
                        } else {
                            //将输入的汉字转换为字母
                            String input = PinYinUtils.getPinYin(text);
                            String input_HeadChar = PinYinUtils.getPinYinHeadChar(text);

                            Log.e("input", input);
                            Log.e("input_HeadChar", input_HeadChar);
                            //匹配字母,获取全拼
                            String quanpin = PinYinUtils.getPinYin(name);
                            String pinYinHeadChar = PinYinUtils.getPinYinHeadChar(name);
                            if (quanpin.contains(input) || pinYinHeadChar.contains(input_HeadChar)) {
                                //sticklistheadview滚动
                                shl.setSelection(i);
                                break;
                            }
                        }

                    }

                } else {
                    shl.setSelection(0);
                }


            }
        });
    }


    public class sticklistview extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(getActivity(), R.layout.main_type_header, null);
            textView.setText(mPeople.get(position).getRoom());
            return textView;

        }

        @Override
        public long getHeaderId(int position) {
            return mPeople.get(position).getTypeID();
        }

        @Override
        public int getCount() {
            return mPeople.size();
        }

        @Override
        public People getItem(int position) {
            return mPeople.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder holder;
            if (convertView == null) {
                holder = new MyViewHolder();
                convertView = View.inflate(getActivity(), R.layout.main_content, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.number = (ImageView) convertView.findViewById(R.id.tv_number);
                holder.message = (ImageView) convertView.findViewById(R.id.tv_message);
                convertView.setTag(holder);

            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            String name = getItem(position).getName();
            String number = getItem(position).getNumber();
            holder.name.setText(name + "    " + number);

            holder.number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //打电话
                    //跳转打电话
                    String number = mPeople.get(position).getNumber();
                    // 意图将数据返回
                    Intent data = new Intent();
                    data.setAction(Intent.ACTION_CALL);
                    data.setData(Uri.parse("tel:" + number));
                    startActivity(data);

                }
            });

            holder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转发送短信
                    Uri uri = Uri.parse("smsto:" + mPeople.get(position).getNumber());
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    startActivity(intent);

                }
            });
            return convertView;

        }


    }

    static class MyViewHolder {
        TextView name;
        ImageView number;
        ImageView message;

    }
}
