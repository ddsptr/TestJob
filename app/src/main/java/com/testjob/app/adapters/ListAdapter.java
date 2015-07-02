package com.testjob.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.testjob.app.DownloadImageTask;
import com.testjob.app.R;
import com.testjob.app.dto.Comment;
import com.testjob.app.dto.MainArticle;
import com.testjob.app.dto.SubArticle;

import java.util.ArrayList;

/**
 * Created by dds on 01.07.15.
 */
public class ListAdapter extends BaseAdapter {
    private static final int MAIN_ARTICLES_COUNT = 1;

    private static final int TYPE_UNDEFINED = -1;
    private static final int TYPE_MAIN_ARTICLE = 0;
    private static final int TYPE_SUB_ARTICLE = 1;
    private static final int TYPE_COMMENT = 2;

    private static final int TYPES_COUNT = 3;

    private MainArticle mMainArticle;
    private ArrayList<SubArticle> mSubArticle = new ArrayList<SubArticle>();
    private ArrayList<Comment> mComment = new ArrayList<Comment>();

    private LayoutInflater mInflater;
    private Context mContext;

    public ListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addArticle(MainArticle mainArticle) {
        mMainArticle = mainArticle;
        notifyDataSetChanged();
    }

    public void addSubArticle(SubArticle subArticle) {
        mSubArticle.add(subArticle);
        notifyDataSetChanged();
    }

    public void addComment(Comment comment) {
        mComment.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return MAIN_ARTICLES_COUNT + mSubArticle.size() + mComment.size();
    }

    @Override
    public Object getItem(int position) {
        switch (getTypeByPosition(position)) {
            case TYPE_MAIN_ARTICLE:
                return mMainArticle;
            case TYPE_SUB_ARTICLE:
                return mSubArticle.get(getSubArticleFromAbsolutePosition(position));
            case TYPE_COMMENT:
                return mComment.get(getCommentFromAbsolutePosition(position));
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        switch (getTypeByPosition(position)) {
            case TYPE_MAIN_ARTICLE:
                if (view == null) {
                    view = mInflater.inflate(R.layout.main_article, null);
                }
                if (mMainArticle != null) {
                    TextView tvMainArticleTitle = (TextView) view.findViewById(R.id.main_article_title);
                    tvMainArticleTitle.setText(mMainArticle.getTitle());
                    TextView tvMainArticleDescription = (TextView) view.findViewById(R.id.main_article_description);
                    tvMainArticleDescription.setText(mMainArticle.getDescription());
                }
                break;

            case TYPE_SUB_ARTICLE:
                if (view == null) {
                    view = mInflater.inflate(R.layout.sub_article, null);
                }
                if (mSubArticle.size() > 0) {
                    int subArticlePosition = getSubArticleFromAbsolutePosition(position);
                    ImageView ivSubArticlePicture = (ImageView) view.findViewById(R.id.sub_article_picture);
                    String imageSubArticleUrl = mSubArticle.get(subArticlePosition).getPicture();
                    new DownloadImageTask(ivSubArticlePicture).execute(imageSubArticleUrl);
                    TextView tvSubArticleTitle = (TextView) view.findViewById(R.id.sub_article_title);
                    tvSubArticleTitle.setText(mSubArticle.get(subArticlePosition).getTitle());
                    TextView tvSubArticleDescription = (TextView) view.findViewById(R.id.sub_article_description);
                    tvSubArticleDescription.setText(mSubArticle.get(subArticlePosition).getDescription());
                }
                break;

            case TYPE_COMMENT:
                if (view == null) {
                    view = mInflater.inflate(R.layout.comment, null);
                }
                if (mComment.size() > 0) {
                    int commentPosition = getCommentFromAbsolutePosition(position);
                    ImageView ivCommentAvatar = (ImageView) view.findViewById(R.id.comment_avatar);
                    String imageCommentAvatarUrl = mComment.get(commentPosition).getAvatar();
                    new DownloadImageTask(ivCommentAvatar).execute(imageCommentAvatarUrl);
                    TextView tvCommentUserName = (TextView) view.findViewById(R.id.comment_username);
                    tvCommentUserName.setText(mComment.get(commentPosition).getUserName());
                    TextView tvCommentText = (TextView) view.findViewById(R.id.comment_text);
                    tvCommentText.setText(mComment.get(commentPosition).getText());
                    TextView tvCommentDate = (TextView) view.findViewById(R.id.comment_date);
                    tvCommentDate.setText(mComment.get(commentPosition).getDate());
                }
                break;

        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeByPosition(position);
    }

    @Override
    public int getViewTypeCount() {
        return TYPES_COUNT;
    }

    private int getTypeByPosition(int position) {
        if (position < MAIN_ARTICLES_COUNT) {
            return TYPE_MAIN_ARTICLE;
        } else if (position < MAIN_ARTICLES_COUNT + mSubArticle.size()) {
            return TYPE_SUB_ARTICLE;
        } else if (position < MAIN_ARTICLES_COUNT + mSubArticle.size() + mComment.size()){
            return TYPE_COMMENT;
        } else {
            return TYPE_UNDEFINED;
        }
    }

    private int getCommentFromAbsolutePosition(int position) {
        return position - mSubArticle.size() - MAIN_ARTICLES_COUNT;
    }

    private int getSubArticleFromAbsolutePosition(int position) {
        return position - MAIN_ARTICLES_COUNT;
    }
}
