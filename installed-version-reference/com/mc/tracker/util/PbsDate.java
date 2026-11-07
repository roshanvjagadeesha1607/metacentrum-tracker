package com.mc.tracker.util;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PbsDate.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u0015\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u0004¢\u0006\u0002\u0010\u000bJ\u0018\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004¨\u0006\u000e"}, d2 = {"Lcom/mc/tracker/util/PbsDate;", "", "()V", "elapsedSince", "", "qtime", "formatDuration", "ms", "", "parse", "raw", "(Ljava/lang/String;)Ljava/lang/Long;", "waitDuration", "estimatedStart", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class PbsDate {
    public static final PbsDate INSTANCE = new PbsDate();

    private PbsDate() {
    }

    public final Long parse(String raw) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        try {
            String normalized = new Regex("\\s+").replace(StringsKt.trim((CharSequence) raw).toString(), " ");
            Date date = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.US).parse(normalized);
            if (date != null) {
                return Long.valueOf(date.getTime());
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public final String formatDuration(long ms) {
        if (ms < 0) {
            return "0m";
        }
        long totalMinutes = ms / ((long) 60000);
        long j = 1440;
        long days = totalMinutes / j;
        long j2 = 60;
        long hours = (totalMinutes % j) / j2;
        long minutes = totalMinutes % j2;
        if (days > 0) {
            return days + "d " + hours + 'h';
        }
        return hours > 0 ? hours + "h " + minutes + 'm' : new StringBuilder().append(minutes).append('m').toString();
    }

    public final String waitDuration(String qtime, String estimatedStart) {
        Intrinsics.checkNotNullParameter(qtime, "qtime");
        Intrinsics.checkNotNullParameter(estimatedStart, "estimatedStart");
        Long l = parse(qtime);
        if (l == null) {
            return null;
        }
        long start = l.longValue();
        Long l2 = parse(estimatedStart);
        if (l2 == null) {
            return null;
        }
        long end = l2.longValue();
        long diff = end - start;
        if (diff < 0) {
            return null;
        }
        return formatDuration(diff);
    }

    public final String elapsedSince(String qtime) {
        Intrinsics.checkNotNullParameter(qtime, "qtime");
        Long l = parse(qtime);
        if (l == null) {
            return null;
        }
        long start = l.longValue();
        long diff = System.currentTimeMillis() - start;
        if (diff < 0) {
            return null;
        }
        return formatDuration(diff);
    }
}
