package qc.hengda.com.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final int SUCCESS = 1;
    private static final int FAIL = 2;
    List<String> fatherName;
    List<String> nameList;
    List<List<String>> childName;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    fatherName = new ArrayList<>();
                    childName = new ArrayList<>();
                    Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    for (CarInfo carInfo : mCarInfos) {
                        List<CarInfo.ChildrenBeanX> children = carInfo.children;
                        for (CarInfo.ChildrenBeanX child : children) {
                            List<CarInfo.ChildrenBean> children1 = child.children;
                            String name = child.name;
                            fatherName.add(name);
                            nameList = new ArrayList<>();
                            for (CarInfo.ChildrenBean childrenBean : children1) {
                                nameList.add(childrenBean.name);
                                Log.d("MainActivity", childrenBean.name);
                            }
                            childName.add(nameList);
                        }
                    }
                    mRl.setVisibility(View.GONE);
                    break;
                case FAIL:
                    mRl.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private List<CarInfo> mCarInfos;
    private RelativeLayout mRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRl = (RelativeLayout) findViewById(R.id.rl);
    }

    public void get(final View view) {
        mRl.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url("http://data.auto.china.com/js/catalogPinyin.js").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                view.setClickable(false);
                mCarInfos = new ArrayList<CarInfo>();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        try {
                            String str = response.body().string().toString();
                            String json = getJson(str);
                            JSONArray jsonArray = JSON.parseArray(json);
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JSONArray bigBrands = jsonArray.getJSONObject(i).getJSONArray("bigBrands");
                                for (int i1 = 0; i1 < bigBrands.size(); i1++) {
                                    JSONObject jsonObject = bigBrands.getJSONObject(i1);
                                    CarInfo carInfo = JSONObject.toJavaObject(jsonObject, CarInfo.class);
                                    mCarInfos.add(carInfo);
                                }
                            }
                            Log.e("MainAC", "SUCCESS");
                            mHandler.sendEmptyMessage(SUCCESS);

                        } catch (Exception e) {
                            Log.e("MainAC", "FAIL");
                            mHandler.sendEmptyMessage(FAIL);
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    public String getJson(String str) {
        int i = str.indexOf("=");
        String substring = str.substring(i + 1, str.length());
        return substring;
    }

    public void show(View view) {
        if (fatherName != null && childName != null) {
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置

                    Toast.makeText(MainActivity.this, "您选择了" + fatherName.get(options1) + "--" + childName.get(options1).get(option2) , Toast.LENGTH_SHORT).show();
                }
            }).build();

            pvOptions.setPicker(fatherName, childName);
            pvOptions.show();
        }
    }
}
