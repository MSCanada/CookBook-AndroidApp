package muhammadsuhail.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Recipe> result_json;
    String responseString = null;
    JSONObject jsonResponse;
    JSONArray jsonMainNode;
    Button button;
    EditText input;
    TextView source_url;
    TextView title;
    ListView custom_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input=(EditText)findViewById(R.id.input);

        ArrayList<Recipe> it=new ArrayList<Recipe>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        it=dbHandler.findProduct();

        custom_list = (ListView) findViewById(R.id.custom_list);
        custom_list.setAdapter(new CustomListAdapter(getApplicationContext(), it));
        custom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object ob=custom_list.getItemAtPosition(position);
                Recipe item=(Recipe)ob;
                Intent i=new Intent(getApplicationContext(),Recipe_display.class);
                i.putExtra("item_selected", item);
                startActivity(i);
            }
        });




    }


    public void clickfunction(View view){
        String input2= input.getText().toString();
        String input1= URLEncoder.encode(input2);

        new RequestTask().execute("http://food2fork.com/api/search?key=0db3874e77a0eebd62cda99128afb010&q="+input1);


    }



    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;

            try {

                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();

                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            // ArrayList<Recipe> result_json=new ArrayList<Recipe>();
            try {
                jsonResponse = new JSONObject(result);
                jsonMainNode = jsonResponse.optJSONArray("recipes");
                int lengthJsonArr = jsonMainNode.length();
                result_json=new ArrayList<Recipe>();
                for(int i=0;i<lengthJsonArr;i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String title = jsonChildNode.optString("title").toString();
                    String source_url = jsonChildNode.optString("source_url").toString();
                    String image_url = jsonChildNode.optString("image_url").toString();


                    Recipe recipe_data = new Recipe();
                    recipe_data.settitle(title);
                    recipe_data.setimage_url(image_url);
                    recipe_data.setsource_url(source_url);
                    result_json.add(recipe_data);
                }
                if(lengthJsonArr==0)
                {
                    Toast.makeText(getApplicationContext(),"No Recipes Found",Toast.LENGTH_LONG).show();
                }

                custom_list = (ListView) findViewById(R.id.custom_list);
                custom_list.setAdapter(new CustomListAdapter(getApplicationContext(), result_json));
                custom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object ob=custom_list.getItemAtPosition(position);
                        Recipe item=(Recipe)ob;
                        Intent i=new Intent(getApplicationContext(),Recipe_display.class);
                        i.putExtra("item_selected", item);
                        startActivity(i);
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
                super.onPostExecute(result);
                //Do anything with response..
            }

        }
    }
    @Override
    protected void onDestroy() {
        Intent intent=new Intent(getApplicationContext(),SoundPlay.class);
        stopService(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();

    }
    public void favourites(View view)
    {
        ArrayList<Recipe> it=new ArrayList<Recipe>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        it=dbHandler.findProduct();

        custom_list = (ListView) findViewById(R.id.custom_list);
        custom_list.setAdapter(new CustomListAdapter(getApplicationContext(), it));
        custom_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object ob=custom_list.getItemAtPosition(position);
                Recipe item=(Recipe)ob;
                Intent i=new Intent(getApplicationContext(),Recipe_display.class);
                i.putExtra("item_selected", item);
                startActivity(i);
            }
        });
    }

}