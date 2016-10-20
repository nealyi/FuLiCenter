package com.nealyi.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nealyi.app.R;
import com.nealyi.app.activity.MainActivity;
import com.nealyi.app.adapter.CategoryAdapter;
import com.nealyi.app.bean.CategoryChildBean;
import com.nealyi.app.bean.CategoryGroupBean;
import com.nealyi.app.net.NetDao;
import com.nealyi.app.net.OkHttpUtils;
import com.nealyi.app.utils.ConvertUtils;
import com.nealyi.app.utils.L;

import java.util.ArrayList;

/**
 * Created by nealyi on 16/10/20.
 */
public class CategoryFragment extends BaseFragment {


    @BindView(R.id.elv_category)
    ExpandableListView mElvCategory;

    MainActivity mContext;
    CategoryAdapter mAdapter;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    int index;
    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext, mGroupList, mChildList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {
        mElvCategory.setGroupIndicator(null);
        mElvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();
    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    groupCount = groupList.size();
                    L.e("GroupList:"+groupList.size());
                    for (int i = 0; i < groupCount; i++) {
                        downloadChild(groupList.get(i));
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error:"+error);
            }
        });
    }

    private void downloadChild(final CategoryGroupBean group) {
        NetDao.downloadCategoryChild(mContext, group.getId(), new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                if (result != null && result.length > 0) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    L.e("ChildList:"+childList.size());
                    mGroupList.add(index,group);
                    mChildList.add(index, childList);
                    index++;
                }
                if (index == groupCount) {
                    mAdapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }


    @Override
    protected void setListener() {

    }
}
