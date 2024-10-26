package com.example.dicodingevents.ui.settings

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.dicodingevents.EventWorker
import com.example.dicodingevents.R
import com.example.dicodingevents.databinding.ActivitySettingsBinding
import com.example.dicodingevents.ui.ViewModelFactory
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = getString(R.string.title_setting)
        workManager = WorkManager.getInstance(this)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val settingViewModel by viewModels<SettingsViewModel> {
            factory
        }

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        settingViewModel.getEventNotificationSetting()
            .observe(this) { isEventNotificationActive: Boolean ->
                binding.switchEventNotification.isChecked = isEventNotificationActive
                if (isEventNotificationActive) {
                    getEventDataPeriodically()
                }
            }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

        binding.switchEventNotification.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            when (isChecked) {
                true -> {
                    getEventDataPeriodically()
                    settingViewModel.saveEventNotificationSetting(true)
                }

                false -> {
                    cancelPeriodTask()
                    settingViewModel.saveEventNotificationSetting(false)
                }
            }
        }

    }

    private fun getEventDataPeriodically() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest =
            PeriodicWorkRequest.Builder(EventWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraint)
                .build()

        workManager.enqueueUniquePeriodicWork(
            "EventNotificationWork",
            ExistingPeriodicWorkPolicy.KEEP, // Keep existing work if already enqueued
            periodicWorkRequest
        )
    }

    private fun cancelPeriodTask() {
        workManager.cancelUniqueWork("EventNotificationWork")
//      workManager.cancelAllWork()
    }
}