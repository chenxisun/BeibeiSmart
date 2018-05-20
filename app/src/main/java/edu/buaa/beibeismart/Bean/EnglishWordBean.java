package edu.buaa.beibeismart.Bean;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class EnglishWordBean implements Serializable {
    int id;
    int version;
    int deleted;
    long createTime;
    long updateTime;
    int topicId;
    String topicName;
    int wordId;
    String englishContent;
    String chineseContent;
    String englishVoicePath;
    String chineseVoicePath;

    @Override
    public String toString() {
        return "EnglishWordBean{" +
                "id=" + id +
                ", version=" + version +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", wordId=" + wordId +
                ", englishContent='" + englishContent + '\'' +
                ", chineseContent='" + chineseContent + '\'' +
                ", englishVoicePath='" + englishVoicePath + '\'' +
                ", chineseVoicePath='" + chineseVoicePath + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }

    String imgPath;

    public EnglishWordBean(JSONObject jsonObject) {
        try {
            setId(jsonObject.getInt("id"));
            setVersion(jsonObject.getInt("version"));
            setDeleted(jsonObject.getInt("deleted"));
            setCreateTime(jsonObject.getLong("createTime"));
            setUpdateTime(jsonObject.getLong("updateTime"));
            setTopicId(jsonObject.getInt("topicId"));
            setTopicName(jsonObject.getString("topicName"));
            setWordId(jsonObject.getInt("wordId"));
            setEnglishContent(jsonObject.getString("englishContent"));
            setChineseContent(jsonObject.getString("chineseContent"));
            setEnglishVoicePath(jsonObject.getString("englishVoicePath"));
            setChineseVoicePath(jsonObject.getString("chineseVoicePath"));
            setImgPath(jsonObject.getString("img1Path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getEnglishContent() {
        return englishContent;
    }

    public void setEnglishContent(String englishContent) {
        this.englishContent = englishContent;
    }

    public String getChineseContent() {
        return chineseContent;
    }

    public void setChineseContent(String chineseContent) {
        this.chineseContent = chineseContent;
    }

    public String getEnglishVoicePath() {
        return englishVoicePath;
    }

    public void setEnglishVoicePath(String englishVoicePath) {
        this.englishVoicePath = englishVoicePath;
    }

    public String getChineseVoicePath() {
        return chineseVoicePath;
    }

    public void setChineseVoicePath(String chineseVoicePath) {
        this.chineseVoicePath = chineseVoicePath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
