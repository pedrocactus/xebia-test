package com.xebiatest.boxotop.app.ui;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by pierrecastex on 19/09/2014.
 */
public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <E extends View> E get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (E) childView;
    }
}
