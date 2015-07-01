package com.testjob.app.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by dds on 01.07.15.
 */
public class MainListAdapter extends BaseAdapter {
    private static final int SECTIONS_COUNT = 3;

    private static final int MAIN_ARTICLE_POSITION = 0;
    private static final int SUB_ARTICLE_POSITION = 1;
    private static final int COMMENT_POSITION = 2;

    private static final int MAIN_ARTICLE_ID = 0;
    private static final int SUB_ARTICLE_ID = 1;
    private static final int COMMENT_ID = 2;


    @Override
    public int getCount() {
        return SECTIONS_COUNT;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        switch (position) {
            case MAIN_ARTICLE_POSITION:
                return MAIN_ARTICLE_ID;
            case SUB_ARTICLE_POSITION:
                return SUB_ARTICLE_ID;
            case COMMENT_POSITION:
                return COMMENT_ID;
        }
        return -1; //todo decide about this unreachable code and throwing exception
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
