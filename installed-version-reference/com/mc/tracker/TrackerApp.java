package com.mc.tracker;

import android.app.Application;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.util.CrashHandler;
import kotlin.Metadata;

/* JADX INFO: compiled from: TrackerApp.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lcom/mc/tracker/TrackerApp;", "Landroid/app/Application;", "()V", "onCreate", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TrackerApp extends Application {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        CrashHandler.INSTANCE.install(this);
    }
}
