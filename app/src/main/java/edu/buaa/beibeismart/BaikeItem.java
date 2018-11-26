package edu.buaa.beibeismart;

/**
 * Created by DK on 2018/10/11.
 */

public class BaikeItem {



    private String path;
    private String name;

    public BaikeItem(String name,String path){
        this.name=name;
        this.path=path;

    }


    public void setItemName(String name) {
        this.name = name;
    }

    public void setPiturePath(String path) {
        this.path = path;
    }

    public String getPiturePath(){
       return path;
   }

    public String getItemName() {
        return name;
    }

    @Override
    public String toString(){
        return "baikeItem{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }


}
