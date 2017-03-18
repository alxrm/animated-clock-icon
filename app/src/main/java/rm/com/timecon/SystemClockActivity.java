package rm.com.timecon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import rm.com.clocks.ClockImageView;

public final class SystemClockActivity extends AppCompatActivity {

  private final Handler handlerTicker = new Handler();
  private final long delayInitial = TimeUnit.SECONDS.toMillis(1);

  private ClockImageView viewClockFace;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_system_clock);
    viewClockFace = (ClockImageView) findViewById(R.id.clock_face);
  }

  @Override protected void onResume() {
    super.onResume();
    scheduleNextTick(true);
  }

  @Override protected void onPause() {
    super.onPause();
    cancelTickerSchedule();
  }

  private void cancelTickerSchedule() {
    handlerTicker.removeCallbacksAndMessages(null);
  }

  private void scheduleNextTick(final boolean isFirstTime) {
    final long tickDelay = isFirstTime ? delayInitial : calculateNextDelay();

    handlerTicker.postDelayed(new Runnable() {
      @Override public void run() {
        showCurrentTime();
        scheduleNextTick(!isFirstTime);
      }
    }, tickDelay);
  }

  private void showCurrentTime() {
    final Calendar calendar = Calendar.getInstance();
    final int hours = calendar.get(Calendar.HOUR_OF_DAY);
    final int minutes = calendar.get(Calendar.MINUTE);

    viewClockFace.animateToTime(hours, minutes);
  }

  private long calculateNextDelay() {
    final Calendar calNextMinute = Calendar.getInstance();
    calNextMinute.set(Calendar.SECOND, 0);
    calNextMinute.set(Calendar.MILLISECOND, 0);
    calNextMinute.add(Calendar.MINUTE, 1);

    // there could be some rare cases, when the diff is negative
    return Math.max(0, calNextMinute.getTimeInMillis() - System.currentTimeMillis());
  }
}
