package com.supercwn.player.model;


import android.support.v4.util.ArrayMap;

import com.supercwn.player.SuperApplication;

import java.util.ArrayList;
import java.util.Set;

public class SCChannelFilter {

    private ArrayMap<String,ArrayList<SCChannelFilterItem>> filters;

    public SCChannelFilter(ArrayMap<String, ArrayList<SCChannelFilterItem>> filters) {
        this.filters = filters;
    }
    public SCChannelFilter() {
        filters = new ArrayMap<>();
    }

    public void addFilter(String key, ArrayList<SCChannelFilterItem> items) {
        filters.put(key,items);
    }

    public Set<String> getFilterKeys()  {
        return filters.keySet();
    }

    public ArrayList<SCChannelFilterItem> getFilterItemsByKey(String key ) {
        return filters.get(key);
    }

    @Override
    public String toString() {
        return "SCChannelFilter{" +
                "filters=" + filters +
                '}';
    }

    public void selectFilterItem(SCChannelFilterItem item) {
        String key = item.getParentKey();
        ArrayList<SCChannelFilterItem> items = getFilterItemsByKey(key);
        for(SCChannelFilterItem i : items) {
            i.setChecked(false);
        }
        item.setChecked(true);
    }

    public ArrayList<SCChannelFilterItem> getSelectedItems() {
        Set<String> keys = getFilterKeys();
        ArrayList<SCChannelFilterItem> items = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.toArray()[i];
            ArrayList<SCChannelFilterItem> is = getFilterItemsByKey(key);
            for(SCChannelFilterItem it : is) {
                if(it.isChecked())
                    items.add(it);
            }
        }
        if(items.size() > 0) {
            return items;
        }
        return null;
    }

    public String toJson() {
        String ret = SuperApplication.getGson().toJson(this);
        return ret;
    }

    public static SCChannelFilter fromJson(String json) {
        SCChannelFilter filter  = SuperApplication.getGson().fromJson(json,SCChannelFilter.class);
        return filter;
    }


}
