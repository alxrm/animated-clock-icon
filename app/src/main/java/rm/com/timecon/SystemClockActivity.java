package rm.com.timecon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import rm.com.clocks.ClockImageView;

public final class SystemClockActivity extends AppCompatActivity {

  private final Handler handlerTicker = new Handler();
  private final long intervalTicker = TimeUnit.MINUTES.toMillis(1);

  private ClockImageView viewClockFace;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_system_clock);

    viewClockFace = (ClockImageView) findViewById(R.id.clock_face);
  }

  @Override protected void onResume() {
    super.onResume();
    showToCurrentTime(true);
    scheduleNextTick();
  }

  @Override protected void onPause() {
    super.onPause();
    cancelTickerSchedule();
  }

  private void cancelTickerSchedule() {
    handlerTicker.removeCallbacksAndMessages(null);
  }

  private void scheduleNextTick() {
    handlerTicker.postDelayed(new Runnable() {
      @Override public void run() {
        showToCurrentTime(false);
        scheduleNextTick();
      }
    }, intervalTicker);
  }

  private void showToCurrentTime(boolean animated) {
    final Calendar calendar = Calendar.getInstance();
    final int hours = calendar.get(Calendar.HOUR_OF_DAY);
    final int minutes = calendar.get(Calendar.MINUTE);

    if (animated) {
      viewClockFace.animateToTime(hours, minutes);
    } else {
      viewClockFace.setHours(hours);
      viewClockFace.setMinutes(minutes);
    }
  }
}
