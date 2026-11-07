package com.mc.tracker.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Prefs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0014\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u000f2\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00132\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bJ\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\bJ\u0018\u0010\u001c\u001a\n \u001e*\u0004\u0018\u00010\u001d0\u001d2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001c\u0010\u001f\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"J\u0016\u0010#\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010$\u001a\u00020\nJ\u001c\u0010%\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"J6\u0010&\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u00042\u0006\u0010+\u001a\u00020\nJ\u001c\u0010,\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"J\"\u0010-\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0013J\u001c\u0010/\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"J\u0016\u00100\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00101\u001a\u00020\u0004J\"\u00102\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0013J\u0016\u00103\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u00104\u001a\u00020\u0015J\u001c\u00105\u001a\u00020 2\u0006\u0010\u0007\u001a\u00020\b2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\"R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lcom/mc/tracker/util/Prefs;", "", "()V", "FILE_NAME", "", "getAlarmedJobs", "", "context", "Landroid/content/Context;", "getCheckIntervalMinutes", "", "getCompletedNotified", "getExtraTrackedJobs", "getHost", "getJobWaitCache", "", "getKnownJobs", "getLastJobsRaw", "getLastKnownStates", "", "getMonitoringEnabled", "", "getPassword", "getPrivateKey", "getThresholdMinutes", "getUsername", "getWatchedJobs", "isConfigured", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "saveAlarmedJobs", "", "set", "", "saveCheckIntervalMinutes", "minutes", "saveCompletedNotified", "saveConfig", "host", "username", "privateKey", "password", "thresholdMinutes", "saveExtraTrackedJobs", "saveJobWaitCache", "map", "saveKnownJobs", "saveLastJobsRaw", "raw", "saveLastKnownStates", "saveMonitoringEnabled", "enabled", "saveWatchedJobs", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class Prefs {
    private static final String FILE_NAME = "mc_tracker_prefs";
    public static final Prefs INSTANCE = new Prefs();

    private Prefs() {
    }

    private final SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(FILE_NAME, 0);
    }

    public final void saveConfig(Context context, String host, String username, String privateKey, String password, int thresholdMinutes) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(privateKey, "privateKey");
        Intrinsics.checkNotNullParameter(password, "password");
        prefs(context).edit().putString("host", host).putString("username", username).putString("privateKey", privateKey).putString("password", password).putInt("thresholdMinutes", thresholdMinutes).apply();
    }

    public final String getHost(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("host", "ssh.metacentrum.cz");
        return string == null ? "ssh.metacentrum.cz" : string;
    }

    public final String getUsername(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("username", "");
        return string == null ? "" : string;
    }

    public final String getPrivateKey(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("privateKey", "");
        return string == null ? "" : string;
    }

    public final String getPassword(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("password", "");
        return string == null ? "" : string;
    }

    public final int getThresholdMinutes(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return prefs(context).getInt("thresholdMinutes", 60);
    }

    public final int getCheckIntervalMinutes(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return prefs(context).getInt("checkIntervalMinutes", 15);
    }

    public final void saveCheckIntervalMinutes(Context context, int minutes) {
        Intrinsics.checkNotNullParameter(context, "context");
        prefs(context).edit().putInt("checkIntervalMinutes", RangesKt.coerceAtLeast(minutes, 15)).apply();
    }

    public final boolean getMonitoringEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return prefs(context).getBoolean("monitoringEnabled", true);
    }

    public final void saveMonitoringEnabled(Context context, boolean enabled) {
        Intrinsics.checkNotNullParameter(context, "context");
        prefs(context).edit().putBoolean("monitoringEnabled", enabled).apply();
    }

    public final boolean isConfigured(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (StringsKt.isBlank(getUsername(context)) ^ true) && ((StringsKt.isBlank(getPrivateKey(context)) ^ true) || (StringsKt.isBlank(getPassword(context)) ^ true));
    }

    public final void saveLastJobsRaw(Context context, String raw) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(raw, "raw");
        prefs(context).edit().putString("lastJobsRaw", raw).apply();
    }

    public final String getLastJobsRaw(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("lastJobsRaw", "");
        return string == null ? "" : string;
    }

    public final Set<String> getAlarmedJobs(Context context) {
        Set<String> mutableSet;
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = prefs(context).getStringSet("alarmedJobs", SetsKt.emptySet());
        return (stringSet == null || (mutableSet = CollectionsKt.toMutableSet(stringSet)) == null) ? new LinkedHashSet() : mutableSet;
    }

    public final void saveAlarmedJobs(Context context, Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "set");
        prefs(context).edit().putStringSet("alarmedJobs", set).apply();
    }

    public final Set<String> getKnownJobs(Context context) {
        Set<String> mutableSet;
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = prefs(context).getStringSet("knownJobs", SetsKt.emptySet());
        return (stringSet == null || (mutableSet = CollectionsKt.toMutableSet(stringSet)) == null) ? new LinkedHashSet() : mutableSet;
    }

    public final void saveKnownJobs(Context context, Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "set");
        prefs(context).edit().putStringSet("knownJobs", set).apply();
    }

    public final Set<String> getCompletedNotified(Context context) {
        Set<String> mutableSet;
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = prefs(context).getStringSet("completedNotified", SetsKt.emptySet());
        return (stringSet == null || (mutableSet = CollectionsKt.toMutableSet(stringSet)) == null) ? new LinkedHashSet() : mutableSet;
    }

    public final void saveCompletedNotified(Context context, Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "set");
        prefs(context).edit().putStringSet("completedNotified", set).apply();
    }

    public final Set<String> getWatchedJobs(Context context) {
        Set<String> mutableSet;
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = prefs(context).getStringSet("watchedJobs", SetsKt.emptySet());
        return (stringSet == null || (mutableSet = CollectionsKt.toMutableSet(stringSet)) == null) ? new LinkedHashSet() : mutableSet;
    }

    public final void saveWatchedJobs(Context context, Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "set");
        prefs(context).edit().putStringSet("watchedJobs", set).apply();
    }

    public final Map<String, String> getLastKnownStates(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("lastKnownStates", "");
        String raw = string != null ? string : "";
        if (StringsKt.isBlank(raw)) {
            return MapsKt.emptyMap();
        }
        try {
            JSONObject json = new JSONObject(raw);
            Map map = new LinkedHashMap();
            Iterator<String> itKeys = json.keys();
            Intrinsics.checkNotNullExpressionValue(itKeys, "keys(...)");
            while (itKeys.hasNext()) {
                Object element$iv = itKeys.next();
                String key = (String) element$iv;
                Intrinsics.checkNotNull(key);
                String string2 = json.getString(key);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                map.put(key, string2);
            }
            return map;
        } catch (Exception e) {
            return MapsKt.emptyMap();
        }
    }

    public final void saveLastKnownStates(Context context, Map<String, String> map) throws JSONException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(map, "map");
        JSONObject json = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            json.put(k, v);
        }
        prefs(context).edit().putString("lastKnownStates", json.toString()).apply();
    }

    public final Map<String, String> getJobWaitCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = prefs(context).getString("jobWaitCache", "");
        String raw = string != null ? string : "";
        if (StringsKt.isBlank(raw)) {
            return new LinkedHashMap();
        }
        try {
            JSONObject json = new JSONObject(raw);
            Map map = new LinkedHashMap();
            Iterator<String> itKeys = json.keys();
            Intrinsics.checkNotNullExpressionValue(itKeys, "keys(...)");
            while (itKeys.hasNext()) {
                Object element$iv = itKeys.next();
                String key = (String) element$iv;
                Intrinsics.checkNotNull(key);
                String string2 = json.getString(key);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                map.put(key, string2);
            }
            return map;
        } catch (Exception e) {
            return new LinkedHashMap();
        }
    }

    public final void saveJobWaitCache(Context context, Map<String, String> map) throws JSONException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(map, "map");
        JSONObject json = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            json.put(k, v);
        }
        prefs(context).edit().putString("jobWaitCache", json.toString()).apply();
    }

    public final Set<String> getExtraTrackedJobs(Context context) {
        Set<String> mutableSet;
        Intrinsics.checkNotNullParameter(context, "context");
        Set<String> stringSet = prefs(context).getStringSet("extraTrackedJobs", SetsKt.emptySet());
        return (stringSet == null || (mutableSet = CollectionsKt.toMutableSet(stringSet)) == null) ? new LinkedHashSet() : mutableSet;
    }

    public final void saveExtraTrackedJobs(Context context, Set<String> set) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(set, "set");
        prefs(context).edit().putStringSet("extraTrackedJobs", set).apply();
    }
}
