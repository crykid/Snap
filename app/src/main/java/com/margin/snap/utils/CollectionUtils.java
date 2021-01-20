package com.margin.snap.utils;

import java.util.List;

/**
 * Created by : luxj at 2021-1-18,15:14
 * Description:
 */
public class CollectionUtils {

    public static <E> void listAdd(List<E> list, E data) {
        if (list != null && data != null && !list.contains(data)) {
            list.add(data);
        }
    }

    public static <E> void listRemove(List<E> list, E data) {
        if (list != null && data != null) {
            list.remove(data);
        }
    }
}
