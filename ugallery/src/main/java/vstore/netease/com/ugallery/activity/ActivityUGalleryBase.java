package vstore.netease.com.ugallery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import vstore.netease.com.ugallery.R;

/**
 * @author yuhuibin
 * @date 2016-05-11
 */
public class ActivityUGalleryBase extends AppCompatActivity{

    private LinearLayout mRoot;
    private FrameLayout mContentContainer;
    private View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRoot = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_gallery_base, null);
        mContentContainer = (FrameLayout) mRoot.findViewById(R.id.content_container);

        super.setContentView(mRoot);
    }

    /**
     * 将子类布局就加载到带有toolbar的父类布局中
     */
    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    private Toolbar mToolbar;
    private View mToolBarLine;
    @Override
    public void setContentView(View view) {
        mContentView = view;
        mContentContainer.addView(mContentView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarLine = findViewById(R.id.toolbar_line);

        initNormalTitlebar();
    }
    private void initNormalTitlebar() {

//        mTitleBar = getLayoutInflater().inflate(R.layout.view_toolbar_title, null);//通用的一种标题栏布局，包含一个返回键、标题、最右两个按钮。
//        mTitleBarBack = (ImageView) mTitleBar.findViewById(R.id.toolbar_back);
//        mTitleBarTitle = (TextView) mTitleBar.findViewById(R.id.toolbar_title);
//        mTitleBarLeftButton = (TextView) mTitleBar.findViewById(R.id.toolbar_left_button);
//        mTitleBarRightButton = (TextView) mTitleBar.findViewById(R.id.toolbar_right_button);//目前最右的这个设为购物车
//
//        mTitleBarBack.setOnClickListener(mClickBack);
    }
}
