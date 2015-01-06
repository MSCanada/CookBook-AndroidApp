package muhammadsuhail.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class Recipe_display extends Activity {
    WebView source1;
    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Bundle extras=getIntent().getExtras();
        if(extras==null){return;}
        recipe= (Recipe) extras.getSerializable("item_selected");
        source1=(WebView)findViewById(R.id.webView);
        source1.setWebViewClient(new WebViewClient());
        source1.loadUrl(recipe.getsource_url());

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Integer version= Build.VERSION.SDK_INT;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addtodb){
            MyDBHandler dbHandler=new MyDBHandler(getApplicationContext(),null,null,1);
            dbHandler.addProduct(recipe);


            Toast.makeText(getApplicationContext(),"Favourite Added",Toast.LENGTH_LONG).show();
        }
        if(id==R.id.print){
            if(version<19){
                Toast.makeText(getApplicationContext(),"Feature Avaiable only for Android 4.4 or above",Toast.LENGTH_LONG).show();
            }
            else{
                PrintManager printManager = (PrintManager) this
                        .getSystemService(Context.PRINT_SERVICE);

                PrintDocumentAdapter printAdapter =
                        source1.createPrintDocumentAdapter();
                String jobName = getString(R.string.app_name) + " Document";

                PrintJob printJob = printManager.print(jobName, printAdapter,
                        new PrintAttributes.Builder().build());
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
