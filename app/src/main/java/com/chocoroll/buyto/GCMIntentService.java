package com.chocoroll.buyto;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


public class GCMIntentService extends GCMBaseIntentService {

	private static void generateNotification(Context context, String message) {

		int icon = R.drawable.logo2;
		long when = System.currentTimeMillis();


		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(icon, message, when);

		String title = "바이투게더";

		Intent notificationIntent = new Intent(context, MainActivity.class);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);



		notification.setLatestEventInfo(context, title, message, intent);

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, notification);

	}

	@Override
	protected void onError(Context arg0, String arg1) {
        Log.e("gcm", "getmessage:" + arg1);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {

		Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);

		String msg = intent.getStringExtra("msg");
		Log.e("getmessage", "getmessage:" + msg);
		generateNotification(context,msg);

		WakeUpScreen.acquire(getApplicationContext());
		WakeUpScreen.release();



	}



	@Override

	protected void onRegistered(Context context, String reg_id) {
		Log.e("gcm", "reg_id:  "+reg_id);
	}



	@Override
	protected void onUnregistered(Context arg0, String arg1) {
        Log.e("gcm", "unregist:  "+arg1);
	}


	public static class WakeUpScreen {

		private static PowerManager.WakeLock wakeLock;

		public static void acquire(Context context, long timeout) {

			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(
					PowerManager.ACQUIRE_CAUSES_WAKEUP  |
					PowerManager.FULL_WAKE_LOCK         |
					PowerManager.ON_AFTER_RELEASE
					, context.getClass().getName());

			if(timeout > 0)
				wakeLock.acquire(timeout);
			else
				wakeLock.acquire();

		}

		public static void acquire(Context context) {
			acquire(context, 0);
		}

		public static void release() {
			if (wakeLock.isHeld())
				wakeLock.release();
		}
	}

}

