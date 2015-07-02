package com.testjob.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.testjob.app.adapters.ListAdapter;
import com.testjob.app.adapters.PictureSliderAdapter;
import com.testjob.app.dto.Comment;
import com.testjob.app.dto.MainArticle;
import com.testjob.app.dto.SubArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dds on 01.07.15.
 */
public class FetchArticleTask extends AsyncTask<Void, Object, Void> {
    private final String LOG_TAG = FetchArticleTask.class.getSimpleName();

    private Context mContext;
    private ListAdapter mListAdapter;
    private PictureSliderAdapter mPictureSliderAdapter;

    public FetchArticleTask(Context context, ListAdapter listAdapter, PictureSliderAdapter pictureSliderAdapter) {
        mContext = context;
        mListAdapter = listAdapter;
        mPictureSliderAdapter = pictureSliderAdapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            getArticleDataFromJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... objects) {
        if (objects[0] instanceof MainArticle) {
            mListAdapter.addArticle((MainArticle) objects[0]);
        } else if (objects[0] instanceof SubArticle) {
            mListAdapter.addSubArticle((SubArticle) objects[0]);
        } else if (objects[0] instanceof Comment) {
            mListAdapter.addComment((Comment) objects[0]);
        } else if (objects[0] instanceof String) {
            mPictureSliderAdapter.addPicture((String) objects[0]);
        }
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
            String jsonString = getStringFromInputStream(stream);
            stream.close();

            JSONObject rootElementJson = new JSONObject(jsonString);
            JSONObject articleJson = rootElementJson.getJSONObject(ARTICLE);
            JSONArray articlePicturesJsonArray = articleJson.getJSONArray(PICTURES);
            for (int i = 0; i < articlePicturesJsonArray.length(); i++) {
                String picture = articlePicturesJsonArray.getString(i);
                publishProgress(picture);
            }

            String articleTitleJson = articleJson.getString(TITLE);
            String articleDescriptionJson = articleJson.getString(DESCRIPTION);
            publishProgress(new MainArticle(articleTitleJson, articleDescriptionJson));

            JSONArray subArticlesJsonArray = rootElementJson.getJSONArray(SUBARTICLES);
            for (int i = 0; i < subArticlesJsonArray.length(); i++) {
                JSONObject subArticleJson = subArticlesJsonArray.getJSONObject(i);
                String subArticlePicture = subArticleJson.getString(PICTURE);
                String subArticleTitle = subArticleJson.getString(TITLE);
                String subArticleDescription = subArticleJson.getString(DESCRIPTION);
                publishProgress(new SubArticle(subArticleTitle, subArticleDescription, subArticlePicture));
            }

            JSONArray commentJsonArray = rootElementJson.getJSONArray(COMMENTS);
            for (int i = 0; i < commentJsonArray.length(); i++) {
                JSONObject commentJson = commentJsonArray.getJSONObject(i);
                String commentAvatar = commentJson.getString(AVATAR);
                String commentUserName = commentJson.getString(USERNAME);
                String commentText = commentJson.getString(TEXT);
                long dateTime = commentJson.getLong(DATE);
                String commentDate = getReadableDateString(dateTime);
                publishProgress(new Comment(commentUserName, commentText, commentDate, commentAvatar));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private String getReadableDateString(long time){
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date).toString();
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
