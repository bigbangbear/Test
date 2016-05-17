package vstore.com.neteaseyx.ugallery.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private View mTitleBar;
    private ImageView mTitleBarBack;
    private TextView mTitleBarTitle;
    private void initNormalTitlebar() {

        mTitleBar = getLayoutInflater().inflate(R.layout.view_toolbar_title, null);//通用的一种标题栏布局，包含一个返回键、标题、最右两个按钮。
        mTitleBarBack = (ImageView) mTitleBar.findViewById(R.id.toolbar_back);
        mTitleBarTitle = (TextView) mTitleBar.findViewById(R.id.toolbar_title);

        mTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);//传入的布局，覆盖整个Toolbar
        mToolbar.addView(mTitleBar, lp);
    }

    /**
     * 添加自定义标题栏
     */
    public void setCustomTitleBar(View titlebarLayout) {
        mToolbar.removeAllViews();  // 先清除掉之前可能加入的

        Toolbar.LayoutParams lp = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT);//传入的布局，覆盖整个Toolbar
        mToolbar.addView(titlebarLayout, lp);
    }

    public void setTitle(String string){
        mTitleBarTitle.setText(string);
    }
}
