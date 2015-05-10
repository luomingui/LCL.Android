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

	// �����ݿ�
	public void OpenDB(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	// �ر����ݿ�
	public void Close() {
		dbHelper.close();
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ����
	 * 
	 * @param list
	 * @param table
	 *            ����
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
	 * ���ڳ�ʼ�����ݿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		// �������ݿ��ļ�
		private static final String DB_NAME = AppConfig.DB_FILE_NAME;
		// �������ݿ�汾
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
		 * ����BlogList��
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
			sb.append("[IsFull] BOOLEAN DEFAULT (0), ");// �Ƿ�ȫ��
			sb.append("[BlogUrl] NVARCHAR(200), ");// ��ҳ��ַ
			sb.append("[UserName] NVARCHAR(50), ");// �û���
			sb.append("[CateId] INTEGER(16), ");
			sb.append("[CateName] NVARCHAR(16))");

			db.execSQL(sb.toString());
		}

		/**
		 * ���°汾ʱ���±�
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			DropTable(db);
			onCreate(db);
		}

		/**
		 * ɾ����
		 * 
		 * @param db
		 */
		private void DropTable(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE IF EXISTS " + AppConfig.DB_BLOG_TABLE + ";");
			db.execSQL(sb.toString());
		}

		/**
		 * ������ݱ�������������ݣ�
		 * 
		 * @param db
		 */
		public static void ClearData(Context context) {
			DatabaseHelper dbHelper = new DBHelper.DatabaseHelper(context);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			StringBuilder sb = new StringBuilder();
			sb.append("DELETE FROM " + AppConfig.DB_BLOG_TABLE + ";");// ��ղ��ͱ�
			db.execSQL(sb.toString());
		}
	}
}
