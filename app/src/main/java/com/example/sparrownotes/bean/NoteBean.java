package com.example.sparrownotes.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 笔记实体类
 * noteID       笔记ID 主键
 * title        标题
 * content      内容
 * category     类别
 * time         创建时间
 * imgResource  图片资源(int)
 * isTop        是否置顶
 *
 * @author Horsen
 */
@DatabaseTable(tableName = "tb_note")
public class NoteBean {

    @DatabaseField(generatedId = true, columnName = "note_id")
    private int noteID;

    @DatabaseField(columnName = "user_id")
    private int userID;

    @DatabaseField(defaultValue = "无标题")
    private String title;

    @DatabaseField(defaultValue = "空")
    private String content;

    @DatabaseField(defaultValue = "未分类")
    private String category;

    @DatabaseField(defaultValue = "未知")
    private String time;

    @DatabaseField(columnName = "img_resource")
    private int imgResource;

    @DatabaseField(defaultValue = "0", columnName = "is_top")
    private String isTop;

    public NoteBean() {
    }

    public NoteBean(int userID, String title, String content, String category, String time) {
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.category = category;
        this.time = time;
    }

    public NoteBean(int userID, String title, String content, String category, String time, int imgResource) {
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.category = category;
        this.time = time;
        this.imgResource = imgResource;
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "noteID=" + noteID +
                "userID=" + userID +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", time='" + time + '\'' +
                ", imgResource='" + imgResource + '\'' +
                ", isTop='" + isTop + '\'' +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTop() {
        return isTop;
    }

    public void setTop(String isTop) {
        this.isTop = isTop;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }
}
