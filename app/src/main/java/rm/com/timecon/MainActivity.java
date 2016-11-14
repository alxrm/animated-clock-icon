package rm.com.timecon;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
    final ClockDrawable clockDrawable = new ClockDrawable.Builder(this)
        .hours(10)
        .minutes(30)
        .withColor(Color.WHITE)
        .withPointerStroke(Stroke.THIN)
        .withDuration(1000)
        .build();

    clocks.setImageDrawable(clockDrawable);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        clockDrawable.animateToTime(21, 18);
      }
    }, 2000);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        clockDrawable.animateToTime(0, 46);
      }
    }, 4000);

  }
}
