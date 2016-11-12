package rm.com.timecon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import rm.com.clocks.ClockDrawable;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final ImageView clocks = (ImageView) findViewById(R.id.clocks);
    final ClockDrawable clockDrawable = new ClockDrawable(getResources());

    clocks.setImageDrawable(clockDrawable);
  }
}
