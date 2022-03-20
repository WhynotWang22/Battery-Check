package com.weather

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    var text_view :TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_view = findViewById(R.id.text_view)
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        this.registerReceiver(myBroadcastReceiver,intentFilter)
    }
    private val myBroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val stringBuilder = StringBuilder()
            val batteryPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            stringBuilder.append("Battery percentage:\n$batteryPercentage %n")
            stringBuilder.append("\nBattery condition:\n")

            when(intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0)){
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> stringBuilder.append("over heat\n")
                BatteryManager.BATTERY_HEALTH_GOOD -> stringBuilder.append("good\n")
                BatteryManager.BATTERY_HEALTH_COLD -> stringBuilder.append("cold\n")
                BatteryManager.BATTERY_HEALTH_DEAD -> stringBuilder.append("dead\n")
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> stringBuilder.append("over voltage\n")
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> stringBuilder.append("failure\n")
                else -> stringBuilder.append("unknown\n")
            }
            stringBuilder.append("\nTemperature:\n")
            val tempertureInCelsius = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)/10

            stringBuilder.append("$tempertureInCelsius \u00B0C\n")

            val temperatureInFahrenheit = ((tempertureInCelsius *1.8)+32).toInt()

            stringBuilder.append("$temperatureInFahrenheit \u00B0F\n")

            stringBuilder.append("$\nPower soure:\n")

            when(intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1)){

                BatteryManager.BATTERY_PLUGGED_AC -> stringBuilder.append("AC adapter")
                BatteryManager.BATTERY_PLUGGED_USB -> stringBuilder.append("USB connection\n")
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> stringBuilder.append("Wireless connection\n")
                else -> stringBuilder.append("NO power source\n")
            }
           stringBuilder.append("\nCharging status:\n")

           when(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){
               BatteryManager.BATTERY_STATUS_CHARGING -> stringBuilder.append("charging\n")
               BatteryManager.BATTERY_STATUS_DISCHARGING ->stringBuilder.append("not charging \n")
               BatteryManager.BATTERY_STATUS_FULL -> stringBuilder.append("full\n")
               BatteryManager.BATTERY_STATUS_NOT_CHARGING -> stringBuilder.append("not charging \n")
               BatteryManager.BATTERY_STATUS_UNKNOWN -> stringBuilder.append("unknown \n")
               else -> stringBuilder.append("unknown\n")
           }
            val technology = intent.extras?.getString(BatteryManager.EXTRA_TECHNOLOGY)
            stringBuilder.append("\nTechnology:\n$technology\n")

            val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0).toDouble()/1000

            stringBuilder.append("\nVoltage:\n$voltage V\n")


            text_view!!.text = stringBuilder
        }


    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }
}