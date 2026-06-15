package com.apptest.game2048.models;

public class Comment {

    private Integer commentPersonPictureRes;
    private String commentPersonName;
    private String commentText;
    private String commentDate;

    public Comment(Integer commentPersonPictureRes, String commentPersonName, String commentText) {
        this.commentPersonPictureRes = commentPersonPictureRes;
        this.commentPersonName = commentPersonName;
        this.commentText = commentText;
    }

    public Integer getCommentPersonPictureRes() {
        return commentPersonPictureRes;
    }

    public void setCommentPersonPictureRes(Integer commentPersonPictureRes) {
        this.commentPersonPictureRes = commentPersonPictureRes;
    }

    public String getCommentPersonName() {
        return commentPersonName;
    }

    public void setCommentPersonName(String commentPersonName) {
        this.commentPersonName = commentPersonName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}