package com.mc.tracker.util;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.model.Job;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;

/* JADX INFO: compiled from: JobStateTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\f"}, d2 = {"Lcom/mc/tracker/util/JobStateTracker;", "", "()V", "processStateTransitions", "", "context", "Landroid/content/Context;", "jobs", "", "Lcom/mc/tracker/model/Job;", "thresholdMinutes", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class JobStateTracker {
    public static final JobStateTracker INSTANCE = new JobStateTracker();

    private JobStateTracker() {
    }

    public final void processStateTransitions(Context context, List<Job> jobs, int thresholdMinutes) throws JSONException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(jobs, "jobs");
        Set<String> watchedJobs = Prefs.INSTANCE.getWatchedJobs(context);
        Map<String, String> lastKnownStates = Prefs.INSTANCE.getLastKnownStates(context);
        Map newStates = new LinkedHashMap();
        List<Job> $this$filter$iv = jobs;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Job it = (Job) element$iv$iv;
            if (true ^ it.isExtraTracked()) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $this$map$iv = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        for (Object item$iv$iv : $this$map$iv) {
            Job it2 = (Job) item$iv$iv;
            destination$iv$iv2.add(it2.getJobId());
        }
        Set<String> set = CollectionsKt.toSet((List) destination$iv$iv2);
        Set<String> knownJobs = Prefs.INSTANCE.getKnownJobs(context);
        Set<String> mutableSet = CollectionsKt.toMutableSet(Prefs.INSTANCE.getAlarmedJobs(context));
        Set<String> mutableSet2 = CollectionsKt.toMutableSet(Prefs.INSTANCE.getCompletedNotified(context));
        for (Job job : jobs) {
            newStates.put(job.getJobId(), job.getState());
            String prevState = lastKnownStates.get(job.getJobId());
            if (Intrinsics.areEqual(job.getState(), "R") && Intrinsics.areEqual(prevState, "Q") && watchedJobs.contains(job.getJobId())) {
                NotificationHelper.INSTANCE.showJobStarted(context, job.getJobId(), job.getName());
                watchedJobs.remove(job.getJobId());
            }
            if (Intrinsics.areEqual(job.getState(), "R")) {
                long remainingMin = job.remainingSec() / ((long) 60);
                boolean z = false;
                if (0 <= remainingMin && remainingMin <= thresholdMinutes) {
                    z = true;
                }
                if (z && !mutableSet.contains(job.getJobId())) {
                    NotificationHelper.INSTANCE.showWalltimeAlarm(context, job.getJobId(), job.getName(), remainingMin);
                    mutableSet.add(job.getJobId());
                }
            }
            if (Intrinsics.areEqual(job.getState(), "C") && !mutableSet2.contains(job.getJobId())) {
                NotificationHelper.INSTANCE.showJobCompleted(context, job.getJobId(), job.getName());
                mutableSet2.add(job.getJobId());
            }
        }
        Set<String> vanished = SetsKt.minus((Set) knownJobs, (Iterable) set);
        for (String id : vanished) {
            if (!mutableSet2.contains(id)) {
                NotificationHelper.INSTANCE.showJobCompleted(context, id, id);
                mutableSet2.add(id);
            }
        }
        Prefs.INSTANCE.saveKnownJobs(context, set);
        Prefs.INSTANCE.saveAlarmedJobs(context, mutableSet);
        Prefs.INSTANCE.saveCompletedNotified(context, mutableSet2);
        Prefs.INSTANCE.saveWatchedJobs(context, watchedJobs);
        Prefs.INSTANCE.saveLastKnownStates(context, newStates);
    }
}
