package com.example.nb_mis_100002.sqlitedatebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void doClick(View view) {
        switch (view.getId()) {
            case R.id.button1://创建数据库
                helper = new MySQLiteOpenHelper(this, "person.db");
                db = helper.getReadableDatabase();
                db.close();
                break;
            case R.id.button2://更新数据库
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();
                db.close();
                break;
            case R.id.button3://增加
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("name", "zhangsan");
                db.insert("person", null, values1);
                db.close();
                break;
            case R.id.button4://修改
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("name", "lisi");
                db.update("person", values2, "_id = ?", new String[]{"1"});
                db.close();
                break;
            case R.id.button5://查询
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();

                Cursor cursor = db.query("person", null, null, null, null, null, null);//全表查询
                while (cursor.moveToNext()) {
                    int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    Log.i("info", "id=" + _id + "name=" + name);
                }

//                Cursor cursor = db.query("person",new String[]{"name"},"_id > ?",
//                        new String[]{"5"},null,null,null);//单表查询
//                while (cursor.moveToNext()) {
//                    String name = cursor.getString(cursor.getColumnIndex("name"));
//                    Log.i("info", "name=" + name);
//                }

                cursor.close();
                db.close();
                break;
            case R.id.button6://删除
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();
                int count = db.delete("person", "_id = ?", new String[]{"5"});
                Log.i("info", "删除数据条数为" + count);
                db.close();
                break;
            case R.id.button7:
                helper = new MySQLiteOpenHelper(this, "person.db", 2);
                db = helper.getReadableDatabase();
                db.beginTransaction();//开始事务
                for (int i = 0; i < 100; i++) {
                    ContentValues values = new ContentValues();
                    values.put("name", "zhangsan" + i);
                    db.insert("person", null, values);
                }
                Cursor cursor1 = db.query("person", null, null, null, null, null, null);
                while (cursor1.moveToNext()) {
                    int _id = cursor1.getInt(cursor1.getColumnIndex("_id"));
                    String name = cursor1.getString(cursor1.getColumnIndex("name"));
                    Log.i("info", "id=" + _id + "name=" + name);
                }
                db.setTransactionSuccessful();//设置事务处理成功
                db.endTransaction();//结束事务
                db.close();
                break;
            default:
                break;
        }
    }

}
