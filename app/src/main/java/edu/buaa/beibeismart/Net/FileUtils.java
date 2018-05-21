package edu.buaa.beibeismart.Net;

import android.os.Environment;

import java.io.File;

/**
 * Created by DK on 2018/5/15.
 */

public class FileUtils {
    private String path = Environment.getExternalStorageDirectory().toString() + "/voice";

    public FileUtils() {
        File file = new File(path);
        /**
         *如果文件夹不存在就创建
         */
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * 创建一个文件
     * @param FileName 文件名
     * @return
     */
    public File createFile(String FileName) {
        return new File(path, FileName);
    }

}
