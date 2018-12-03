package cn.colorfuline.elderlylauncher.ui.firstscreen.db;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * 提供的数据类型等数据。
 */
public class MyProviderMetaData {


    public static final String AUTHORIY = "mycontentprovider";
    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "MyProvider.db";

    /**
     * 继承了BaseColumns，所以已经有了_ID
     */
    public static final class UserTableMetaData implements BaseColumns {

        /**
         * 访问该ContentProvider的URI
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORIY );

        //共享数据的应用 增删改查之后 本应用通过uri监听数据改变
        public static final Uri QUERY_URI = Uri.parse("content://" + AUTHORIY + "/query");
        public static final Uri INSERT_URI = Uri.parse("content://" + AUTHORIY + "/insert");
        public static final Uri DELETE_URI = Uri.parse("content://" + AUTHORIY + "/delete");
        public static final Uri UPDATE_URI = Uri.parse("content://" + AUTHORIY + "/update");

        /**
         * 列名
         */
        public static final String USER_NAME = "user_name";//用户名，
        public static final String USER_PW = "password";//密码
        public static final String NICK_NAME = "nick_name";//用户昵称    传奇丶小胖
        public static final String USER_AVATAR = "user_avatar";//用户头像   url
        public static final String USER_SEX = "user_sex";//用户性别   0 / 1
        public static final String USER_BIRTHDAY = "user_birthday";//用户生日 2016-06-20
        public static final String USER_ADDR = "user_addr";//用户所在地区   广东省 深圳市 福田区
        public static final String USER_ID = "user_id";//用户id，

        /**
         * 默认的排序方法
         */
        public static final String DEFAULT_SORT_ORDER = "_id desc";
    }
}
