package com.khumu.android.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khumu.android.data.SimpleUser;
import com.khumu.android.data.Tag;

public class Article implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("board")
    @Expose
    private String boardName;
    @SerializedName("board_display_name")
    @Expose
    private String boardDisplayName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private SimpleUser author;
    @SerializedName("is_author")
    @Expose
    private boolean isAuthor;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("images")
    @Expose
    private List<String> images;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("liked")
    @Expose
    private Boolean liked;
    @SerializedName("like_article_count")
    @Expose
    private Integer likeArticleCount;
    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;
    @SerializedName("bookmark_article_count")
    @Expose
    private Integer bookmarkArticleCount;

    public Article(){
        this.tags = new ArrayList<>(); // 이게 없으면 기본 생성자로 Deserialization 될 때 tags가 Null이 됨.
        this.images = new ArrayList<>(); //Image file name들을 담는 List
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDisplayName() {
        return boardDisplayName;
    }

    public void setBoardDisplayName(String boardDisplayName) {
        this.boardDisplayName = boardDisplayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SimpleUser getAuthor() {
        return author;
    }

    public void setAuthor(SimpleUser author) {
        this.author = author;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Integer getLikeArticleCount() {
        return likeArticleCount;
    }

    public void setLikeArticleCount(Integer likeArticleCount) {
        this.likeArticleCount = likeArticleCount;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Integer getBookmarkArticleCount() {
        return bookmarkArticleCount;
    }

    public void setBookmarkArticleCount(Integer bookmarkArticleCount) {
        this.bookmarkArticleCount = bookmarkArticleCount;
    }

    public boolean getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }
}
