package com.example.mygithubapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mygithubapps.R;
import com.example.mygithubapps.reminder.ReminderReceiver;
import com.example.mygithubapps.reminder.ReminderService;

public class Reminder extends AppCompatActivity implements View.OnClickListener{
    private ReminderService reminderService;
    private ReminderReceiver reminderReceiver;
    private Switch pengingat_harian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        pengingat_harian = findViewById(R.id.pengingat_harian);

        reminderReceiver = new ReminderReceiver();
        reminderService = new ReminderService(this);

        pengingat_harian.setOnClickListener(this);

        checkPengingat();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pengingat_harian:
                if (pengingat_harian.isChecked()) {
                    reminderService.saveBoolean(ReminderService.STATUS_PENGINGAT_HARIAN, true);
                    reminderReceiver.setDailyReminder(this, "09:00",
                            "Check Your Update User Github Today !!!");
                    Toast.makeText(this, getString(R.string.daily_reminder_active), Toast.LENGTH_SHORT).show();
                } else {
                    reminderService.saveBoolean(ReminderService.STATUS_PENGINGAT_HARIAN, false);
                    reminderReceiver.cancelDailyReminder(this);
                    Toast.makeText(this, getString(R.string.daily_reminder_not_active), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void checkPengingat() {
        if (reminderService.getStatusPengingatHarian()) {
            pengingat_harian.setChecked(true);
        } else {
            pengingat_harian.setChecked(false);
        }
    }
}
