package com.han.myapplication.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.han.myapplication.R;
import com.han.myapplication.activity.MainActivity;
import com.han.myapplication.bean.Constant;
import com.han.myapplication.bean.Content;
import com.han.myapplication.bean.People;
import com.han.myapplication.fragment.MainFragment;
import com.han.myapplication.fragment.TwoDScrollingFragment;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */
public class SelectableHeaderHolder extends TreeNode.BaseNodeViewHolder<IconTreeItemHolder.IconTreeItem> {
    private TextView tvValue;
    private PrintView arrowView;
    private CheckBox nodeSelector;
    //调用MainFragment的方法
    private MainFragment main;
    private final ArrayList<People> mpeople;
    //通过fragment获取MainActivity
    MainActivity activity;
    private final TwoDScrollingFragment menu;

    public SelectableHeaderHolder(Context context, MainFragment main) {
        super(context);
        this.main = main;
        Content content = new Content();
        mpeople = content.getPeople();
        activity = (MainActivity) main.getActivity();
        menu = (TwoDScrollingFragment) activity.getSupportFragmentManager().findFragmentByTag(Constant.TAG_MENU);
    }


    @Override
    public View createNodeView(final TreeNode node, final IconTreeItemHolder.IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_header, null, false);

        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value.text);

        arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
        if (node.isLeaf()) {
            arrowView.setVisibility(View.GONE);
        }

        //给条目设置点击事件
        tvValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tvValue.setBackground(context.getResources().getDrawable(R.drawable.search_selector));
                //tvValue.setTextColor(context.getResources().getColor(R.color.menu_text_selector));

                // TODO: 2017/3/28
                for (int i = 0; i < mpeople.size(); i++) {
                    int people_TypeID = mpeople.get(i).getTypeID();
                    if (people_TypeID == value.type_id) {
                        //折叠菜单栏
                        Log.e("value.type_id",value.type_id+"");
                        menu.setExpandLevel(value.type_id);

                        //点击条目关闭侧滑菜单
                        activity.drawer.closeDrawers();
                        //展开对应的listview
                        main.shl.setSelection(i);
                        break;
                    }
                }
            }
        });

        return view;
    }


    @Override
    public void toggle(boolean active) {
        arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }
}
