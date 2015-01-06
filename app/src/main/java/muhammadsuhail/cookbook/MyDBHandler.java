package muhammadsuhail.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Muhammad Suhail on 04/01/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipeDB.db";
    private static final String TABLE_PRODUCTS = "recipe_table";

    public static final String COLUMN_TITLE = "recipe_title";
    public static final String COLUMN_SOURCEURL = "source_url";
    public static final String COLUMN_IMAGEURL = "image_url";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_PRODUCTS + "("
                + COLUMN_TITLE + " TEXT," + COLUMN_SOURCEURL
                + " TEXT," + COLUMN_IMAGEURL + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);

    }
    public void addProduct(Recipe product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, product.gettitle());
        values.put(COLUMN_SOURCEURL, product.getsource_url());
        values.put(COLUMN_IMAGEURL, product.getimage_url());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    public ArrayList<Recipe> findProduct() {
        ArrayList<Recipe> result=new ArrayList<Recipe>();
        String query = "Select * FROM " + TABLE_PRODUCTS ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Recipe product = new Recipe();
Integer count=cursor.getCount();
        Log.i("length", count.toString());
        cursor.moveToFirst();
for(int i=0;i<count;i++)
{
    product=new Recipe();
    product.settitle(cursor.getString(0));
    product.setsource_url(cursor.getString(1));
    product.setimage_url(cursor.getString(2));
    result.add(product);
    cursor.moveToNext();

}

        cursor.close();
        db.close();
       return result;
    }
}
