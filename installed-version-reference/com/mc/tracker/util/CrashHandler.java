package com.mc.tracker.util;

import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: CrashHandler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/mc/tracker/util/CrashHandler;", "", "()V", "FILE_NAME", "", "clearLastCrash", "", "context", "Landroid/content/Context;", "getLastCrash", "install", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CrashHandler {
    private static final String FILE_NAME = "mc_tracker_crash";
    public static final CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {
    }

    public final void install(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        final Context appContext = context.getApplicationContext();
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.mc.tracker.util.CrashHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                CrashHandler.install$lambda$0(appContext, defaultHandler, thread, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void install$lambda$0(Context $appContext, Thread.UncaughtExceptionHandler $defaultHandler, Thread thread, Throwable throwable) {
        try {
            String trace = Log.getStackTraceString(throwable);
            Intrinsics.checkNotNullExpressionValue(trace, "getStackTraceString(...)");
            $appContext.getSharedPreferences(FILE_NAME, 0).edit().putString("lastCrash", trace).apply();
        } catch (Exception e) {
        }
        if ($defaultHandler != null) {
            $defaultHandler.uncaughtException(thread, throwable);
        }
    }

    public final String getLastCrash(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String trace = context.getSharedPreferences(FILE_NAME, 0).getString("lastCrash", null);
        String str = trace;
        if (str == null || StringsKt.isBlank(str)) {
            return null;
        }
        return trace;
    }

    public final void clearLastCrash(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.getSharedPreferences(FILE_NAME, 0).edit().remove("lastCrash").apply();
    }
}
