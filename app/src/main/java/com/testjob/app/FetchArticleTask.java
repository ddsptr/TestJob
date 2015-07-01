package com.testjob.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.testjob.app.adapters.ListAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dds on 01.07.15.
 */
public class FetchArticleTask extends AsyncTask<Void, Void, Void> {
    private final String LOG_TAG = FetchArticleTask.class.getSimpleName();

    private Context mContext;
    private ListAdapter mAdapter;

    public FetchArticleTask(Context context, ListAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    protected Void doInBackground(Void... params) {
        try {
            getArticleDataFromJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getArticleDataFromJson()
            throws JSONException {

        final String ARTICLE = "article";
        final String PICTURES = "pictures";
        final String TITLE = "title";
        final String DESCRIPTION = "description";
        final String SUBARTICLES = "subarticles";
        final String COMMENTS = "comments";
        final String PICTURE = "picture";
        final String AVATAR = "avatar";
        final String USERNAME = "username";
        final String TEXT = "text";
        final String DATE = "date";

        try {
            InputStream stream = mContext.getResources().openRawResource(R.raw.json);
            String jsonString = stream.toString();
            stream.close();

            JSONObject articleJson = new JSONObject(jsonString);
            JSONArray articlePicturesJsonArray = articleJson.getJSONArray(PICTURES);
            for (int i = 0; i < articlePicturesJsonArray.length(); i++) {
                String picture = articlePicturesJsonArray.getString(i);
            }

            JSONObject articleTitleJson = articleJson.getJSONObject(TITLE);
            JSONObject articleDescriptionJson = articleJson.getJSONObject(DESCRIPTION);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
