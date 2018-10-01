package com.gsatechworld.gugrify.view.playlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PlayListModel implements Parcelable{
    String image;
    String news_headlines;
    String views;
    String likes;
    ArrayList<String> comments;
    ArrayList<String> texts;
    boolean isClicked;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    String post_id;

    public PlayListModel(String post_id, String image, String news_headlines, String views, String likes,
                         ArrayList<String> comments, ArrayList<String> texts, boolean isClicked) {

        this.post_id = post_id;
        this.image = image;
        this.news_headlines = news_headlines;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.texts = texts;
        this.isClicked = isClicked;
    }

    protected PlayListModel(Parcel in) {
        image = in.readString();
        news_headlines = in.readString();
        views = in.readString();
        likes = in.readString();
        comments = in.createStringArrayList();
        texts = in.createStringArrayList();
        isClicked = in.readByte() != 0;
    }

    public static final Creator<PlayListModel> CREATOR = new Creator<PlayListModel>() {
        @Override
        public PlayListModel createFromParcel(Parcel in) {
            return new PlayListModel(in);
        }

        @Override
        public PlayListModel[] newArray(int size) {
            return new PlayListModel[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getNews_headlines() {
        return news_headlines;
    }


    public String getViews() {
        return views;
    }

    public String getLikes() {
        return likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(news_headlines);
        parcel.writeString(views);
        parcel.writeString(likes);
        parcel.writeStringList(comments);
        parcel.writeStringList(texts);
        parcel.writeByte((byte) (isClicked ? 1 : 0));
    }
}
