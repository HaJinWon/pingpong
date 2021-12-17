package com.douzone.pingpong.domain.comment;

public class Comment2 {

    private Long comment_id;
    private String contents;
    private String date;
    private Long member_id;
    private Long post_id;

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "Comment2{" +
                "comment_id=" + comment_id +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", member_id=" + member_id +
                ", post_id=" + post_id +
                '}';
    }
}
