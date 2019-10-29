package com.jamespeccia.simplebattery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;

class BatteryData extends BroadcastReceiver {

  private TextView[] textViews;

  /**
   * Constructor for class BatteryData
   *
   * @param textViews TextViews to update when a new intent is received
   */
  BatteryData(TextView[] textViews) {
    this.textViews = textViews;
  }

  /**
   * Finds and updates information about the battery
   *
   * @param intent received intent from the BroadcastReceiver
   */
  private void updateStatus(Intent intent) {
    boolean doesBatteryExist = false;

    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    // Identifies the battery's percentage, after checking if one exists
    if (level != -1 && scale != -1) {
      doesBatteryExist = true;
      int batteryPct = (int) ((level / (float) scale) * 100f);
      textViews[0].setText("Percentage: " + batteryPct + "%");
    }

    // Identifies the battery's state
    switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
      case BatteryManager.BATTERY_STATUS_CHARGING:
        textViews[1].setText("Current State: Charging");
        break;

      case BatteryManager.BATTERY_STATUS_DISCHARGING:
        textViews[1].setText("Current State: Discharging");
        break;

      case BatteryManager.BATTERY_STATUS_FULL:
        textViews[1].setText("Current State: Full");
        break;

      case BatteryManager.BATTERY_STATUS_UNKNOWN:
        textViews[1].setText("Current State: Unknown");
        break;

      case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
        textViews[1].setText("Current State: Not Charging");
        break;

      default:
        textViews[1].setText("Current State: No Data Available");
        break;
    }

    // Identifies the device's plugged state
    switch (intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)) {
      case BatteryManager.BATTERY_PLUGGED_WIRELESS:
        textViews[2].setText("Plugged: Wireless");
        break;

      case BatteryManager.BATTERY_PLUGGED_USB:
        textViews[2].setText("Plugged: USB");
        break;

      case BatteryManager.BATTERY_PLUGGED_AC:
        textViews[2].setText("Plugged: AC Adapter");
        break;

      default:
        textViews[2].setText("Plugged: None");
        break;
    }

    // Identifies the battery's voltage
    if (doesBatteryExist && intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) > 0) {
      textViews[3].setText(
          "Voltage: " + intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) / 1000f + "V");
    } else {
      textViews[5].setText("Voltage: N/A");
    }

    // Identifies the battery's health
    switch (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {
      case BatteryManager.BATTERY_HEALTH_COLD:
        textViews[4].setText("Health: Cold");
        break;

      case BatteryManager.BATTERY_HEALTH_DEAD:
        textViews[4].setText("Health: Dead");
        break;

      case BatteryManager.BATTERY_HEALTH_GOOD:
        textViews[4].setText("Health: Good");
        break;

      case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
        textViews[4].setText("Health: Over Voltage");
        break;

      case BatteryManager.BATTERY_HEALTH_OVERHEAT:
        textViews[4].setText("Health: Overheated");
        break;

      case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
        textViews[4].setText("Health: Failure");
        break;

      case BatteryManager.BATTERY_HEALTH_UNKNOWN:
        textViews[4].setText("Health: Unknown");
        break;

      default:
        textViews[4].setText("Health: No Data Available");
        break;
    }

    // Identifies the battery's temperature
    if (doesBatteryExist && intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) > 0) {
      textViews[5].setText(
          "Temperature: "
              + ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10f
              + "°C");
    } else {
      textViews[5].setText("Temperature: N/A");
    }

    // Identifies the battery's type
    if (doesBatteryExist
        && !"".equals(intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY))) {
      textViews[6].setText(
          "Technology: " + intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY));
    } else {
      textViews[6].setText("Technology: N/A");
    }
  }

  /**
   * Called when a new intent is received. When called, the battery information is updated through
   * the method updateStatus
   *
   * @param context context in which the intent was received
   * @param intent intent received
   */
  @Override
  public void onReceive(Context context, Intent intent) {
    updateStatus(intent);
  }
}
