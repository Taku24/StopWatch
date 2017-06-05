package stopwatch.taku.com.stopwatch2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mResult;
    Reel[] mReels = new Reel[3];

    private int mTapCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView1 = (TextView) findViewById(R.id.reel1);
        mTextView2 = (TextView) findViewById(R.id.reel2);
        mTextView3 = (TextView) findViewById(R.id.reel3);
        mResult = (TextView) findViewById(R.id.result);
        for (int i = 0; i < 3; i += 1){
            mReels[i] = new Reel(i);
            mReels[i].start();
        }
    }

    public void btnStop(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn:
                mReels[0].stop();
                mTapCount += 1;
                break;

            case R.id.btn2:
                mReels[1].stop();
                mTapCount += 1;
                break;

            case R.id.btn3:
                mReels[2].stop();
                mTapCount += 1;
                break;

            default:
                break;
        }

        if(mTapCount == 3){
            int value1 = Integer.parseInt(mTextView1.getText().toString());
            int value2 = Integer.parseInt(mTextView2.getText().toString());
            int value3 = Integer.parseInt(mTextView3.getText().toString());
            if(value1 == value2 && value1 == value3){
                mResult.setText(getText(R.string.atari));
            } else {
                mResult.setText(getText(R.string.hazure));
            }
        }
    }

    public void onStop(){
        super.onStop();
        for(int j = 0; j < 3; j += 1){
            mReels[j].stop();
        }
        mTapCount = 0;
    }

    class Reel implements Runnable, Handler.Callback{

        private int mId;
        Handler mHandler;
        Thread mThread;

        public Reel(int id){
            mId = id;
        }

        public void start(){
            mThread = new Thread(this);
            mHandler = new Handler(this);
            mThread.start();
        }

        public void stop(){
            mThread = null;
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (mId){
                case 0:
                    mTextView1.setText((String) msg.obj);
                    break;

                case 1:
                    mTextView2.setText((String) msg.obj);
                    break;

                case 2:
                    mTextView3.setText((String) msg.obj);
                    break;

                default:
                    break;
            }
            return true;
        }

        @Override
        public void run() {
            int speed = 0;
            int num = 0;
            String st;

            switch (mId){
                case 0:
                    speed = 200;
                    break;

                case 1:
                    speed = 600;
                    break;

                case 2:
                    speed = 400;
                    break;

                default:
                    break;
            }

            while (mThread != null){
                st = String.valueOf(num);
                Message msg = Message.obtain();
                msg.obj = st;
                mHandler.sendMessage(msg);
                num++;
                if(num == 10){
                    num = 0;
                }
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e){
                    Log.d("Debug", "run: " + e);
                }

            }
        }
    }
}
