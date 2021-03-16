package com.example.sheba_mental_health_project.model;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.example.sheba_mental_health_project.R;
import com.example.sheba_mental_health_project.view.MainActivity;

public class NotificationsReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private RemoteViews mRemoteViews;
    private Notification mNotification;

    private final int APPOINTMENT_NOTIF_ID = 0;

    private final String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        final boolean isNotificationAllowed = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean("notifications_sp", false);

        final Bundle bundle = intent.getBundleExtra("bundle");

        if (isNotificationAllowed && bundle != null) {
            final Appointment appointment = (Appointment) bundle.getSerializable("appointment");
            final boolean isPatient = bundle.getBoolean("is_patient", true);

            if (appointment != null) {
                createUpComingAppointmentNotification(context, appointment, isPatient);
            }
        }
    }

    private void createUpComingAppointmentNotification(final Context context,
                                                       final Appointment appointment,
                                                       final boolean isPatient) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        final String channelID = "sheba_channel_id";
        final CharSequence channelName = "Sheba_Channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    null);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);
        builder.setPriority(Notification.PRIORITY_MAX).setContentTitle(context.getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_red_pain_point) // TODO: add a logo
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        final Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final String action = isPatient ? "open_patient_appointment" : "open_therapist_appointment";
        activityIntent.setAction(action);

        activityIntent.putExtra("appointment_to_open", appointment);
        final PendingIntent activityPendingIntent = PendingIntent.getActivity(context,
                0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(activityPendingIntent);

        builder.setCustomContentView(mRemoteViews);

        mNotification = builder.build();

        final String notificationSubject = isPatient ?
                context.getString(R.string.appointment_notif_title_patient) + " " +
                        appointment.getTherapist().getLastName() :
                context.getString(R.string.appointment_notif_title_therapist) + ' ' +
                        appointment.getPatient().getFullName();
        mRemoteViews.setTextViewText(R.id.notif_subject_tv, notificationSubject);

        mNotificationManager.notify(APPOINTMENT_NOTIF_ID, mNotification);
    }
}
