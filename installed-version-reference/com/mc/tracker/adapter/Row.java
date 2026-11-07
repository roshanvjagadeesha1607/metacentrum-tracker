package com.mc.tracker.adapter;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.model.Job;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JobAdapter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lcom/mc/tracker/adapter/Row;", "", "()V", "Header", "JobRow", "Lcom/mc/tracker/adapter/Row$Header;", "Lcom/mc/tracker/adapter/Row$JobRow;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class Row {
    public /* synthetic */ Row(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: JobAdapter.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/mc/tracker/adapter/Row$Header;", "Lcom/mc/tracker/adapter/Row;", "title", "", "(Ljava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class Header extends Row {
        private final String title;

        public static /* synthetic */ Header copy$default(Header header, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = header.title;
            }
            return header.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getTitle() {
            return this.title;
        }

        public final Header copy(String title) {
            Intrinsics.checkNotNullParameter(title, "title");
            return new Header(title);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Header) && Intrinsics.areEqual(this.title, ((Header) other).title);
        }

        public int hashCode() {
            return this.title.hashCode();
        }

        public String toString() {
            return "Header(title=" + this.title + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Header(String title) {
            super(null);
            Intrinsics.checkNotNullParameter(title, "title");
            this.title = title;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    private Row() {
    }

    /* JADX INFO: compiled from: JobAdapter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/mc/tracker/adapter/Row$JobRow;", "Lcom/mc/tracker/adapter/Row;", "job", "Lcom/mc/tracker/model/Job;", "(Lcom/mc/tracker/model/Job;)V", "getJob", "()Lcom/mc/tracker/model/Job;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class JobRow extends Row {
        private final Job job;

        public static /* synthetic */ JobRow copy$default(JobRow jobRow, Job job, int i, Object obj) {
            if ((i & 1) != 0) {
                job = jobRow.job;
            }
            return jobRow.copy(job);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Job getJob() {
            return this.job;
        }

        public final JobRow copy(Job job) {
            Intrinsics.checkNotNullParameter(job, "job");
            return new JobRow(job);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof JobRow) && Intrinsics.areEqual(this.job, ((JobRow) other).job);
        }

        public int hashCode() {
            return this.job.hashCode();
        }

        public String toString() {
            return "JobRow(job=" + this.job + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JobRow(Job job) {
            super(null);
            Intrinsics.checkNotNullParameter(job, "job");
            this.job = job;
        }

        public final Job getJob() {
            return this.job;
        }
    }
}
