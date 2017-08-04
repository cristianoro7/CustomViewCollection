package com.desperado.customviewcollection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.desperado.customviewcollection.circlesnake.CircleSnakeBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("HandlerLeak")
    private Handler mUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCircleSnakeBar.setProgress(mProgress);
            mProgress = mProgress + 1;
            mUpdateHandler.sendEmptyMessageDelayed(0, 500);
        }
    };

    private CircleSnakeBar mCircleSnakeBar;

    private int mProgress = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleSnakeBar = findViewById(R.id.main_csb_snake_bar);
        mCircleSnakeBar.setOnCircleBarSnakeListener(new CircleSnakeBar.OnCircleSnakeBarSnakeListener() {
            @Override
            public void onStartSnake(int progress) {
                Log.d(TAG, "onStartSnake: " + progress);
            }

            @Override
            public void onSnaking(int progress) {
                Log.d(TAG, "onSnaking: " + progress);
            }

            @Override
            public void onEndSnake(int progress) {
                Log.d(TAG, "onEndSnake: " + progress);
            }
        });
        mUpdateHandler.sendEmptyMessageDelayed(0, 500);
//        ImageViewContainer container = findViewById(R.id.container);
//        List<Integer> list = new ArrayList<>();
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.image14);
//        list.add(R.drawable.xixi);
//        list.add(R.drawable.image12);
//        list.add(R.mipmap.ic_launcher_round);
//        list.add(R.drawable.image14);
//        container.setImageViewContainerAdapter(new ImageViewContainer.ImageViewContainerAdapter<>(
//                list, this
//        ));
    }
}
