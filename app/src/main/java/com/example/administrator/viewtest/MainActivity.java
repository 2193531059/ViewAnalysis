package com.example.administrator.viewtest;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.move.MyMoveView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivityA";
    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private int mCount = 0;

    private MyMoveView mMoveView;
    private Button btn1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount < FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        Log.e(TAG, "scrollX = " + scrollX);
                        mMoveView.scrollTo(scrollX, 0);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mMoveView = findViewById(R.id.my_view);
        btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        mMoveView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
//                mMoveView.smoothScrollTo(200, 100);//scrollTo方法弹性滑动

//                ObjectAnimator.ofFloat(mMoveView, "translationX", 0, 100).setDuration(1000).start();//oldNineAndroid动画弹性滑动

//                final int startX = 0;
//                final int deltaX = 100;
//                ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(1000);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        float fraction = valueAnimator.getAnimatedFraction();
//                        mMoveView.scrollTo( (int) (startX + deltaX * fraction), 0);
//                    }
//                });
//                animator.start();

                mHandler.sendEmptyMessage(MESSAGE_SCROLL_TO);
                break;
            case R.id.my_view:
                Log.e(TAG, "onClick: mMoveView click");
                break;
        }
    }
}
