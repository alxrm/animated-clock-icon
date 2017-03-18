package rm.com.timecon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import rm.com.clocks.ClockImageView;

public final class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ClockImageView clocks = (ClockImageView) findViewById(R.id.clocks);
    final TextView timeText = (TextView) findViewById(R.id.clocks_time);
    final Button showNext = (Button) findViewById(R.id.clocks_show);

    //    final ClockDrawable clockDrawable = ClockDrawable.builder(this)
    //        .hours(4)
    //        .minutes(20)
    //        .withSpeed(-2.5F)
    //        .withColor(0xAAFFFFFF)
    //        .withDuration(600)
    //        .withFrameWidth(Stroke.REGULAR)
    //        .into(clocks);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        timeText.setText("21:18");
        clocks.animateToTime(21, 18);
      }
    }, 1000);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        timeText.setText("0:46");
        clocks.animateToTime(0, 46);
      }
    }, 2000);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        timeText.setText("Indeterminate");
        clocks.animateIndeterminate();
      }
    }, 3000);

    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        timeText.setText("3:22");
        clocks.animateToTime(3, 22);
      }
    }, 5000);

    showNext.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, SystemClockActivity.class));
      }
    });
  }
}
