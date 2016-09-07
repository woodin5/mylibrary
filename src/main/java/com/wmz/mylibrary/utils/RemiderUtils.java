package com.wmz.mylibrary.utils;

import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.Vibrator;

/**
 * Created by wmz on 26/7/16.
 */
public class RemiderUtils {
    private Vibrator vibrator;
    private MediaPlayer player;
    private Context context;
    private boolean isVibrator;
    private boolean isRing;
    private volatile static RemiderUtils instance;

    private RemiderUtils(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new RemiderUtils(context.getApplicationContext());
        }
    }

    public static RemiderUtils getInstance() {
        if (instance == null) {
            throw new NullPointerException("RemiderUtils need init!");
        }
        return instance;
    }


    public RemiderUtils setRing(boolean isRing) {


        this.isRing = isRing;
        if (isRing) {
            if (player == null) {
                player = new MediaPlayer();
                player.setLooping(true);
            }

            playRing();

        } else {
            stopRing();
        }
        return this;
    }

    public RemiderUtils setVibrator(boolean isVibrator) {

        this.isVibrator = isVibrator;

        if (isVibrator) {
            if (vibrator == null) {
                vibrator = (Vibrator) context
                        .getSystemService(Service.VIBRATOR_SERVICE);
            }

            if(isVibratorOver) {
                isVibratorOver=false;
                mHandler.postDelayed(vr, 1500);
                vibrator.vibrate(1000);
            }
        } else {
            stopVibrator();
        }
        return this;
    }
    private boolean isVibratorOver=true;
    private Runnable vr = new Runnable() {
        @Override
        public void run() {
            isVibratorOver=true;
        }
    };

    private Handler mHandler = new Handler();


    public RemiderUtils stopRemide() {
        try {
            stopRing();
            stopVibrator();
        } catch (Exception e) {
        }
        return this;
    }

    private void stopVibrator() {
        if (isVibrator && vibrator != null && vibrator.hasVibrator()) {

            vibrator.cancel();
        }
    }

    private void stopRing() {
        if (isRing && player != null && player.isPlaying()) {
            player.stop();
        }
    }

    private void playRing() {
        try {
            if (player.isPlaying()) {
                return;
            }
            player.reset();
            player.setDataSource(context, RingtoneManager
                    .getActualDefaultRingtoneUri(context,
                            RingtoneManager.TYPE_ALARM));
            player.setLooping(true);
            player.prepare();
            player.start();
        } catch (Exception e) {
        }
    }

    public void release() {
        if (isRing && player != null) {
            player.release();
        }
    }
}
