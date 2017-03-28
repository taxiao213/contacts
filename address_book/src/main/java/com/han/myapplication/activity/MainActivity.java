package com.han.myapplication.activity;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.han.myapplication.R;
import com.han.myapplication.bean.Constant;
import com.han.myapplication.db.MySqlite;
import com.han.myapplication.fragment.MainFragment;
import com.han.myapplication.fragment.TwoDScrollingFragment;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.menu)
    FrameLayout menu;
    //@BindView(R.id.drawer)
    //DrawerLayout drawer;
    private MainFragment mainFragment;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ButterKnife.bind(this);
        initFragment();

        //打开一个数据库，path为存放的路径 /data/data/com.han.myapplication/files
        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        //读取assert目录下，的子目录的资源文件的方法
        //getAssets().open("db.db");
        /*try {
            InputStream open = getAssets().open("fonts/material-icon-font.ttf");

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //String path = new File(filesDir, "file.db").getAbsolutePath();

        //SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        //创建数据库
        SQLiteDatabase db = createDatabase();
        //插入数据
        insert(db);
        //查询数据
        query(db);
    }

    /**
     * 载入数据库
     */
    protected void copyDB(String dbName) {
        // 得到 data/data/com.xxx.xx/files 的对象
        File files = getFilesDir();
        // 生成目标文件
        File targetFile = new File(files, dbName);
        if (targetFile.exists()) {
            // 目标文件已经存在 不用再拷贝
            return;
        }

        // 资产管理器
        AssetManager assets = getAssets();
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            // 打开资产目录下的文件流
            is = assets.open(dbName);

            // 生成目标文件的写入流
            fos = new FileOutputStream(targetFile);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            StreamUtils.close(is);
            StreamUtils.close(fos);
        }
    }


    public static class StreamUtils {

        /**
         * 关闭流
         * @param stream
         */
        public static void close(Closeable stream) {
            if(stream!=null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //创建数据库
    private SQLiteDatabase createDatabase() {
        MySqlite mySqlite = new MySqlite(getApplicationContext());
        // 可读 如果存储空间满 不会报错 会给你返回一个可读的数据库
        SQLiteDatabase db = mySqlite.getReadableDatabase();
        // 可写 如果存储空间满 会报错
        // helPer.getWritableDatabase();

        return db;

    }

    /**
     * 插入数据
     *
     * @param db
     */
    private void insert(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("name", "小明");
        values.put("age", 20);
        values.put("type_id", 001);

        long insert = db.insert("people", null, values);
        if (insert != -1) {
            Log.e("insert", "插入成功");
        } else {
            Log.e("insert", "插入失败");
        }


    }

    /**
     * 查询数据
     *
     * @param db
     */
    private void query(SQLiteDatabase db) {
        // 参数1:表名
        // 参数2:列名
        // 参数3:查询条件
        // 参数4:查询条件的值
        // 参数5:分组查询
        // 参数6:分组查询的条件
        // 参数7:排序
        // 参数8:分页查询
        Cursor cursor = db.query("people", null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String age = cursor.getString(cursor.getColumnIndex("age"));
            int type_id = cursor.getInt(cursor.getColumnIndex("type_id"));
            Log.e("query", "name==" + name + "age==" + age + "type_id==" + type_id);
        }
        cursor.close();
        db.close();

    }

    private void initFragment() {
        // 获取管理类
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 得到事务
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        // 参1 要替换的布局的id 参2 替换的fragment 参3 tag 给fragment添加一个标签
        // fragmentManager.findFragmentByTag(tag)
        mainFragment = new MainFragment();
        beginTransaction.replace(R.id.main, mainFragment, Constant.TAG_MAIN);

        //        beginTransaction.replace(R.id.menu, new MenuFragment(), Constant.TAG_MENU);
        // TODO: 2017/3/27 修改为expandlistview
        //        beginTransaction.replace(R.id.menu, new MenuExpandFragment(), Constant.TAG_MENU);
        //        beginTransaction.replace(R.id.menu, new MenuTreeViewFragment(), Constant.TAG_MENU);

        final TwoDScrollingFragment menuFragment = new TwoDScrollingFragment();
        beginTransaction.replace(R.id.menu, menuFragment, Constant.TAG_MENU);
        beginTransaction.commit();// 提交事务

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //标签记录 侧滑菜单打开或者关闭的状态
                mainFragment.isMain = false;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //标签记录 侧滑菜单打开或者关闭的状态
                mainFragment.isMain = true;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }
}
