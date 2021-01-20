package com.margin.snap.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.margin.snap.R;
import com.margin.snap.utils.testmethod.ClickTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by : luxj at 2021-1-20,13:43
 * Description:
 */
public class TestMethodUtils {

    public static void find(Activity activity, int rootView) {
        find(activity, (ViewGroup) LayoutInflater.from(activity).inflate(rootView, null));
    }

    public static void find(Activity activity, ViewGroup rootView) {
        if (activity != null) {
            Method[] methods = activity.getClass().getDeclaredMethods();
            Map<String, Method> methodMap = new HashMap<>();
            List<String> methodNames = new ArrayList<>();
            for (Method method : methods) {
                ClickTest annotation = method.getAnnotation(ClickTest.class);
                if (annotation != null) {
                    String name;
                    if ((name = annotation.value()).isEmpty()) {
                        name = method.getName();
                    }
                    methodMap.put(name, method);
                    methodNames.add(name);
                }
            }
            View inflate = LayoutInflater.from(activity).inflate(R.layout.layout_test_method, rootView, true);
            ListView listView = inflate.findViewById(R.id.methods_listview);
            listView.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, methodNames));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Method method = methodMap.get(methodNames.get(position));
                try {
                    method.invoke(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
