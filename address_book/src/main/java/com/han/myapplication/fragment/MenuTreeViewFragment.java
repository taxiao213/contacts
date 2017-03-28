package com.han.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.han.myapplication.R;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by aaa on 2017/3/27.
 */

public class MenuTreeViewFragment extends Fragment {

    private ViewGroup containerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tree_group, null, false);
        containerView = (ViewGroup) rootView.findViewById(R.id.container);
        //初始化菜单
        initdata();
        return rootView;

    }

    private void initdata() {
        //初始化treeview
        TreeNode root = TreeNode.root();
        //设置多级菜单
        TreeNode parent = new TreeNode("MyParentNode");

        TreeNode child0 = new TreeNode("ChildNode0");
        TreeNode child1 = new TreeNode("ChildNode1");
        parent.addChildren(child0, child1);

        TreeNode parent1 = new TreeNode("MyParentNode1");
        TreeNode child01 = new TreeNode("ChildNode01");
        TreeNode child11 = new TreeNode("ChildNode11");

        parent1.addChildren(child01, child11);



        root.addChildren(parent,parent1);

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        //设置默认的动画和旋转效果
        tView.setDefaultAnimation(true);
        tView.setUse2dScroll(true);
        //将AndroidTreeView加到ViewGroup
        containerView.addView(tView.getView());

        //全部展开
        //tView.expandAll();



    }


}
