package edu.buaa.beibeismart.Bean;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class EnglishTopicBean {
    int id;
    int version;
    int deleted;
    long createTime;
    long updateTime;
    String content;
    int size;

    @Override
    public String toString() {
        return "EnglishTopicBean{" +
                "id=" + id +
                ", version=" + version +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", size=" + size +
                ", englishVoicePath='" + englishVoicePath + '\'' +
                ", chineseVoicePath='" + chineseVoicePath + '\'' +
                ", img1Path='" + img1Path + '\'' +
                '}';
    }

    String englishVoicePath;
    String chineseVoicePath;
    String img1Path;

    public EnglishTopicBean(JSONObject object){

        try {
            //JSONObject object = new JSONObject(jsonString);
            setId(object.getInt("id"));
            setVersion(object.getInt("version"));
            setDeleted(object.getInt("deleted"));
            setCreateTime(object.getLong("createTime"));
            setUpdateTime(object.getLong("updateTime"));
            setContent(object.getString("content"));
            setSize(object.getInt("size"));
            setEnglishVoicePath(object.getString("englishVoicePath"));
            //if (object.getString("content").endsWith("letter")){
            //setEnglishVoicePath("englishVoice");
            //}
            setChineseVoicePath(object.getString("chineseVoicePath"));
            setImg1Path(object.getString("img1Path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public EnglishTopicBean(int id, int version, int deleted, long createTime, long updateTime, String content, int size, String englishVoicePath, String chineseVoicePath, String img1Path) {
        this.id = id;
        this.version = version;
        this.deleted = deleted;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.content = content;
        this.size = size;
        this.englishVoicePath = englishVoicePath;
        this.chineseVoicePath = chineseVoicePath;
        this.img1Path = img1Path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setEnglishVoicePath(String englishVoicePath) {
        this.englishVoicePath = englishVoicePath;
    }

    public void setChineseVoicePath(String chineseVoicePath) {
        this.chineseVoicePath = chineseVoicePath;
    }

    public void setImg1Path(String img1Path) {
        this.img1Path = img1Path;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public int getDeleted() {
        return deleted;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

    public String getEnglishVoicePath() {
        return englishVoicePath;
    }

    public String getChineseVoicePath() {
        return chineseVoicePath;
    }

    public String getImg1Path() {
        return img1Path;
    }
}
