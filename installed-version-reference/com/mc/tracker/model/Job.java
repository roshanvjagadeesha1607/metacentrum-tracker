package com.mc.tracker.model;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Job.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b,\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B«\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0015J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u000fHÆ\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\bHÆ\u0003J\t\u00104\u001a\u00020\bHÆ\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003HÆ\u0003J»\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u00109\u001a\u00020\u000f2\b\u0010:\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010;\u001a\u00020<HÖ\u0001J\u0006\u0010=\u001a\u00020\bJ\t\u0010>\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b'\u0010&¨\u0006?"}, d2 = {"Lcom/mc/tracker/model/Job;", "", "jobId", "", "name", "state", "queue", "walltimeRequestedSec", "", "walltimeUsedSec", "estimatedStartTime", "submitTime", "queueWaitDuration", "owner", "isExtraTracked", "", "ncpus", "ngpus", "mem", "scratchLocal", "execHost", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEstimatedStartTime", "()Ljava/lang/String;", "getExecHost", "()Z", "getJobId", "getMem", "getName", "getNcpus", "getNgpus", "getOwner", "getQueue", "getQueueWaitDuration", "getScratchLocal", "getState", "getSubmitTime", "getWalltimeRequestedSec", "()J", "getWalltimeUsedSec", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "remainingSec", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final /* data */ class Job {
    private final String estimatedStartTime;
    private final String execHost;
    private final boolean isExtraTracked;
    private final String jobId;
    private final String mem;
    private final String name;
    private final String ncpus;
    private final String ngpus;
    private final String owner;
    private final String queue;
    private final String queueWaitDuration;
    private final String scratchLocal;
    private final String state;
    private final String submitTime;
    private final long walltimeRequestedSec;
    private final long walltimeUsedSec;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getJobId() {
        return this.jobId;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final String getOwner() {
        return this.owner;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final boolean getIsExtraTracked() {
        return this.isExtraTracked;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final String getNcpus() {
        return this.ncpus;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final String getNgpus() {
        return this.ngpus;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final String getMem() {
        return this.mem;
    }

    /* JADX INFO: renamed from: component15, reason: from getter */
    public final String getScratchLocal() {
        return this.scratchLocal;
    }

    /* JADX INFO: renamed from: component16, reason: from getter */
    public final String getExecHost() {
        return this.execHost;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getState() {
        return this.state;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getQueue() {
        return this.queue;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getWalltimeRequestedSec() {
        return this.walltimeRequestedSec;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getWalltimeUsedSec() {
        return this.walltimeUsedSec;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getEstimatedStartTime() {
        return this.estimatedStartTime;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getSubmitTime() {
        return this.submitTime;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final String getQueueWaitDuration() {
        return this.queueWaitDuration;
    }

    public final Job copy(String jobId, String name, String state, String queue, long walltimeRequestedSec, long walltimeUsedSec, String estimatedStartTime, String submitTime, String queueWaitDuration, String owner, boolean isExtraTracked, String ncpus, String ngpus, String mem, String scratchLocal, String execHost) {
        Intrinsics.checkNotNullParameter(jobId, "jobId");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(queue, "queue");
        return new Job(jobId, name, state, queue, walltimeRequestedSec, walltimeUsedSec, estimatedStartTime, submitTime, queueWaitDuration, owner, isExtraTracked, ncpus, ngpus, mem, scratchLocal, execHost);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Job)) {
            return false;
        }
        Job job = (Job) other;
        return Intrinsics.areEqual(this.jobId, job.jobId) && Intrinsics.areEqual(this.name, job.name) && Intrinsics.areEqual(this.state, job.state) && Intrinsics.areEqual(this.queue, job.queue) && this.walltimeRequestedSec == job.walltimeRequestedSec && this.walltimeUsedSec == job.walltimeUsedSec && Intrinsics.areEqual(this.estimatedStartTime, job.estimatedStartTime) && Intrinsics.areEqual(this.submitTime, job.submitTime) && Intrinsics.areEqual(this.queueWaitDuration, job.queueWaitDuration) && Intrinsics.areEqual(this.owner, job.owner) && this.isExtraTracked == job.isExtraTracked && Intrinsics.areEqual(this.ncpus, job.ncpus) && Intrinsics.areEqual(this.ngpus, job.ngpus) && Intrinsics.areEqual(this.mem, job.mem) && Intrinsics.areEqual(this.scratchLocal, job.scratchLocal) && Intrinsics.areEqual(this.execHost, job.execHost);
    }

    public int hashCode() {
        int iHashCode = ((((((((((this.jobId.hashCode() * 31) + this.name.hashCode()) * 31) + this.state.hashCode()) * 31) + this.queue.hashCode()) * 31) + Long.hashCode(this.walltimeRequestedSec)) * 31) + Long.hashCode(this.walltimeUsedSec)) * 31;
        String str = this.estimatedStartTime;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.submitTime;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.queueWaitDuration;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.owner;
        int iHashCode5 = (((iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31) + Boolean.hashCode(this.isExtraTracked)) * 31;
        String str5 = this.ncpus;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.ngpus;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.mem;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.scratchLocal;
        int iHashCode9 = (iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.execHost;
        return iHashCode9 + (str9 != null ? str9.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Job(jobId=").append(this.jobId).append(", name=").append(this.name).append(", state=").append(this.state).append(", queue=").append(this.queue).append(", walltimeRequestedSec=").append(this.walltimeRequestedSec).append(", walltimeUsedSec=").append(this.walltimeUsedSec).append(", estimatedStartTime=").append(this.estimatedStartTime).append(", submitTime=").append(this.submitTime).append(", queueWaitDuration=").append(this.queueWaitDuration).append(", owner=").append(this.owner).append(", isExtraTracked=").append(this.isExtraTracked).append(", ncpus=");
        sb.append(this.ncpus).append(", ngpus=").append(this.ngpus).append(", mem=").append(this.mem).append(", scratchLocal=").append(this.scratchLocal).append(", execHost=").append(this.execHost).append(')');
        return sb.toString();
    }

    public Job(String jobId, String name, String state, String queue, long walltimeRequestedSec, long walltimeUsedSec, String estimatedStartTime, String submitTime, String queueWaitDuration, String owner, boolean isExtraTracked, String ncpus, String ngpus, String mem, String scratchLocal, String execHost) {
        Intrinsics.checkNotNullParameter(jobId, "jobId");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(queue, "queue");
        this.jobId = jobId;
        this.name = name;
        this.state = state;
        this.queue = queue;
        this.walltimeRequestedSec = walltimeRequestedSec;
        this.walltimeUsedSec = walltimeUsedSec;
        this.estimatedStartTime = estimatedStartTime;
        this.submitTime = submitTime;
        this.queueWaitDuration = queueWaitDuration;
        this.owner = owner;
        this.isExtraTracked = isExtraTracked;
        this.ncpus = ncpus;
        this.ngpus = ngpus;
        this.mem = mem;
        this.scratchLocal = scratchLocal;
        this.execHost = execHost;
    }

    public /* synthetic */ Job(String str, String str2, String str3, String str4, long j, long j2, String str5, String str6, String str7, String str8, boolean z, String str9, String str10, String str11, String str12, String str13, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, j, j2, (i & 64) != 0 ? null : str5, (i & 128) != 0 ? null : str6, (i & 256) != 0 ? null : str7, (i & 512) != 0 ? null : str8, (i & 1024) != 0 ? false : z, (i & 2048) != 0 ? null : str9, (i & 4096) != 0 ? null : str10, (i & 8192) != 0 ? null : str11, (i & 16384) != 0 ? null : str12, (i & 32768) != 0 ? null : str13);
    }

    public final String getJobId() {
        return this.jobId;
    }

    public final String getName() {
        return this.name;
    }

    public final String getState() {
        return this.state;
    }

    public final String getQueue() {
        return this.queue;
    }

    public final long getWalltimeRequestedSec() {
        return this.walltimeRequestedSec;
    }

    public final long getWalltimeUsedSec() {
        return this.walltimeUsedSec;
    }

    public final String getEstimatedStartTime() {
        return this.estimatedStartTime;
    }

    public final String getSubmitTime() {
        return this.submitTime;
    }

    public final String getQueueWaitDuration() {
        return this.queueWaitDuration;
    }

    public final String getOwner() {
        return this.owner;
    }

    public final boolean isExtraTracked() {
        return this.isExtraTracked;
    }

    public final String getNcpus() {
        return this.ncpus;
    }

    public final String getNgpus() {
        return this.ngpus;
    }

    public final String getMem() {
        return this.mem;
    }

    public final String getScratchLocal() {
        return this.scratchLocal;
    }

    public final String getExecHost() {
        return this.execHost;
    }

    public final long remainingSec() {
        return RangesKt.coerceAtLeast(this.walltimeRequestedSec - this.walltimeUsedSec, 0L);
    }
}
