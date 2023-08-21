package com.jgeun.timebuddy

import android.app.Application
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics

class TimeBuddyApplication : Application() {

	override fun onCreate() {
		super.onCreate()

		initFirebase()
	}

	private fun initFirebase() {
		FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
	}
}