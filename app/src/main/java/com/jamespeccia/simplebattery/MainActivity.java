package com.jamespeccia.simplebattery;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

  private BatteryData batteryData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView device = findViewById(R.id.device);
    device.setText("Device: " + getDeviceName());

    TextView[] textViews = new TextView[7];

    // Initializes all TextViews
    textViews[0] = findViewById(R.id.percent);
    textViews[1] = findViewById(R.id.state);
    textViews[2] = findViewById(R.id.plugged);
    textViews[3] = findViewById(R.id.voltage);
    textViews[4] = findViewById(R.id.health);
    textViews[5] = findViewById(R.id.temp);
    textViews[6] = findViewById(R.id.technology);

    // Creates an intent filter to know when to update battery information
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
    intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
    intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

    // Creates and starts BatteryData to update the device's battery information
    batteryData = new BatteryData(textViews);
    registerReceiver(batteryData, intentFilter);
  }

  @Override
  protected void onDestroy() {
    unregisterReceiver(batteryData);
    super.onDestroy();
  }

  /**
   * Gets the name of the device
   *
   * @return the name of the device
   */
  private String getDeviceName() {
    String manufacturer = Build.MANUFACTURER;
    String model = Build.MODEL;
    if (model.startsWith(manufacturer)) {
      return capitalize(model);
    } else {
      return capitalize(manufacturer) + " " + model;
    }
  }

  /**
   * Capitalizes a passed string
   *
   * @param s string to be capitalized
   * @return the capitalized string
   */
  private String capitalize(String s) {
    if (s == null || s.length() == 0) {
      return "";
    }
    char first = s.charAt(0);
    if (Character.isUpperCase(first)) {
      return s;
    } else {
      return Character.toUpperCase(first) + s.substring(1);
    }
  }
}
