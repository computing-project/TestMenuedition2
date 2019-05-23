package com.example.testmenu_edition_2;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;


public class MainActivity extends Activity implements OnGroupExpandListener,
        ParentAdapter.OnChildTreeViewClickListener {

    ArrayList<Criteria> criteriaList;
    private Context mContext;

    private ExpandableListView eList;

    private ArrayList<ParentEntity> parents;

    private ParentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_main);

        loadData();

        initEList();

    }

    /**
     *         初始化菜单数据源
     * */
    private void loadData() {

        parents = new ArrayList<ParentEntity>();
        criteriaList = DefaultCriteriaList.getDefaultCriteriaList();

        for (int i = 0; i < criteriaList.get(0).getSubsectionList().size(); i++) {

            ParentEntity parent = new ParentEntity();

            parent.setGroupName(criteriaList.get(0).getSubsectionList().get(i).getName());

            parent.setGroupColor(getResources().getColor(
                    android.R.color.black));

            ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();

            for (int j = 0; j < criteriaList.get(0).getSubsectionList().get(i).getShortTextList().size(); j++) {

                ChildEntity child = new ChildEntity();

                child.setGroupName(criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(j).getName());

                child.setGroupColor(Color.parseColor("#000000"));

                ArrayList<String> childNames = new ArrayList<String>();

                ArrayList<Integer> childColors = new ArrayList<Integer>();

                for (int k = 0; k < criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(j).getLongtext().size(); k++) {

                    childNames.add(criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(j).getLongtext().get(k));

                    childColors.add(Color.parseColor("#000000"));

                }

                child.setChildNames(childNames);

                childs.add(child);

            }

            parent.setChilds(childs);

            parents.add(parent);

        }
    }

    /**
     *         初始化ExpandableListView
     * */
    private void initEList() {

        eList = (ExpandableListView) findViewById(R.id.eList);

        eList.setOnGroupExpandListener(this);

        adapter = new ParentAdapter(mContext, parents);

        eList.setAdapter(adapter);

        adapter.setOnChildTreeViewClickListener(this);

    }

    /**
     *         点击子ExpandableListView的子项时，回调本方法，根据下标获取值来做相应的操作
     * */
    @Override
    public void onClickPosition(int parentPosition, int groupPosition,
                                int childPosition) {
        // do something
        String childName = parents.get(parentPosition).getChilds()
                .get(groupPosition).getChildNames().get(childPosition)
                .toString();
        Toast.makeText(
                mContext,
                "点击的下标为： parentPosition=" + parentPosition
                        + "   groupPosition=" + groupPosition
                        + "   childPosition=" + childPosition + "\n点击的是："
                        + childName, Toast.LENGTH_SHORT).show();
    }

    /**
     *         展开一项，关闭其他项，保证每次只能展开一项
     * */
    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < parents.size(); i++) {
            if (i != groupPosition) {
                eList.collapseGroup(i);
            }
        }
    }

}
