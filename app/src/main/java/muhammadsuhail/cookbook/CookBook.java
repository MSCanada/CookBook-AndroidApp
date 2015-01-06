package muhammadsuhail.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class CookBook extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        Runnable runnable = new Runnable() {
            public void run() {

                long endTime = System.currentTimeMillis() + 5*1000;

                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime-System.currentTimeMillis());
                        } catch (Exception e) {}
                    }
                }
Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        };
        Intent intent=new Intent(this,SoundPlay.class);
        startService(intent);
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    protected void onDestroy() {

        Intent intent=new Intent(getApplicationContext(),SoundPlay.class);
        stopService(intent);

        super.onDestroy();

    }

}
