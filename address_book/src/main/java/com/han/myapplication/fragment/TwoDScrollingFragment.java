package com.han.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.han.myapplication.R;
import com.han.myapplication.bean.Constant;
import com.han.myapplication.viewholder.IconTreeItemHolder;
import com.han.myapplication.viewholder.SelectableHeaderHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class TwoDScrollingFragment extends Fragment {
    private static final String NAME = "科室";
    private AndroidTreeView tView;
    private MainFragment main;
    //给根节点设置type_id
    private int root_id1 = 001;
    private int root_id2 = 002;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selectable_nodes, null, false);
        ViewGroup containerView = (ViewGroup) rootView.findViewById(R.id.container);
        main = (MainFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Constant.TAG_MAIN);

        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "西区 ", root_id1)).setViewHolder(new SelectableHeaderHolder(getActivity(), main));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "东区", root_id2)).setViewHolder(new SelectableHeaderHolder(getActivity(), main));

        fillFolder(s1);
        //fillFolder(s2);

        root.addChildren(s1, s2);

        tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        containerView.addView(tView.getView());

        //展开所有的treeview
        //tView.expandAll();

        //展开对应的treeview
        //tView.expandNode(s1.getChildren().get(0).getChildren().get(0));

        //默认展开第二级别treeview
        tView.expandLevel(1);

        /*if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }*/
        return rootView;
    }

    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 1; i < 8; i++) {
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, NAME + i, i)).setViewHolder(new SelectableHeaderHolder(getActivity(), main));
            currentNode.addChild(file);
            currentNode = file;
        }
    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", tView.getSaveState());
    }*/

    public void setExpandLevel(int level) {
        //先折叠所有的菜单，然后在展开
        tView.collapseAll();
        tView.expandLevel(level);
    }

}
