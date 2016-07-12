package com.example.pc.day_1listview.jiebanlvxing;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.android.volley.toolbox.Volley;
import com.example.pc.day_1listview.R;
import com.example.pc.day_1listview.remen.MyApp;
import com.example.pc.day_1listview.remen.Remen;
import com.example.pc.day_1listview.remen.Uris;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：朴奈Created by pc on 2016/7/10.
 */
public class RemenFragment extends android.support.v4.app.Fragment {
    // private ImageView iv_big_id;//图片
    private Activity activity;
    private ListView lv_id;//ListView
    private List<Map<String, Object>> dataResource;
    private List<Remen.DataBean> data;
    //  List<Remen.UserBean>user;
    private SimpleAdapter adapter;
    private RequestQueue mQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_remen, container, false);
        lv_id = (ListView) view.findViewById(R.id.lv_remen_id);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aboutListView();
    }

    private void aboutListView() {
        //数据源
        dataResource = new LinkedList<>();
        //适配器
        adapter = new SimpleAdapter(getContext(), dataResource, R.layout.layout_remen_item,
                new String[]{"user_name",
                        "user_time",
                        "content",
                        "adress",
                        "adressdate",
                        "picture",
                        "adressdingwei",
                        "guanzhu"},
                new int[]{R.id.name_id,
                        R.id.time_id,
                        R.id.content_id,
                        R.id.adress_id,
                        R.id.date_id,
                        R.id.iv_pic_id,
                        R.id.dingweiadress_id
                        , R.id.guanzhu_id
                });
        //实时显示图片
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                if (view instanceof ImageView && o instanceof Bitmap) {
                    ImageView iv = (ImageView) view;
                    Bitmap b = (Bitmap) o;
                    iv.setImageBitmap(b);
                    return true;
                }
                return false;
            }
        });
        //绑定适配器
        lv_id.setAdapter(adapter);
        //填充数据源
        fillDataResource();
        //添加监听器(点击跳转到详情Fragment界面）

    }

    private void fillDataResource() {
        mQueue = Volley.newRequestQueue(activity);

        final StringRequest request = new StringRequest(Uris.ACTIVITY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("===================TAG", "aaaaaaaaa      onResponse: " + response);
                        //Gson解析
                        Gson gson = new Gson();
                        Remen remen = gson.fromJson(response, Remen.class);
                        data = remen.getData();

                        //遍历
                        for (Remen.DataBean dbean : data) {
                            Map<String, Object> map = new LinkedHashMap<>();
                            List<Remen.DataBean.ResourceBean> resource = dbean.getResource();
                            getBitmap(dbean.getResource().get(0).getFile(), map);

//                             "user_name",
                            map.put("user_name", dbean.getUser().getName());
                            map.put("user_time", dbean.getFormat_ctime());
                            map.put("content", dbean.getContent());
                            map.put("adress", dbean.getAim_province_id());
                            map.put("adressdate", dbean.getFormat_start_time());
                            map.put("picture", dbean.getResource());
                            map.put("adressdingwei", dbean.getStart_city());
                            map.put("guanzhu", dbean.getAttenttion_nr());
                            //将Map实例添加到数据源中
                            dataResource.add(map);
                            adapter.notifyDataSetChanged();
                        }
//                        Log.e("######################:", remen + "");
                    }

                    private void getBitmap(final String path, final Map<String, Object> map) {
                        new Thread() {
                            InputStream inputStream = null;
                            HttpURLConnection conn = null;
                            URL url = null;

                            @Override
                            public void run() {
                                try {
                                    url = new URL(path);

                                    conn = (HttpURLConnection) url.openConnection();
                                    conn.setConnectTimeout(3000);
                                    conn.setRequestMethod("GET");
                                    if (conn.getResponseCode() == 200) {
                                        inputStream = conn.getInputStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                        map.put("picture", bitmap);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                super.run();
                            }
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Uris.ACTIVITY_URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("TAG","aaaaaaaaaaaa=================="+ response.toString());
//                        //Gson解析
//                        Gson gson = new Gson();
//                        Remen remen = gson.fromJson(response.toString(), Remen.class);
//                        data = remen.getData();
////
//                        //遍历
//                        for (Remen.DataBean dbean : data) {
//                            Map<String, Object> map = new LinkedHashMap<>();
//                            List<Remen.DataBean.ResourceBean> resource = dbean.getResource();
//                            getBitmap(dbean.getFace(),map);
//
//                           // "user_name",   map.put("user_time",dbean.getFormat_ctime());
//                            map.put("content", dbean.getContent());
//                            map.put("adress", dbean.getAim_province_id());
//                            map.put("adressdate", dbean.getFormat_start_time() );
//                            map.put("picture", dbean.getResource());
//                            map.put("adressdingwei", dbean.getStart_city());
//                            map.put("guanzhu", dbean.getAttenttion_nr());
//                            //将Map实例添加到数据源中
//                            dataResource.add(map);
//                            adapter.notifyDataSetChanged(); map.put("user_name", dbean.getUser().getName());
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("TAG", error.getMessage(), error);
//            }
//        });
        //添加到请求队列中
        request.setTag("qx");

        mQueue.add(request);
//        mQueue.add(jsonObjectRequest);
    }

    private void getBitmap(final String path, final Map<String, Object> map) {
        new Thread() {
            InputStream inputStream = null;
            HttpURLConnection conn = null;
            URL url = null;

            @Override
            public void run() {
                try {
                    url = new URL(path);

                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(3000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        inputStream = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        map.put("picture", bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
    }
}