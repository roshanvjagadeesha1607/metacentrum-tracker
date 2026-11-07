package com.mc.tracker.worker;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.mc.tracker.model.Job;
import com.mc.tracker.parser.JobParser;
import com.mc.tracker.ssh.SshJobFetcher;
import com.mc.tracker.util.JobStateTracker;
import com.mc.tracker.util.Prefs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: JobCheckWorker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lcom/mc/tracker/worker/JobCheckWorker;", "Landroidx/work/Worker;", "context", "Landroid/content/Context;", "params", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class JobCheckWorker extends Worker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JobCheckWorker(Context context, WorkerParameters params) {
        super(context, params);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(params, "params");
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        Job extraJob;
        Context context = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(context, "getApplicationContext(...)");
        if (!Prefs.INSTANCE.isConfigured(context)) {
            ListenableWorker.Result resultSuccess = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess, "success(...)");
            return resultSuccess;
        }
        if (!Prefs.INSTANCE.getMonitoringEnabled(context)) {
            ListenableWorker.Result resultSuccess2 = ListenableWorker.Result.success();
            Intrinsics.checkNotNullExpressionValue(resultSuccess2, "success(...)");
            return resultSuccess2;
        }
        try {
            String host = Prefs.INSTANCE.getHost(context);
            String username = Prefs.INSTANCE.getUsername(context);
            String key = Prefs.INSTANCE.getPrivateKey(context);
            String password = Prefs.INSTANCE.getPassword(context);
            int thresholdMinutes = Prefs.INSTANCE.getThresholdMinutes(context);
            SshJobFetcher fetcher = new SshJobFetcher(host, username, key, password, 0, 16, null);
            String raw = fetcher.fetchQstatOutput();
            List<Job> listPlus = JobParser.INSTANCE.parse(raw);
            Set<String> extraTrackedJobs = Prefs.INSTANCE.getExtraTrackedJobs(context);
            List<Job> $this$map$iv = listPlus;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            Iterator it = $this$map$iv.iterator();
            while (true) {
                String host2 = host;
                String username2 = username;
                if (!it.hasNext()) {
                    break;
                }
                Object item$iv$iv = it.next();
                Job it2 = (Job) item$iv$iv;
                destination$iv$iv.add(StringsKt.substringBefore$default(it2.getJobId(), ".", (String) null, 2, (Object) null));
                host = host2;
                username = username2;
                key = key;
                password = password;
            }
            Set existingShortIds = CollectionsKt.toSet((List) destination$iv$iv);
            for (String extraId : extraTrackedJobs) {
                String shortExtra = StringsKt.substringBefore$default(StringsKt.trim((CharSequence) extraId).toString(), ".", (String) null, 2, (Object) null);
                if (!StringsKt.isBlank(shortExtra) && !existingShortIds.contains(shortExtra) && (extraJob = fetcher.fetchJobById(extraId)) != null) {
                    listPlus = CollectionsKt.plus((Collection<? extends Job>) listPlus, extraJob.copy((64511 & 1) != 0 ? extraJob.jobId : null, (64511 & 2) != 0 ? extraJob.name : null, (64511 & 4) != 0 ? extraJob.state : null, (64511 & 8) != 0 ? extraJob.queue : null, (64511 & 16) != 0 ? extraJob.walltimeRequestedSec : 0L, (64511 & 32) != 0 ? extraJob.walltimeUsedSec : 0L, (64511 & 64) != 0 ? extraJob.estimatedStartTime : null, (64511 & 128) != 0 ? extraJob.submitTime : null, (64511 & 256) != 0 ? extraJob.queueWaitDuration : null, (64511 & 512) != 0 ? extraJob.owner : null, (64511 & 1024) != 0 ? extraJob.isExtraTracked : true, (64511 & 2048) != 0 ? extraJob.ncpus : null, (64511 & 4096) != 0 ? extraJob.ngpus : null, (64511 & 8192) != 0 ? extraJob.mem : null, (64511 & 16384) != 0 ? extraJob.scratchLocal : null, (64511 & 32768) != 0 ? extraJob.execHost : null));
                }
            }
            JobStateTracker.INSTANCE.processStateTransitions(context, listPlus, thresholdMinutes);
            Prefs.INSTANCE.saveLastJobsRaw(context, raw);
            ListenableWorker.Result resultSuccess3 = ListenableWorker.Result.success();
            Intrinsics.checkNotNull(resultSuccess3);
            return resultSuccess3;
        } catch (Throwable th) {
            ListenableWorker.Result resultRetry = ListenableWorker.Result.retry();
            Intrinsics.checkNotNull(resultRetry);
            return resultRetry;
        }
    }
}
