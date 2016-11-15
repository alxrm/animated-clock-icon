package rm.com.timecon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rm.com.clocks.ClockImageView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ClockImageView clocks = (ClockImageView) findViewById(R.id.clocks);
    final TextView timeText = (TextView) findViewById(R.id.clocks_time);

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
        timeText.setText("21:18");
        clocks.animateToTime(21, 18);
      }
    }, 1000);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        timeText.setText("0:46");
        clocks.animateToTime(0, 46);
      }
    }, 2000);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        timeText.setText("Indeterminate");
        clocks.start();
      }
    }, 3000);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        timeText.setText("3:22");
        clocks.animateToTime(3, 22);
      }
    }, 5000);
  }
}
