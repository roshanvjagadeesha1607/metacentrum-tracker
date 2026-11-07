package com.mc.tracker.adapter;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.mc.tracker.model.Job;
import com.mc.tracker.util.Prefs;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.debug.internal.DebugCoroutineInfoImplKt;

/* JADX INFO: compiled from: JobAdapter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u0016\u0017\u0018B#\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000bH\u0016J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\u0014\u0010\u0014\u001a\u00020\b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/mc/tracker/adapter/JobAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "rows", "", "Lcom/mc/tracker/adapter/Row;", "onDataChanged", "Lkotlin/Function0;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "update", "newRows", "Companion", "HeaderVH", "JobVH", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_JOB = 1;
    private final Function0<Unit> onDataChanged;
    private List<? extends Row> rows;

    public /* synthetic */ JobAdapter(List list, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? new Function0<Unit>() { // from class: com.mc.tracker.adapter.JobAdapter.1
            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
            }
        } : anonymousClass1);
    }

    public JobAdapter(List<? extends Row> rows, Function0<Unit> onDataChanged) {
        Intrinsics.checkNotNullParameter(rows, "rows");
        Intrinsics.checkNotNullParameter(onDataChanged, "onDataChanged");
        this.rows = rows;
        this.onDataChanged = onDataChanged;
    }

    public final void update(List<? extends Row> newRows) {
        Intrinsics.checkNotNullParameter(newRows, "newRows");
        this.rows = newRows;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        Row row = this.rows.get(position);
        if (row instanceof Row.Header) {
            return 0;
        }
        if (row instanceof Row.JobRow) {
            return 1;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            View v = inflater.inflate(R.layout.simple_list_item_1, parent, false);
            Intrinsics.checkNotNull(v);
            return new HeaderVH(v);
        }
        View v2 = inflater.inflate(com.mc.tracker.R.layout.item_job, parent, false);
        Intrinsics.checkNotNull(v2);
        return new JobVH(v2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Row row = this.rows.get(position);
        if (!(row instanceof Row.Header)) {
            if (row instanceof Row.JobRow) {
                ((JobVH) holder).bind(((Row.JobRow) row).getJob(), this.onDataChanged);
                return;
            }
            return;
        }
        ((HeaderVH) holder).bind(((Row.Header) row).getTitle());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.rows.size();
    }

    /* JADX INFO: compiled from: JobAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/mc/tracker/adapter/JobAdapter$HeaderVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "text", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "bind", "", "title", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class HeaderVH extends RecyclerView.ViewHolder {
        private final TextView text;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public HeaderVH(View view) {
            super(view);
            Intrinsics.checkNotNullParameter(view, "view");
            this.text = (TextView) view.findViewById(R.id.text1);
        }

        public final void bind(String title) {
            Intrinsics.checkNotNullParameter(title, "title");
            this.text.setText(title);
            this.text.setTypeface(null, 1);
            this.text.setTextSize(18.0f);
            this.text.setTextColor(this.itemView.getContext().getColor(com.mc.tracker.R.color.brandPrimaryDark));
            this.text.setPadding(24, 24, 24, 8);
        }
    }

    /* JADX INFO: compiled from: JobAdapter.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/mc/tracker/adapter/JobAdapter$JobVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "badge", "Landroid/widget/TextView;", "kotlin.jvm.PlatformType", "card", "Lcom/google/android/material/card/MaterialCardView;", "details", "name", "watchButton", "bind", "", "job", "Lcom/mc/tracker/model/Job;", "onDataChanged", "Lkotlin/Function0;", "showJobDetailDialog", "ctx", "Landroid/content/Context;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class JobVH extends RecyclerView.ViewHolder {
        private final TextView badge;
        private final MaterialCardView card;
        private final TextView details;
        private final TextView name;
        private final TextView watchButton;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JobVH(View view) {
            super(view);
            Intrinsics.checkNotNullParameter(view, "view");
            this.card = (MaterialCardView) view;
            this.name = (TextView) view.findViewById(com.mc.tracker.R.id.textJobName);
            this.details = (TextView) view.findViewById(com.mc.tracker.R.id.textJobDetails);
            this.badge = (TextView) view.findViewById(com.mc.tracker.R.id.textStatusBadge);
            this.watchButton = (TextView) view.findViewById(com.mc.tracker.R.id.buttonWatch);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code duplicated, block: B:39:0x0147  */
        /* JADX WARN: Code duplicated, block: B:46:0x0159  */
        /* JADX WARN: Code duplicated, block: B:66:0x0218  */
        /* JADX WARN: Code duplicated, block: B:68:0x0224  */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        public final void bind(final Job job, final Function0<Unit> onDataChanged) {
            Triple triple;
            String queueWaitDuration;
            String it;
            Intrinsics.checkNotNullParameter(job, "job");
            Intrinsics.checkNotNullParameter(onDataChanged, "onDataChanged");
            this.name.setText(job.getName() + "  (" + job.getJobId() + ')');
            long j = 3600;
            long usedH = job.getWalltimeUsedSec() / j;
            long reqH = job.getWalltimeRequestedSec() / j;
            String base = "Queue: " + job.getQueue() + "   Walltime: " + usedH + "h / " + reqH + 'h';
            List lines = CollectionsKt.mutableListOf(base);
            if (job.isExtraTracked()) {
                String it2 = job.getOwner();
                if (it2 == null || (it = " • " + it2) == null) {
                    it = "";
                }
                lines.add(0, "Manually tracked" + it + " (long-press to stop)");
            }
            String ownerText = job.getState();
            boolean z = true;
            switch (ownerText.hashCode()) {
                case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL /* 67 */:
                    if (ownerText.equals("C")) {
                        queueWaitDuration = job.getQueueWaitDuration();
                        if (queueWaitDuration != null && !StringsKt.isBlank(queueWaitDuration)) {
                            z = false;
                        }
                        if (!z) {
                            lines.add("Waited before start: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
                case 81:
                    if (ownerText.equals("Q")) {
                        String estimatedStartTime = job.getEstimatedStartTime();
                        if (!(estimatedStartTime == null || StringsKt.isBlank(estimatedStartTime))) {
                            lines.add("Estimated start: " + job.getEstimatedStartTime());
                        }
                        String queueWaitDuration2 = job.getQueueWaitDuration();
                        if (queueWaitDuration2 != null && !StringsKt.isBlank(queueWaitDuration2)) {
                            z = false;
                        }
                        if (!z) {
                            lines.add("Waited so far: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
                case 82:
                    if (ownerText.equals("R")) {
                        queueWaitDuration = job.getQueueWaitDuration();
                        if (queueWaitDuration != null) {
                            z = false;
                        }
                        if (!z) {
                            lines.add("Waited before start: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
            }
            this.details.setText(CollectionsKt.joinToString$default(lines, "\n", null, null, 0, null, null, 62, null));
            final Context ctx = this.itemView.getContext();
            switch (job.getState()) {
                case "C":
                    triple = new Triple("COMPLETED", Integer.valueOf(com.mc.tracker.R.color.colorCompleted), Integer.valueOf(com.mc.tracker.R.color.colorCompletedBg));
                    break;
                case "H":
                    triple = new Triple("HELD", Integer.valueOf(com.mc.tracker.R.color.colorHeld), Integer.valueOf(com.mc.tracker.R.color.colorHeldBg));
                    break;
                case "Q":
                    triple = new Triple("QUEUED", Integer.valueOf(com.mc.tracker.R.color.colorQueued), Integer.valueOf(com.mc.tracker.R.color.colorQueuedBg));
                    break;
                case "R":
                    triple = new Triple(DebugCoroutineInfoImplKt.RUNNING, Integer.valueOf(com.mc.tracker.R.color.colorRunning), Integer.valueOf(com.mc.tracker.R.color.colorRunningBg));
                    break;
                default:
                    String state = job.getState();
                    if (StringsKt.isBlank(state)) {
                        state = "?";
                    }
                    triple = new Triple(state, Integer.valueOf(com.mc.tracker.R.color.colorQueued), Integer.valueOf(com.mc.tracker.R.color.colorQueuedBg));
                    break;
            }
            String label = (String) triple.component1();
            int strokeColor = ((Number) triple.component2()).intValue();
            int bgColor = ((Number) triple.component3()).intValue();
            this.badge.setText(label);
            this.badge.getBackground().mutate().setTint(ctx.getColor(strokeColor));
            this.card.setStrokeColor(ctx.getColor(strokeColor));
            this.card.setCardBackgroundColor(ctx.getColor(bgColor));
            if (Intrinsics.areEqual(job.getState(), "Q")) {
                this.watchButton.setVisibility(0);
                bind$refreshWatchIcon(ctx, job, this);
                this.watchButton.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.adapter.JobAdapter$JobVH$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        JobAdapter.JobVH.bind$lambda$2(ctx, job, this, view);
                    }
                });
            } else {
                this.watchButton.setVisibility(8);
                this.watchButton.setOnClickListener(null);
            }
            if (job.isExtraTracked()) {
                this.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.mc.tracker.adapter.JobAdapter$JobVH$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return JobAdapter.JobVH.bind$lambda$4(ctx, job, onDataChanged, view);
                    }
                });
            } else {
                this.itemView.setOnLongClickListener(null);
            }
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.mc.tracker.adapter.JobAdapter$JobVH$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    JobAdapter.JobVH.bind$lambda$5(this.f$0, ctx, job, view);
                }
            });
        }

        private static final void bind$refreshWatchIcon(Context ctx, Job $job, JobVH this$0) {
            Prefs prefs = Prefs.INSTANCE;
            Intrinsics.checkNotNull(ctx);
            boolean isWatched = prefs.getWatchedJobs(ctx).contains($job.getJobId());
            this$0.watchButton.getBackground().mutate().setTint(ctx.getColor(isWatched ? com.mc.tracker.R.color.colorRunning : com.mc.tracker.R.color.colorCompleted));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$2(Context $ctx, Job job, JobVH this$0, View it) {
            boolean nowWatched;
            Intrinsics.checkNotNullParameter(job, "$job");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Prefs prefs = Prefs.INSTANCE;
            Intrinsics.checkNotNull($ctx);
            Set<String> watchedJobs = prefs.getWatchedJobs($ctx);
            if (watchedJobs.contains(job.getJobId())) {
                watchedJobs.remove(job.getJobId());
                nowWatched = false;
            } else {
                watchedJobs.add(job.getJobId());
                nowWatched = true;
            }
            Prefs.INSTANCE.saveWatchedJobs($ctx, watchedJobs);
            bind$refreshWatchIcon($ctx, job, this$0);
            Toast.makeText($ctx, nowWatched ? "Will notify you when this job starts" : "Removed from start notifications", 0).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean bind$lambda$4(final Context $ctx, final Job job, final Function0 onDataChanged, View it) {
            Intrinsics.checkNotNullParameter(job, "$job");
            Intrinsics.checkNotNullParameter(onDataChanged, "$onDataChanged");
            new AlertDialog.Builder($ctx).setTitle("Stop tracking this job?").setMessage(job.getName() + " (" + job.getJobId() + ") will no longer be tracked.").setPositiveButton("Stop tracking", new DialogInterface.OnClickListener() { // from class: com.mc.tracker.adapter.JobAdapter$JobVH$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    JobAdapter.JobVH.bind$lambda$4$lambda$3($ctx, onDataChanged, job, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$4$lambda$3(Context $ctx, Function0 onDataChanged, final Job job, DialogInterface dialogInterface, int i) {
            Intrinsics.checkNotNullParameter(onDataChanged, "$onDataChanged");
            Intrinsics.checkNotNullParameter(job, "$job");
            Prefs prefs = Prefs.INSTANCE;
            Intrinsics.checkNotNull($ctx);
            Set<String> extraTrackedJobs = prefs.getExtraTrackedJobs($ctx);
            CollectionsKt.removeAll(extraTrackedJobs, new Function1<String, Boolean>() { // from class: com.mc.tracker.adapter.JobAdapter$JobVH$bind$3$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(StringsKt.startsWith$default(job.getJobId(), StringsKt.substringBefore$default(StringsKt.trim((CharSequence) it).toString(), ".", (String) null, 2, (Object) null), false, 2, (Object) null));
                }
            });
            Prefs.INSTANCE.saveExtraTrackedJobs($ctx, extraTrackedJobs);
            onDataChanged.invoke();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$5(JobVH this$0, Context $ctx, Job job, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(job, "$job");
            Intrinsics.checkNotNull($ctx);
            this$0.showJobDetailDialog($ctx, job);
        }

        /* JADX WARN: Code duplicated, block: B:32:0x00f5  */
        /* JADX WARN: Code duplicated, block: B:38:0x0106  */
        /* JADX WARN: Code duplicated, block: B:40:0x0109  */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        private final void showJobDetailDialog(Context ctx, Job job) {
            String queueWaitDuration;
            boolean z;
            long j = 3600;
            long usedH = job.getWalltimeUsedSec() / j;
            long reqH = job.getWalltimeRequestedSec() / j;
            List lines = new ArrayList();
            lines.add("Status: " + job.getState());
            lines.add("Queue: " + job.getQueue());
            lines.add("Walltime: " + usedH + "h / " + reqH + 'h');
            String state = job.getState();
            switch (state.hashCode()) {
                case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL /* 67 */:
                    if (state.equals("C")) {
                        queueWaitDuration = job.getQueueWaitDuration();
                        if (queueWaitDuration != null || StringsKt.isBlank(queueWaitDuration)) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (!z) {
                            lines.add("Waited before start: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
                case 81:
                    if (state.equals("Q")) {
                        String estimatedStartTime = job.getEstimatedStartTime();
                        if (!(estimatedStartTime == null || StringsKt.isBlank(estimatedStartTime))) {
                            lines.add("Estimated start: " + job.getEstimatedStartTime());
                        }
                        String queueWaitDuration2 = job.getQueueWaitDuration();
                        if (!(queueWaitDuration2 == null || StringsKt.isBlank(queueWaitDuration2))) {
                            lines.add("Waited so far: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
                case 82:
                    if (state.equals("R")) {
                        queueWaitDuration = job.getQueueWaitDuration();
                        if (queueWaitDuration != null) {
                            z = true;
                        } else {
                            z = true;
                        }
                        if (!z) {
                            lines.add("Waited before start: ~" + job.getQueueWaitDuration());
                        }
                    }
                    break;
            }
            String ncpus = job.getNcpus();
            if (!(ncpus == null || StringsKt.isBlank(ncpus))) {
                lines.add("CPUs: " + job.getNcpus());
            }
            String ngpus = job.getNgpus();
            if (!(ngpus == null || StringsKt.isBlank(ngpus))) {
                lines.add("GPUs: " + job.getNgpus());
            }
            String mem = job.getMem();
            if (!(mem == null || StringsKt.isBlank(mem))) {
                lines.add("Memory: " + job.getMem());
            }
            String scratchLocal = job.getScratchLocal();
            if (!(scratchLocal == null || StringsKt.isBlank(scratchLocal))) {
                lines.add("Scratch (local): " + job.getScratchLocal());
            }
            String execHost = job.getExecHost();
            if (!(execHost == null || StringsKt.isBlank(execHost))) {
                lines.add("Running on: " + job.getExecHost());
            }
            String submitTime = job.getSubmitTime();
            if (!(submitTime == null || StringsKt.isBlank(submitTime))) {
                lines.add("Submitted: " + job.getSubmitTime());
            }
            if (job.isExtraTracked()) {
                String owner = job.getOwner();
                if (!(owner == null || StringsKt.isBlank(owner))) {
                    lines.add("Owner: " + job.getOwner());
                }
            }
            new AlertDialog.Builder(ctx).setTitle(job.getName() + " (" + job.getJobId() + ')').setMessage(CollectionsKt.joinToString$default(lines, "\n", null, null, 0, null, null, 62, null)).setPositiveButton("Close", (DialogInterface.OnClickListener) null).show();
        }
    }
}
