package com.mc.tracker.util;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import com.mc.tracker.AlarmActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotificationHelper.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u001e\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J\u001e\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J&\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/mc/tracker/util/NotificationHelper;", "", "()V", "CHANNEL_ALARM", "", "CHANNEL_COMPLETE", "ensureChannels", "", "context", "Landroid/content/Context;", "showJobCompleted", "jobId", "jobName", "showJobStarted", "showWalltimeAlarm", "remainingMinutes", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NotificationHelper {
    public static final String CHANNEL_ALARM = "job_alarm";
    public static final String CHANNEL_COMPLETE = "job_complete";
    public static final NotificationHelper INSTANCE = new NotificationHelper();

    private NotificationHelper() {
    }

    public final void ensureChannels(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager nm = (NotificationManager) context.getSystemService(NotificationManager.class);
            Uri alarmSound = RingtoneManager.getDefaultUri(4);
            NotificationChannel $this$ensureChannels_u24lambda_u240 = new NotificationChannel(CHANNEL_ALARM, "Walltime alarms", 4);
            $this$ensureChannels_u24lambda_u240.setSound(alarmSound, new AudioAttributes.Builder().setUsage(4).build());
            $this$ensureChannels_u24lambda_u240.enableVibration(true);
            NotificationChannel completeChannel = new NotificationChannel(CHANNEL_COMPLETE, "Job completed", 3);
            nm.createNotificationChannel($this$ensureChannels_u24lambda_u240);
            nm.createNotificationChannel(completeChannel);
        }
    }

    public final void showWalltimeAlarm(Context context, String jobId, String jobName, long remainingMinutes) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(jobId, "jobId");
        Intrinsics.checkNotNullParameter(jobName, "jobName");
        ensureChannels(context);
        Intent $this$showWalltimeAlarm_u24lambda_u241 = new Intent(context, (Class<?>) AlarmActivity.class);
        $this$showWalltimeAlarm_u24lambda_u241.putExtra("jobId", jobId);
        $this$showWalltimeAlarm_u24lambda_u241.putExtra("jobName", jobName);
        $this$showWalltimeAlarm_u24lambda_u241.setFlags(335544320);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, jobId.hashCode(), $this$showWalltimeAlarm_u24lambda_u241, 201326592);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ALARM).setSmallIcon(R.drawable.ic_dialog_alert).setContentTitle("Walltime almost up: " + jobName).setContentText("Only ~" + remainingMinutes + " min of walltime left for job " + jobId).setPriority(1).setCategory(NotificationCompat.CATEGORY_ALARM).setFullScreenIntent(fullScreenPendingIntent, true).setAutoCancel(true).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        NotificationManager nm = (NotificationManager) context.getSystemService(NotificationManager.class);
        nm.notify(jobId.hashCode(), notification);
    }

    public final void showJobCompleted(Context context, String jobId, String jobName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(jobId, "jobId");
        Intrinsics.checkNotNullParameter(jobName, "jobName");
        ensureChannels(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_COMPLETE).setSmallIcon(R.drawable.stat_sys_download_done).setContentTitle("Job completed").setContentText(jobName + " (" + jobId + ") has finished").setPriority(0).setAutoCancel(true).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        NotificationManager nm = (NotificationManager) context.getSystemService(NotificationManager.class);
        nm.notify((jobId + "_done").hashCode(), notification);
    }

    public final void showJobStarted(Context context, String jobId, String jobName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(jobId, "jobId");
        Intrinsics.checkNotNullParameter(jobName, "jobName");
        ensureChannels(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_COMPLETE).setSmallIcon(R.drawable.ic_media_play).setContentTitle("Job started running").setContentText(jobName + " (" + jobId + ") has started").setPriority(0).setAutoCancel(true).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        NotificationManager nm = (NotificationManager) context.getSystemService(NotificationManager.class);
        nm.notify((jobId + "_started").hashCode(), notification);
    }
}
