package rm.com.timecon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import rm.com.clocks.ClockImageView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ClockImageView clocks = (ClockImageView) findViewById(R.id.clocks);
//    final ClockDrawable clockDrawable = ClockDrawable.builder(this)
//        .hours(10)
//        .minutes(30)
//        .withColor(Color.WHITE)
//        .withPointerWidth(Stroke.THIN)
//        .withDuration(600)
//        .into(clocks);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        clocks.animateToTime(21, 18);
      }
    }, 2000);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        clocks.animateToTime(0, 46);
      }
    }, 4000);

  }
}
