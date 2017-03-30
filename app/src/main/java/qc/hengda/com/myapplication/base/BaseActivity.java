package qc.hengda.com.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2017/3/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private SparseArray<View> mViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void processClick(View v); //处理点击时间

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    /**
     * 通过id找控键
     *
     * @param viewId
     * @param <E>
     * @return
     */
    public <E extends View> E find(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    /**
     * 设置点击事件
     *
     * @param v
     */
    public void setOnclick(View v) {
        v.setOnClickListener(this);
    }
}
