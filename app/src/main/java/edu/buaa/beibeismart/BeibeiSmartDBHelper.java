package edu.buaa.beibeismart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DK on 2018/4/27.
 */

public class BeibeiSmartDBHelper extends SQLiteOpenHelper {

     private static final String DB_Name = "BeiBeiSmartDate.db";
     private static final int version=1;

    public BeibeiSmartDBHelper(Context context) {
        super(context, DB_Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           //在打开APP主界面时使用代码 DatabaseHelper database = new DatabaseHelper(this); db = database.getReadableDatabase();
         // 获得数据库对象，只有第一次创造数据库时会调用onCreate方法

        String sql="create table Music(musicId integer primary key autoincrement, musicName varchar(100), musicTpye TEXT, musicDuration integer, collected integer " +
                "music_voice_path varchar(500), music_vedio_path varchar(500), ctime TEXT, mtime TEXT, deleted integer);";
        String sq2="create table Story(storyId integer primary key autoincrement, storyName varchar(100), storyTpye TEXT, storyDuration integer, collected integer " +
                "story_voice_path varchar(500), story_vedio_path varchar(500), ctime TEXT, mtime TEXT, deleted integer);";
        db.execSQL(sql);
        db.execSQL(sq2);

        // 连接服务器数据得到歌曲和故事信息，完善表内信息，可能会使用ODBC
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
