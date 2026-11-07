package com.mc.tracker;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.databinding.ActivityAlarmBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AlarmActivity.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014J\b\u0010\t\u001a\u00020\u0006H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/mc/tracker/AlarmActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "ringtone", "Landroid/media/Ringtone;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AlarmActivity extends AppCompatActivity {
    private Ringtone ringtone;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAlarmBinding binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(binding, "inflate(...)");
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            getWindow().addFlags(2621568);
        }
        String jobName = getIntent().getStringExtra("jobName");
        if (jobName == null) {
            jobName = "job";
        }
        String jobId = getIntent().getStringExtra("jobId");
        if (jobId == null) {
            jobId = "";
        }
        binding.textJobInfo.setText(jobName + '\n' + jobId);
        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(this, 4);
        Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
        this.ringtone = ringtone;
        if (ringtone != null) {
            ringtone.play();
        }
        binding.buttonDismiss.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.AlarmActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(AlarmActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Ringtone ringtone = this$0.ringtone;
        if (ringtone != null) {
            ringtone.stop();
        }
        this$0.finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Ringtone ringtone = this.ringtone;
        if (ringtone != null) {
            ringtone.stop();
        }
        super.onDestroy();
    }
}
