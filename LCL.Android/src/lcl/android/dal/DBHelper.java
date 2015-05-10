package lcl.android.dal;

import java.util.List;

import lcl.android.core.AppConfig;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	public final static byte[] _writeLock = new byte[0];

	// 打开数据库
	public void OpenDB(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	// 关闭数据库
	public void Close() {
		dbHelper.close();
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 插入
	 * 
	 * @param list
	 * @param table
	 *            表名
	 */
	public void Insert(List<ContentValues> list, String tableName) {
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				db.delete(tableName, null, null);
				for (int i = 0, len = list.size(); i < len; i++)
					db.insert(tableName, null, list.get(i));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}

	public DBHelper(Context context) {
		this.dbHelper = new DatabaseHelper(context);
	}

	/**
	 * 用于初始化数据库
	 * 
	 * @author Administrator
	 * 
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		// 定义数据库文件
		private static final String DB_NAME = AppConfig.DB_FILE_NAME;
		// 定义数据库版本
		private static final int DB_VERSION = 1;

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			CreateBlogDb(db);
		}

		/**
		 * 创建BlogList表
		 * 
		 * @param db
		 */
		private void CreateBlogDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE [" + AppConfig.DB_BLOG_TABLE + "] (");
			sb.append("[BlogId] INTEGER(13) NOT NULL DEFAULT (0), ");
			sb.append("[BlogTitle] NVARCHAR(50) NOT NULL DEFAULT (''), ");
			sb.append("[Summary] NVARCHAR(500) NOT NULL DEFAULT (''), ");
			sb.append("[Content] NTEXT NOT NULL DEFAULT (''), ");
			sb.append("[Published] DATETIME, ");
			sb.append("[Updated] DATETIME, ");
			sb.append("[AuthorUrl] NVARCHAR(200), ");
			sb.append("[AuthorName] NVARCHAR(50), ");
			sb.append("[AuthorAvatar] NVARCHAR(200), ");
			sb.append("[View] INTEGER(16) DEFAULT (0), ");
			sb.append("[Comments] INTEGER(16) DEFAULT (0), ");
			sb.append("[Digg] INTEGER(16) DEFAULT (0), ");
			sb.append("[IsReaded] BOOLEAN DEFAULT (0), ");
			sb.append("[IsFull] BOOLEAN DEFAULT (0), ");// 是否全文
			sb.append("[BlogUrl] NVARCHAR(200), ");// 网页地址
			sb.append("[UserName] NVARCHAR(50), ");// 用户名
			sb.append("[CateId] INTEGER(16), ");
			sb.append("[CateName] NVARCHAR(16))");

			db.execSQL(sb.toString());
		}

		/**
		 * 更新版本时更新表
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			DropTable(db);
			onCreate(db);
		}

		/**
		 * 删除表
		 * 
		 * @param db
		 */
		private void DropTable(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE IF EXISTS " + AppConfig.DB_BLOG_TABLE + ";");
			db.execSQL(sb.toString());
		}

		/**
		 * 清空数据表（仅清空无用数据）
		 * 
		 * @param db
		 */
		public static void ClearData(Context context) {
			DatabaseHelper dbHelper = new DBHelper.DatabaseHelper(context);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM " + AppConfig.DB_BLOG_TABLE + ";");// 清空博客表
			db.execSQL(sb.toString());
		}
	}
}
