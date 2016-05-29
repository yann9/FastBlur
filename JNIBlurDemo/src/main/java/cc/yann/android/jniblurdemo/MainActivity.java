package cc.yann.android.jniblurdemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import cc.yann.android.jniblur.JNIBlur;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    TextView textView;
    ImageView imageView;
    TextView radiusView;
    SeekBar seekBar;
    Button javaBlur;
    Button jniBlur;
    Bitmap bitmap;
    Bitmap blured;

    int radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);
        radiusView = (TextView) findViewById(R.id.radius);
        seekBar = (SeekBar) findViewById(R.id.seek);
        javaBlur = (Button) findViewById(R.id.java_blur);
        jniBlur = (Button) findViewById(R.id.jni_blur);
        javaBlur.setOnClickListener(this);
        jniBlur.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test, options);
        imageView.setImageBitmap(bitmap);
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Bitmap>() {
            String prefix;
            long timeTook;
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (blured != null && blured != bitmap) {
                    blured.recycle();
                    imageView.setImageBitmap(bitmap);
                }
                dialog = ProgressDialog.show(MainActivity.this, null, "blurring...", true, false);
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                long start = System.currentTimeMillis();
                if (id == R.id.java_blur) {
                    prefix = "Java stack blur";
                    blured = FastBlur.doBlur(bitmap, radius, false);
                } else {
                    prefix = "JNI native blur";
                    blured = JNIBlur.blur(bitmap, radius);
                }
                long end = System.currentTimeMillis();
                timeTook = end - start;
                return blured;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                dialog.dismiss();
                textView.setText(prefix + " finished,took " + timeTook + " ms");
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        radius = progress;
        radiusView.setText("radius:" + radius);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
