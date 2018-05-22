package edu.buaa.beibeismart.Activity;

import java.util.Date;
import java.util.List;

public class Translation {
    public List<data> content;
    public static class data{
        public int id;
        private int verson;
        private int deleted;
        private Date createTime;
        private Date updateTime;
        public String name;
        public String img1Path;
        public String img2Path;
        public String img3Path;
        public String img4Path;
        public String img5Path;
        public String voicePath;
    }
    private boolean last;
    private int totalElements;
    private int totalPages;
    private String sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;

    public void show(){
        System.out.println("sdsdsdsd");
        System.out.println(content.get(0).name);
        System.out.println(content.get(0).img1Path);
    }

    public List<data> getContent(){
        return content;
    }

}
