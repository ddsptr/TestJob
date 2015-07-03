package com.testjob.app.adapters;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.testjob.app.DownloadImageTask;
import com.testjob.app.ImageCache;
import com.testjob.app.R;
import com.testjob.app.Utils;
import com.testjob.app.dto.Comment;
import com.testjob.app.dto.MainArticle;
import com.testjob.app.dto.SubArticle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
    private LinkedList<Comment> mComment = new LinkedList<Comment>();

    private LayoutInflater mInflater;
    private Context mContext;
    private ImageCache mImageCache;
    private Bitmap mAvatarNotAvailable;
    private Bitmap mImageNotAvailable;

    public ListAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int memClass = ((ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;
        mImageCache = new ImageCache(cacheSize);
        mAvatarNotAvailable =
                Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.default_avatar, 50, 50);
        mImageNotAvailable =
                Utils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.image_not_available, 100, 80);
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
        int parentId = comment.getParentId();
        if (parentId != 0) {
            try {
                mComment.add(getCommentPosition(parentId) + 1, comment);
            } catch (IndexOutOfBoundsException e) {
                mComment.add(comment);
            }
        } else {
            mComment.add(comment);
        }
        notifyDataSetChanged();
    }

    public String getTitle() {
        return mMainArticle.getTitle();
    }

    public int getCommentStartPosition() {
        return MAIN_ARTICLES_COUNT + mSubArticle.size();
    }

    public int getMaxCommentId() {
        int maxId = 0;
        for (Comment comment : mComment) {
            if (comment.getId() > maxId) {
                maxId = comment.getId();
            }
        }
        return maxId;
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
                    inflateImage(ivSubArticlePicture, imageSubArticleUrl, mImageNotAvailable);
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
                    inflateImage(ivCommentAvatar, imageCommentAvatarUrl, mAvatarNotAvailable);
                    TextView tvCommentUserName = (TextView) view.findViewById(R.id.comment_username);
                    tvCommentUserName.setText(mComment.get(commentPosition).getUserName());
                    TextView tvCommentText = (TextView) view.findViewById(R.id.comment_text);
                    tvCommentText.setText(mComment.get(commentPosition).getText());
                    TextView tvCommentDate = (TextView) view.findViewById(R.id.comment_date);
                    tvCommentDate.setText(mComment.get(commentPosition).getDate());
                    if (mComment.get(commentPosition).getParentId() != 0) {
                        view.setPadding(view.getPaddingLeft() + ivCommentAvatar.getLayoutParams().width / 2,
                                view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
                    }
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
        } else if (position < MAIN_ARTICLES_COUNT + mSubArticle.size() + mComment.size()) {
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

    private void inflateImage(ImageView imageView, String url, Bitmap notAvailable) {
        Bitmap image = mImageCache.get(url);
        if (image != null) {
            imageView.setImageBitmap(image);
        } else {
            new DownloadImageTask(imageView, mImageCache, notAvailable).execute(url);
        }
    }

    private int getCommentPosition(int id)
        throws IndexOutOfBoundsException {
        for (int i = 0; i < mComment.size(); i++) {
            if (mComment.get(i).getId() == id) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
