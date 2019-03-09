package com.hptpd.taskdispatcherserver.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public class PageUtil {

    public static <T> List<T> listToPage(int pageNo, int pageSize, List<T> list) {
        List<T> result = new ArrayList<T>();
        if (list != null && list.size() > 0) {
            int allCount = list.size();
            int pageCount = (allCount + pageSize - 1) / pageSize;
            if (pageNo >= pageCount) {
                pageNo = pageCount;
            }
            int start = (pageNo - 1) * pageSize;
            int end = pageNo * pageSize;
            if (end >= allCount) {
                end = allCount;
            }
            for (int i = start; i < end; i++) {
                result.add(list.get(i));
            }
        }
        return (result != null && result.size() > 0) ? result : null;
    }


}
