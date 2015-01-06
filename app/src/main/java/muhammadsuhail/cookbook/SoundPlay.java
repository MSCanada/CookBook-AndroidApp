package muhammadsuhail.cookbook;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Muhammad Suhail on 04/01/2015.
 */
public class SoundPlay extends Service {
    MediaPlayer mp;
    @Override
    public void onCreate(){
        mp=MediaPlayer.create(this,R.raw.sound);
        mp.setLooping(false); // Set looping
        mp.setVolume(100,100);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Runnable r = new Runnable() {
            public void run() {

                mp.start();


            }
        };

        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

