package com.gsatechworld.gugrify.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostDetailPojo {

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("response")
        @Expose
        private String response;
        @SerializedName("selection")
        @Expose
        private String selection;
        @SerializedName("post_id")
        @Expose
        private String postId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("news_headline")
        @Expose
        private String newsHeadline;
        @SerializedName("news_title")
        @Expose
        private String newsTitle;
        @SerializedName("news_brief")
        @Expose
        private String newsBrief;
        @SerializedName("news_description")
        @Expose
        private String newsDescription;
        @SerializedName("youtube_link")
        @Expose
        private String youtubeLink;
        @SerializedName("reporter_location")
        @Expose
        private String reporterLocation;
        @SerializedName("time_of_post")
        @Expose
        private String timeOfPost;
        @SerializedName("published_date")
        @Expose
        private String publishedDate;
        @SerializedName("reporter_name")
        @Expose
        private String reporterName;
        @SerializedName("reporter_pic")
        @Expose
        private String reporterPic;
        @SerializedName("comments")
        @Expose
        private List<String> comments = null;
        @SerializedName("likes")
        @Expose
        private String likes;
        @SerializedName("views")
        @Expose
        private String views;
        @SerializedName("image_array")
        @Expose
        private List<String> imageArray = null;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public String getSelection() {
            return selection;
        }

        public void setSelection(String selection) {
            this.selection = selection;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNewsHeadline() {
            return newsHeadline;
        }

        public void setNewsHeadline(String newsHeadline) {
            this.newsHeadline = newsHeadline;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getNewsBrief() {
            return newsBrief;
        }

        public void setNewsBrief(String newsBrief) {
            this.newsBrief = newsBrief;
        }

        public String getNewsDescription() {
            return newsDescription;
        }

        public void setNewsDescription(String newsDescription) {
            this.newsDescription = newsDescription;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
        }

        public String getReporterLocation() {
            return reporterLocation;
        }

        public void setReporterLocation(String reporterLocation) {
            this.reporterLocation = reporterLocation;
        }

        public String getTimeOfPost() {
            return timeOfPost;
        }

        public void setTimeOfPost(String timeOfPost) {
            this.timeOfPost = timeOfPost;
        }

        public String getPublishedDate() {
            return publishedDate;
        }

        public void setPublishedDate(String publishedDate) {
            this.publishedDate = publishedDate;
        }

        public String getReporterName() {
            return reporterName;
        }

        public void setReporterName(String reporterName) {
            this.reporterName = reporterName;
        }

        public String getReporterPic() {
            return reporterPic;
        }

        public void setReporterPic(String reporterPic) {
            this.reporterPic = reporterPic;
        }

        public List<String> getComments() {
            return comments;
        }

        public void setComments(List<String> comments) {
            this.comments = comments;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

        public List<String> getImageArray() {
            return imageArray;
        }

        public void setImageArray(List<String> imageArray) {
            this.imageArray = imageArray;
        }

    }

}
