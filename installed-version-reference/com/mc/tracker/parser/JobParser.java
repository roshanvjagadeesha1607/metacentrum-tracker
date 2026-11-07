package com.mc.tracker.parser;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.mc.tracker.model.Job;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: JobParser.kt */
/* JADX INFO: loaded from: classes7.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/mc/tracker/parser/JobParser;", "", "()V", "jobIdPattern", "Lkotlin/text/Regex;", "parse", "", "Lcom/mc/tracker/model/Job;", "raw", "", "parseHms", "", "hms", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class JobParser {
    public static final JobParser INSTANCE = new JobParser();
    private static final Regex jobIdPattern = new Regex("^\\d+\\..+");

    private JobParser() {
    }

    public final List<Job> parse(String raw) {
        this = this;
        Intrinsics.checkNotNullParameter(raw, "raw");
        List jobs = new ArrayList();
        for (String rawLine : StringsKt.lines(raw)) {
            String line = StringsKt.trim((CharSequence) rawLine).toString();
            if (!StringsKt.isBlank(line)) {
                List<String> listSplit = new Regex("\\s+").split(line, 0);
                if (!listSplit.isEmpty() && jobIdPattern.matches(listSplit.get(0)) && listSplit.size() >= 11) {
                    String jobId = listSplit.get(0);
                    String queue = listSplit.get(2);
                    String jobName = listSplit.get(3);
                    String reqTime = listSplit.get(8);
                    String state = listSplit.get(9);
                    String elapTime = listSplit.get(10);
                    if (Intrinsics.areEqual(state, "F")) {
                        state = "C";
                    }
                    String state2 = state;
                    long reqSec = this.parseHms(reqTime);
                    long usedSec = Intrinsics.areEqual(elapTime, "--") ? 0L : this.parseHms(elapTime);
                    jobs.add(new Job(jobId, jobName, state2, queue, reqSec, usedSec, null, null, null, null, false, null, null, null, null, null, 65472, null));
                }
            }
        }
        return jobs;
    }

    private final long parseHms(String hms) {
        List p = StringsKt.split$default((CharSequence) hms, new String[]{":"}, false, 0, 6, (Object) null);
        long j = 0;
        try {
            switch (p.size()) {
                case 2:
                    j = (Long.parseLong((String) p.get(0)) * ((long) 3600)) + (Long.parseLong((String) p.get(1)) * ((long) 60));
                    break;
                case 3:
                    j = (Long.parseLong((String) p.get(0)) * ((long) 3600)) + (Long.parseLong((String) p.get(1)) * ((long) 60)) + Long.parseLong((String) p.get(2));
                    break;
            }
        } catch (Exception e) {
        }
        return j;
    }
}
