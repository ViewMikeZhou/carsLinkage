package qc.hengda.com.myapplication.base;

import android.app.Application;

/**
 * Created by Administrator on 2017/3/30.
 */

public abstract class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    protected abstract void initConfig();
}
