package com.mc.tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.databinding.ActivitySettingsBinding;
import com.mc.tracker.ssh.SshJobFetcher;
import com.mc.tracker.util.Prefs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SettingsActivity.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R(\u0010\u0005\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\b \t*\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00070\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/mc/tracker/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/mc/tracker/databinding/ActivitySettingsBinding;", "filePicker", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "kotlin.jvm.PlatformType", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private final ActivityResultLauncher<String[]> filePicker;

    public SettingsActivity() {
        ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.OpenDocument(), new ActivityResultCallback() { // from class: com.mc.tracker.SettingsActivity$$ExternalSyntheticLambda2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) throws IOException {
                SettingsActivity.filePicker$lambda$2(this.f$0, (Uri) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.filePicker = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void filePicker$lambda$2(SettingsActivity this$0, Uri uri) throws IOException {
        InputStream inputStreamOpenInputStream;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (uri != null && (inputStreamOpenInputStream = this$0.getContentResolver().openInputStream(uri)) != null) {
            Intrinsics.checkNotNull(inputStreamOpenInputStream);
            Reader inputStreamReader = new InputStreamReader(inputStreamOpenInputStream, Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                BufferedReader reader = bufferedReader;
                ActivitySettingsBinding activitySettingsBinding = this$0.binding;
                if (activitySettingsBinding == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                    activitySettingsBinding = null;
                }
                activitySettingsBinding.editKey.setText(TextStreamsKt.readText(reader));
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedReader, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding activitySettingsBindingInflate = ActivitySettingsBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activitySettingsBindingInflate, "inflate(...)");
        this.binding = activitySettingsBindingInflate;
        ActivitySettingsBinding activitySettingsBinding = null;
        if (activitySettingsBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBindingInflate = null;
        }
        setContentView(activitySettingsBindingInflate.getRoot());
        ActivitySettingsBinding activitySettingsBinding2 = this.binding;
        if (activitySettingsBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding2 = null;
        }
        activitySettingsBinding2.editHost.setText(Prefs.INSTANCE.getHost(this));
        ActivitySettingsBinding activitySettingsBinding3 = this.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        activitySettingsBinding3.editUsername.setText(Prefs.INSTANCE.getUsername(this));
        ActivitySettingsBinding activitySettingsBinding4 = this.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding4 = null;
        }
        activitySettingsBinding4.editPassword.setText(Prefs.INSTANCE.getPassword(this));
        ActivitySettingsBinding activitySettingsBinding5 = this.binding;
        if (activitySettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding5 = null;
        }
        activitySettingsBinding5.editKey.setText(Prefs.INSTANCE.getPrivateKey(this));
        ActivitySettingsBinding activitySettingsBinding6 = this.binding;
        if (activitySettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding6 = null;
        }
        activitySettingsBinding6.editThreshold.setText(String.valueOf(Prefs.INSTANCE.getThresholdMinutes(this)));
        ActivitySettingsBinding activitySettingsBinding7 = this.binding;
        if (activitySettingsBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding7 = null;
        }
        activitySettingsBinding7.buttonBrowseKey.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.SettingsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
        ActivitySettingsBinding activitySettingsBinding8 = this.binding;
        if (activitySettingsBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding = activitySettingsBinding8;
        }
        activitySettingsBinding.buttonSave.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.onCreate$lambda$5(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.filePicker.launch(new String[]{"*/*"});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$5(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivitySettingsBinding activitySettingsBinding = this$0.binding;
        ActivitySettingsBinding activitySettingsBinding2 = null;
        if (activitySettingsBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding = null;
        }
        String string = StringsKt.trim((CharSequence) String.valueOf(activitySettingsBinding.editHost.getText())).toString();
        if (StringsKt.isBlank(string)) {
            string = "ssh.metacentrum.cz";
        }
        String hostRaw = string;
        String host = SshJobFetcher.INSTANCE.sanitizeHost(hostRaw);
        ActivitySettingsBinding activitySettingsBinding3 = this$0.binding;
        if (activitySettingsBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding3 = null;
        }
        String username = StringsKt.trim((CharSequence) String.valueOf(activitySettingsBinding3.editUsername.getText())).toString();
        ActivitySettingsBinding activitySettingsBinding4 = this$0.binding;
        if (activitySettingsBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding4 = null;
        }
        String password = String.valueOf(activitySettingsBinding4.editPassword.getText());
        ActivitySettingsBinding activitySettingsBinding5 = this$0.binding;
        if (activitySettingsBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activitySettingsBinding5 = null;
        }
        String key = StringsKt.trim((CharSequence) String.valueOf(activitySettingsBinding5.editKey.getText())).toString();
        ActivitySettingsBinding activitySettingsBinding6 = this$0.binding;
        if (activitySettingsBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activitySettingsBinding2 = activitySettingsBinding6;
        }
        Integer intOrNull = StringsKt.toIntOrNull(String.valueOf(activitySettingsBinding2.editThreshold.getText()));
        int threshold = intOrNull != null ? intOrNull.intValue() : 60;
        Prefs.INSTANCE.saveConfig(this$0, host, username, key, password, threshold);
        this$0.startActivity(new Intent(this$0, (Class<?>) MainActivity.class));
        this$0.finish();
    }
}
