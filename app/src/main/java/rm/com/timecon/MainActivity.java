package rm.com.timecon;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import rm.com.clocks.ClockDrawable;
import rm.com.clocks.Stroke;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ImageView clocks = (ImageView) findViewById(R.id.clocks);
    final ClockDrawable clockDrawable = new ClockDrawable(this);

    clockDrawable.setFrameStroke(Stroke.REGULAR);
    clockDrawable.setPointerStroke(Stroke.THIN);

    clocks.setImageDrawable(clockDrawable);

//    clockDrawable.start();

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        clockDrawable.animateToTime(16, 20);
      }
    }, 2000);

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        clockDrawable.animateToTime(0, 30);
      }
    }, 4000);

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        clockDrawable.start();
      }
    }, 6000);
  }
}
