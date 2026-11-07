package com.mc.tracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.mc.tracker.adapter.JobAdapter;
import com.mc.tracker.adapter.Row;
import com.mc.tracker.databinding.ActivityMainBinding;
import com.mc.tracker.model.Job;
import com.mc.tracker.parser.JobParser;
import com.mc.tracker.ssh.SshJobFetcher;
import com.mc.tracker.util.CrashHandler;
import com.mc.tracker.util.JobStateTracker;
import com.mc.tracker.util.PbsDate;
import com.mc.tracker.util.Prefs;
import com.mc.tracker.worker.JobCheckWorker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\fH\u0014J\b\u0010\u0011\u001a\u00020\fH\u0002J\u0016\u0010\u0012\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\b\u0010\u0016\u001a\u00020\fH\u0002J\b\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\fH\u0002J\b\u0010\u0019\u001a\u00020\fH\u0002J\b\u0010\u001a\u001a\u00020\fH\u0002J\b\u0010\u001b\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/mc/tracker/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/mc/tracker/adapter/JobAdapter;", "binding", "Lcom/mc/tracker/databinding/ActivityMainBinding;", "notifPermLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "kotlin.jvm.PlatformType", "loadCachedJobs", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "refreshNow", "renderJobs", "jobs", "", "Lcom/mc/tracker/model/Job;", "requestNotifPermissionIfNeeded", "schedulePeriodicCheck", "showAddJobDialog", "showIntervalDialog", "updateIntervalRowEnabled", "updateIntervalText", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class MainActivity extends AppCompatActivity {
    private JobAdapter adapter;
    private ActivityMainBinding binding;
    private final ActivityResultLauncher<String> notifPermLauncher;

    public MainActivity() {
        ActivityResultLauncher<String> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                MainActivity.notifPermLauncher$lambda$0((Boolean) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.notifPermLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notifPermLauncher$lambda$0(Boolean it) {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBindingInflate = ActivityMainBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityMainBindingInflate, "inflate(...)");
        this.binding = activityMainBindingInflate;
        ActivityMainBinding activityMainBinding = null;
        if (activityMainBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBindingInflate = null;
        }
        setContentView(activityMainBindingInflate.getRoot());
        this.adapter = new JobAdapter(CollectionsKt.emptyList(), new Function0<Unit>() { // from class: com.mc.tracker.MainActivity.onCreate.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                MainActivity.this.refreshNow();
            }
        });
        ActivityMainBinding activityMainBinding2 = this.binding;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding2 = null;
        }
        activityMainBinding2.recyclerJobs.setLayoutManager(new LinearLayoutManager(this));
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding3 = null;
        }
        RecyclerView recyclerView = activityMainBinding3.recyclerJobs;
        JobAdapter jobAdapter = this.adapter;
        if (jobAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            jobAdapter = null;
        }
        recyclerView.setAdapter(jobAdapter);
        String trace = CrashHandler.INSTANCE.getLastCrash(this);
        if (trace != null) {
            new AlertDialog.Builder(this).setTitle("Last crash (for debugging)").setMessage(trace).setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.onCreate$lambda$2$lambda$1(this.f$0, dialogInterface, i);
                }
            }).setCancelable(false).show();
        }
        ActivityMainBinding activityMainBinding4 = this.binding;
        if (activityMainBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding4 = null;
        }
        activityMainBinding4.buttonSettings.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
        ActivityMainBinding activityMainBinding5 = this.binding;
        if (activityMainBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding5 = null;
        }
        activityMainBinding5.buttonAddJob.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$4(this.f$0, view);
            }
        });
        ActivityMainBinding activityMainBinding6 = this.binding;
        if (activityMainBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding6 = null;
        }
        activityMainBinding6.buttonChangeInterval.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$5(this.f$0, view);
            }
        });
        updateIntervalText();
        ActivityMainBinding activityMainBinding7 = this.binding;
        if (activityMainBinding7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding7 = null;
        }
        activityMainBinding7.switchMonitoring.setChecked(Prefs.INSTANCE.getMonitoringEnabled(this));
        updateIntervalRowEnabled();
        ActivityMainBinding activityMainBinding8 = this.binding;
        if (activityMainBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding8 = null;
        }
        activityMainBinding8.switchMonitoring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                MainActivity.onCreate$lambda$6(this.f$0, compoundButton, z);
            }
        });
        ActivityMainBinding activityMainBinding9 = this.binding;
        if (activityMainBinding9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding9;
        }
        activityMainBinding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda8
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                MainActivity.onCreate$lambda$7(this.f$0);
            }
        });
        requestNotifPermissionIfNeeded();
        if (!Prefs.INSTANCE.isConfigured(this)) {
            startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
            return;
        }
        if (Prefs.INSTANCE.getMonitoringEnabled(this)) {
            schedulePeriodicCheck();
        }
        loadCachedJobs();
        refreshNow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2$lambda$1(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CrashHandler.INSTANCE.clearLastCrash(this$0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivity(new Intent(this$0, (Class<?>) SettingsActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAddJobDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$5(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showIntervalDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$6(MainActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Prefs.INSTANCE.saveMonitoringEnabled(this$0, isChecked);
        if (isChecked) {
            if (Prefs.INSTANCE.isConfigured(this$0)) {
                this$0.schedulePeriodicCheck();
            }
            Toast.makeText(this$0, "Background monitoring turned on", 0).show();
        } else {
            WorkManager.getInstance(this$0).cancelUniqueWork("job_check");
            Toast.makeText(this$0, "Background monitoring turned off", 0).show();
        }
        this$0.updateIntervalRowEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$7(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.refreshNow();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (Prefs.INSTANCE.isConfigured(this)) {
            loadCachedJobs();
        }
    }

    private final void requestNotifPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            this.notifPermLauncher.launch("android.permission.POST_NOTIFICATIONS");
        }
    }

    private final void schedulePeriodicCheck() {
        long minutes = Prefs.INSTANCE.getCheckIntervalMinutes(this);
        TimeUnit repeatIntervalTimeUnit$iv = TimeUnit.MINUTES;
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder((Class<? extends ListenableWorker>) JobCheckWorker.class, minutes, repeatIntervalTimeUnit$iv).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("job_check", ExistingPeriodicWorkPolicy.REPLACE, request);
    }

    private final void updateIntervalText() {
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.textCheckInterval.setText("Auto-check every " + Prefs.INSTANCE.getCheckIntervalMinutes(this) + " min");
    }

    private final void updateIntervalRowEnabled() {
        boolean enabled = Prefs.INSTANCE.getMonitoringEnabled(this);
        ActivityMainBinding activityMainBinding = this.binding;
        ActivityMainBinding activityMainBinding2 = null;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.buttonChangeInterval.setEnabled(enabled);
        ActivityMainBinding activityMainBinding3 = this.binding;
        if (activityMainBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding2 = activityMainBinding3;
        }
        activityMainBinding2.textCheckInterval.setAlpha(enabled ? 1.0f : 0.4f);
    }

    private final void showIntervalDialog() {
        final EditText input = new EditText(this);
        input.setInputType(2);
        input.setText(String.valueOf(Prefs.INSTANCE.getCheckIntervalMinutes(this)));
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding, padding, padding);
        new AlertDialog.Builder(this).setTitle("Auto-check interval").setMessage("How often should the app check in the background? Android doesn't allow background checks more often than every 15 minutes, so anything lower will be rounded up to 15.").setView(input).setPositiveButton("Save", new DialogInterface.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showIntervalDialog$lambda$8(input, this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showIntervalDialog$lambda$8(EditText input, MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(input, "$input");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Integer intOrNull = StringsKt.toIntOrNull(input.getText().toString());
        int minutes = RangesKt.coerceAtLeast(intOrNull != null ? intOrNull.intValue() : 15, 15);
        Prefs.INSTANCE.saveCheckIntervalMinutes(this$0, minutes);
        this$0.updateIntervalText();
        if (Prefs.INSTANCE.isConfigured(this$0)) {
            this$0.schedulePeriodicCheck();
        }
        Toast.makeText(this$0, "Will check every " + minutes + " min from now on", 0).show();
    }

    private final void showAddJobDialog() {
        final EditText input = new EditText(this);
        input.setHint("e.g. 22350980");
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding, padding, padding);
        new AlertDialog.Builder(this).setTitle("Track a job by ID").setMessage("Enter a MetaCentrum job ID - yours or a colleague's - to track it here too.").setView(input).setPositiveButton("Track", new DialogInterface.OnClickListener() { // from class: com.mc.tracker.MainActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showAddJobDialog$lambda$9(input, this, dialogInterface, i);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAddJobDialog$lambda$9(EditText input, MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(input, "$input");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String id = StringsKt.trim((CharSequence) input.getText().toString()).toString();
        if (!StringsKt.isBlank(id)) {
            Set<String> extraTrackedJobs = Prefs.INSTANCE.getExtraTrackedJobs(this$0);
            extraTrackedJobs.add(id);
            Prefs.INSTANCE.saveExtraTrackedJobs(this$0, extraTrackedJobs);
            Toast.makeText(this$0, "Tracking job " + id + " - long-press its card anytime to stop", 1).show();
            this$0.refreshNow();
        }
    }

    /* JADX INFO: renamed from: com.mc.tracker.MainActivity$refreshNow$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    static final class C00341 extends Lambda implements Function0<Unit> {
        C00341() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX WARN: Code duplicated, block: B:23:0x00fb A[Catch: all -> 0x030a, TryCatch #0 {all -> 0x030a, blocks: (B:3:0x0002, B:4:0x0070, B:6:0x0076, B:7:0x008a, B:10:0x009d, B:11:0x00b8, B:13:0x00be, B:49:0x01b1, B:16:0x00e0, B:18:0x00e6, B:24:0x00ff, B:26:0x010e, B:28:0x0114, B:35:0x0145, B:42:0x0164, B:44:0x016e, B:45:0x0175, B:47:0x0186, B:48:0x018a, B:39:0x0153, B:29:0x0123, B:31:0x012b, B:33:0x0131, B:23:0x00fb, B:50:0x01be, B:52:0x01d6, B:53:0x01f8, B:56:0x0200, B:57:0x0224, B:58:0x0238, B:60:0x023e, B:62:0x025e, B:65:0x0265, B:67:0x026b, B:69:0x0275, B:74:0x028e, B:76:0x0295, B:78:0x029f, B:79:0x02aa, B:70:0x027c, B:72:0x0282, B:80:0x02df), top: B:85:0x0002 }] */
        /* JADX WARN: Type inference failed for: r11v19, types: [T, java.util.List] */
        /* JADX WARN: Type inference failed for: r2v14, types: [T, java.util.List] */
        /* JADX WARN: Type inference failed for: r8v4, types: [T, java.util.List] */
        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            Job extraJob;
            String waitStr;
            String key;
            String computedWait;
            Job jobCopy;
            try {
                String host = Prefs.INSTANCE.getHost(MainActivity.this);
                String username = Prefs.INSTANCE.getUsername(MainActivity.this);
                String key2 = Prefs.INSTANCE.getPrivateKey(MainActivity.this);
                String password = Prefs.INSTANCE.getPassword(MainActivity.this);
                SshJobFetcher fetcher = new SshJobFetcher(host, username, key2, password, 0, 16, null);
                String raw = fetcher.fetchQstatOutput();
                Prefs.INSTANCE.saveLastJobsRaw(MainActivity.this, raw);
                final Ref.ObjectRef jobs = new Ref.ObjectRef();
                jobs.element = JobParser.INSTANCE.parse(raw);
                Map<String, String> jobWaitCache = Prefs.INSTANCE.getJobWaitCache(MainActivity.this);
                Iterable $this$map$iv = (Iterable) jobs.element;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                for (Object item$iv$iv : $this$map$iv) {
                    destination$iv$iv.add(((Job) item$iv$iv).getJobId());
                }
                List allIds = (List) destination$iv$iv;
                Map<String, SshJobFetcher.JobDetail> mapFetchJobDetails = fetcher.fetchJobDetails(allIds);
                if (!mapFetchJobDetails.isEmpty()) {
                    Iterable $this$map$iv2 = (Iterable) jobs.element;
                    Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
                    for (Object item$iv$iv2 : $this$map$iv2) {
                        Job job = (Job) item$iv$iv2;
                        String host2 = host;
                        SshJobFetcher.JobDetail d = mapFetchJobDetails.get(job.getJobId());
                        if (d == null) {
                            key = key2;
                            password = password;
                            jobCopy = job;
                        } else {
                            String it = d.getFullName();
                            if (it == null) {
                                it = job.getName();
                            } else {
                                if (!(!StringsKt.isBlank(it))) {
                                    it = null;
                                }
                                if (it == null) {
                                    it = job.getName();
                                }
                            }
                            String fullName = it;
                            if (!Intrinsics.areEqual(job.getState(), "Q") || d.getQtime() == null) {
                                key = key2;
                                computedWait = (d.getQtime() == null || d.getStime() == null) ? null : PbsDate.INSTANCE.waitDuration(d.getQtime(), d.getStime());
                            } else {
                                key = key2;
                                computedWait = PbsDate.INSTANCE.elapsedSince(d.getQtime());
                            }
                            String waitStr2 = (!Intrinsics.areEqual(job.getState(), "Q") && computedWait == null) ? jobWaitCache.get(job.getJobId()) : computedWait;
                            if (computedWait != null && !Intrinsics.areEqual(job.getState(), "Q")) {
                                jobWaitCache.put(job.getJobId(), computedWait);
                            }
                            String estimatedStartTime = d.getEstimatedStartTime();
                            if (estimatedStartTime == null) {
                                estimatedStartTime = job.getEstimatedStartTime();
                            }
                            jobCopy = job.copy((64511 & 1) != 0 ? job.jobId : null, (64511 & 2) != 0 ? job.name : fullName, (64511 & 4) != 0 ? job.state : null, (64511 & 8) != 0 ? job.queue : null, (64511 & 16) != 0 ? job.walltimeRequestedSec : 0L, (64511 & 32) != 0 ? job.walltimeUsedSec : 0L, (64511 & 64) != 0 ? job.estimatedStartTime : estimatedStartTime, (64511 & 128) != 0 ? job.submitTime : d.getQtime(), (64511 & 256) != 0 ? job.queueWaitDuration : waitStr2, (64511 & 512) != 0 ? job.owner : null, (64511 & 1024) != 0 ? job.isExtraTracked : false, (64511 & 2048) != 0 ? job.ncpus : d.getNcpus(), (64511 & 4096) != 0 ? job.ngpus : d.getNgpus(), (64511 & 8192) != 0 ? job.mem : d.getMem(), (64511 & 16384) != 0 ? job.scratchLocal : d.getScratchLocal(), (64511 & 32768) != 0 ? job.execHost : d.getExecHost());
                        }
                        destination$iv$iv2.add(jobCopy);
                        host = host2;
                        username = username;
                        key2 = key;
                        password = password;
                    }
                    jobs.element = (List) destination$iv$iv2;
                }
                Set<String> extraTrackedJobs = Prefs.INSTANCE.getExtraTrackedJobs(MainActivity.this);
                Iterable $this$map$iv3 = (Iterable) jobs.element;
                int $i$f$map = 0;
                Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv3, 10));
                Iterable $this$mapTo$iv$iv = $this$map$iv3;
                for (Object item$iv$iv3 : $this$mapTo$iv$iv) {
                    destination$iv$iv3.add(StringsKt.substringBefore$default(((Job) item$iv$iv3).getJobId(), ".", (String) null, 2, (Object) null));
                    $this$map$iv3 = $this$map$iv3;
                    $i$f$map = $i$f$map;
                    $this$mapTo$iv$iv = $this$mapTo$iv$iv;
                }
                Set existingShortIds = CollectionsKt.toSet((List) destination$iv$iv3);
                for (String extraId : extraTrackedJobs) {
                    String shortExtra = StringsKt.substringBefore$default(StringsKt.trim((CharSequence) extraId).toString(), ".", (String) null, 2, (Object) null);
                    if (!StringsKt.isBlank(shortExtra) && !existingShortIds.contains(shortExtra) && (extraJob = fetcher.fetchJobById(extraId)) != null) {
                        if (Intrinsics.areEqual(extraJob.getState(), "Q")) {
                            waitStr = extraJob.getQueueWaitDuration();
                        } else {
                            String queueWaitDuration = extraJob.getQueueWaitDuration();
                            if (queueWaitDuration == null) {
                                queueWaitDuration = jobWaitCache.get(extraJob.getJobId());
                            }
                            waitStr = queueWaitDuration;
                        }
                        if (extraJob.getQueueWaitDuration() != null && !Intrinsics.areEqual(extraJob.getState(), "Q")) {
                            jobWaitCache.put(extraJob.getJobId(), extraJob.getQueueWaitDuration());
                        }
                        jobs.element = CollectionsKt.plus((Collection<? extends Job>) jobs.element, extraJob.copy((64511 & 1) != 0 ? extraJob.jobId : null, (64511 & 2) != 0 ? extraJob.name : null, (64511 & 4) != 0 ? extraJob.state : null, (64511 & 8) != 0 ? extraJob.queue : null, (64511 & 16) != 0 ? extraJob.walltimeRequestedSec : 0L, (64511 & 32) != 0 ? extraJob.walltimeUsedSec : 0L, (64511 & 64) != 0 ? extraJob.estimatedStartTime : null, (64511 & 128) != 0 ? extraJob.submitTime : null, (64511 & 256) != 0 ? extraJob.queueWaitDuration : waitStr, (64511 & 512) != 0 ? extraJob.owner : null, (64511 & 1024) != 0 ? extraJob.isExtraTracked : true, (64511 & 2048) != 0 ? extraJob.ncpus : null, (64511 & 4096) != 0 ? extraJob.ngpus : null, (64511 & 8192) != 0 ? extraJob.mem : null, (64511 & 16384) != 0 ? extraJob.scratchLocal : null, (64511 & 32768) != 0 ? extraJob.execHost : null));
                    }
                }
                Prefs.INSTANCE.saveJobWaitCache(MainActivity.this, jobWaitCache);
                final MainActivity mainActivity = MainActivity.this;
                mainActivity.runOnUiThread(new Runnable() { // from class: com.mc.tracker.MainActivity$refreshNow$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.C00341.invoke$lambda$4(mainActivity, jobs);
                    }
                });
                JobStateTracker.INSTANCE.processStateTransitions(MainActivity.this, (List) jobs.element, Prefs.INSTANCE.getThresholdMinutes(MainActivity.this));
            } catch (Throwable e) {
                final MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.runOnUiThread(new Runnable() { // from class: com.mc.tracker.MainActivity$refreshNow$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.C00341.invoke$lambda$5(mainActivity2, e);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(MainActivity this$0, Ref.ObjectRef jobs) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(jobs, "$jobs");
            this$0.renderJobs((List) jobs.element);
            ActivityMainBinding activityMainBinding = this$0.binding;
            if (activityMainBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityMainBinding = null;
            }
            activityMainBinding.swipeRefresh.setRefreshing(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(MainActivity this$0, Throwable e) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(e, "$e");
            ActivityMainBinding activityMainBinding = this$0.binding;
            ActivityMainBinding activityMainBinding2 = null;
            if (activityMainBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityMainBinding = null;
            }
            activityMainBinding.swipeRefresh.setRefreshing(false);
            ActivityMainBinding activityMainBinding3 = this$0.binding;
            if (activityMainBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityMainBinding3 = null;
            }
            activityMainBinding3.textError.setText("Error: " + e.getMessage());
            ActivityMainBinding activityMainBinding4 = this$0.binding;
            if (activityMainBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityMainBinding2 = activityMainBinding4;
            }
            activityMainBinding2.textError.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshNow() {
        ActivityMainBinding activityMainBinding = this.binding;
        if (activityMainBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityMainBinding = null;
        }
        activityMainBinding.swipeRefresh.setRefreshing(true);
        ThreadsKt.thread((21 & 1) != 0, (21 & 2) != 0 ? false : false, (21 & 4) != 0 ? null : null, (21 & 8) != 0 ? null : null, (21 & 16) != 0 ? -1 : 0, new C00341());
    }

    private final void loadCachedJobs() {
        String raw = Prefs.INSTANCE.getLastJobsRaw(this);
        if (!StringsKt.isBlank(raw)) {
            renderJobs(JobParser.INSTANCE.parse(raw));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderJobs(List<Job> jobs) {
        List<Job> $this$filter$iv = jobs;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $this$filter$iv) {
            Job it = (Job) element$iv$iv;
            if (Intrinsics.areEqual(it.getState(), "R")) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List running = (List) destination$iv$iv;
        List<Job> $this$filter$iv2 = jobs;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $this$filter$iv2) {
            Job it2 = (Job) element$iv$iv2;
            if (Intrinsics.areEqual(it2.getState(), "Q")) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        List queued = (List) destination$iv$iv2;
        List<Job> $this$filter$iv3 = jobs;
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv3 : $this$filter$iv3) {
            Job it3 = (Job) element$iv$iv3;
            if (Intrinsics.areEqual(it3.getState(), "C")) {
                destination$iv$iv3.add(element$iv$iv3);
            }
        }
        List completed = (List) destination$iv$iv3;
        List rows = new ArrayList();
        rows.add(new Row.Header("Running (" + running.size() + ')'));
        List $this$forEach$iv = running;
        for (Object element$iv : $this$forEach$iv) {
            Job it4 = (Job) element$iv;
            rows.add(new Row.JobRow(it4));
        }
        rows.add(new Row.Header("Queued (" + queued.size() + ')'));
        List $this$forEach$iv2 = queued;
        for (Object element$iv2 : $this$forEach$iv2) {
            Job it5 = (Job) element$iv2;
            rows.add(new Row.JobRow(it5));
        }
        rows.add(new Row.Header("Completed (" + completed.size() + ')'));
        List $this$forEach$iv3 = completed;
        for (Object element$iv3 : $this$forEach$iv3) {
            Job it6 = (Job) element$iv3;
            rows.add(new Row.JobRow(it6));
        }
        JobAdapter jobAdapter = this.adapter;
        ActivityMainBinding activityMainBinding = null;
        if (jobAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            jobAdapter = null;
        }
        jobAdapter.update(rows);
        ActivityMainBinding activityMainBinding2 = this.binding;
        if (activityMainBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityMainBinding = activityMainBinding2;
        }
        activityMainBinding.textError.setVisibility(8);
    }
}
