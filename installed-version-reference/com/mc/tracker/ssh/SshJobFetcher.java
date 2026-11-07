package com.mc.tracker.ssh;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mc.tracker.model.Job;
import com.mc.tracker.util.PbsDate;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SshJobFetcher.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0002J\u0010\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0003J \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000b0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\u0013J\u0006\u0010\u0014\u001a\u00020\u0003J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\f\u001a\u00020\u0003H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0003H\u0002J\u0018\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/mc/tracker/ssh/SshJobFetcher;", "", "host", "", "username", "privateKey", "password", "port", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", "extractJobDetail", "Lcom/mc/tracker/ssh/SshJobFetcher$JobDetail;", "raw", "fetchJobById", "Lcom/mc/tracker/model/Job;", "rawId", "fetchJobDetails", "", "jobIds", "", "fetchQstatOutput", "openSession", "Lcom/jcraft/jsch/Session;", "parseFullJob", "parseHmsColon", "", "hms", "runCommand", "session", "remoteCommand", "Companion", "JobDetail", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class SshJobFetcher {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String host;
    private final String password;
    private final int port;
    private final String privateKey;
    private final String username;

    public SshJobFetcher(String host, String username, String privateKey, String password, int port) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(privateKey, "privateKey");
        Intrinsics.checkNotNullParameter(password, "password");
        this.host = host;
        this.username = username;
        this.privateKey = privateKey;
        this.password = password;
        this.port = port;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ SshJobFetcher(String str, String str2, String str3, String str4, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        String str5;
        int i3;
        if ((i2 & 8) == 0) {
            str5 = str4;
        } else {
            str5 = "";
        }
        if ((i2 & 16) == 0) {
            i3 = i;
        } else {
            i3 = 22;
        }
        this(str, str2, str3, str5, i3);
    }

    private final Session openSession() throws JSchException {
        Session it;
        String cleanHost = INSTANCE.sanitizeHost(this.host);
        JSch jsch = new JSch();
        if (!StringsKt.isBlank(this.password)) {
            it = jsch.getSession(this.username, cleanHost, this.port);
            it.setPassword(this.password);
        } else {
            byte[] bytes = this.privateKey.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            jsch.addIdentity("mc-key", bytes, null, null);
            it = jsch.getSession(this.username, cleanHost, this.port);
        }
        it.setConfig("StrictHostKeyChecking", "no");
        it.setConfig("PreferredAuthentications", "password,publickey,keyboard-interactive");
        it.setTimeout(15000);
        it.connect(15000);
        Intrinsics.checkNotNull(it);
        return it;
    }

    private final String runCommand(Session session, String remoteCommand) throws InterruptedException, JSchException, UnsupportedEncodingException {
        Channel channelOpenChannel = session.openChannel("exec");
        Intrinsics.checkNotNull(channelOpenChannel, "null cannot be cast to non-null type com.jcraft.jsch.ChannelExec");
        ChannelExec channel = (ChannelExec) channelOpenChannel;
        channel.setCommand("bash -lc '" + StringsKt.replace$default(remoteCommand, "'", "'\\''", false, 4, (Object) null) + '\'');
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        channel.setErrStream(errStream);
        InputStream inStream = channel.getInputStream();
        channel.connect(15000);
        Intrinsics.checkNotNull(inStream);
        Reader inputStreamReader = new InputStreamReader(inStream, Charsets.UTF_8);
        String output = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
        for (int waited = 0; !channel.isClosed() && waited < 20000; waited += 100) {
            Thread.sleep(100L);
        }
        int exitStatus = channel.getExitStatus();
        channel.disconnect();
        String string = errStream.toString("UTF-8");
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String errText = StringsKt.trim((CharSequence) string).toString();
        if (StringsKt.isBlank(output)) {
            if (!StringsKt.isBlank(errText)) {
                throw new RuntimeException("Command error: " + errText);
            }
            if (exitStatus != 0) {
                throw new RuntimeException("Command exited with code " + exitStatus + " and no output");
            }
        }
        return output;
    }

    public final String fetchQstatOutput() throws Throwable {
        Session session = openSession();
        try {
            return runCommand(session, "qstat -xu " + this.username);
        } finally {
            session.disconnect();
        }
    }

    public final Job fetchJobById(String rawId) throws Throwable {
        Intrinsics.checkNotNullParameter(rawId, "rawId");
        try {
            Session session = openSession();
            try {
                String shortId = StringsKt.substringBefore$default(StringsKt.trim((CharSequence) rawId).toString(), ".", (String) null, 2, (Object) null);
                if (StringsKt.isBlank(shortId)) {
                    return null;
                }
                String raw = runCommand(session, "qstat -f " + shortId);
                return parseFullJob(raw);
            } finally {
                session.disconnect();
            }
        } catch (Exception e) {
            return null;
        }
    }

    private final Job parseFullJob(String raw) {
        String mem;
        String waitStr;
        String jobId = null;
        String name = "";
        String owner = null;
        String state = "";
        String queue = "";
        long reqWalltimeSec = 0;
        long usedWalltimeSec = 0;
        String qtime = null;
        String stime = null;
        String estimatedStart = null;
        String ncpus = null;
        String ngpus = null;
        String line = null;
        String scratchLocal = null;
        String execHost = null;
        Iterator<String> it = StringsKt.lines(raw).iterator();
        while (true) {
            mem = line;
            if (!it.hasNext()) {
                break;
            }
            String rawLine = it.next();
            String line2 = StringsKt.trim((CharSequence) rawLine).toString();
            String ngpus2 = ngpus;
            String ncpus2 = ncpus;
            String owner2 = owner;
            String estimatedStart2 = estimatedStart;
            if (StringsKt.startsWith$default(line2, "Job Id:", false, 2, (Object) null)) {
                jobId = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "Job Id:", (String) null, 2, (Object) null)).toString();
                line = mem;
                ngpus = ngpus2;
                ncpus = ncpus2;
                owner = owner2;
                estimatedStart = estimatedStart2;
            } else if (StringsKt.startsWith$default(line2, "Job_Name", false, 2, (Object) null)) {
                name = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                line = mem;
                ngpus = ngpus2;
                ncpus = ncpus2;
                owner = owner2;
                estimatedStart = estimatedStart2;
            } else {
                long usedWalltimeSec2 = usedWalltimeSec;
                if (StringsKt.startsWith$default(line2, "Job_Owner", false, 2, (Object) null)) {
                    owner = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "job_state", false, 2, (Object) null)) {
                    state = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "queue", false, 2, (Object) null)) {
                    queue = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "Resource_List.walltime", false, 2, (Object) null)) {
                    reqWalltimeSec = parseHmsColon(StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString());
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "resources_used.walltime", false, 2, (Object) null)) {
                    usedWalltimeSec = parseHmsColon(StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString());
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                } else if (StringsKt.startsWith$default(line2, "Resource_List.ncpus", false, 2, (Object) null)) {
                    ncpus = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "Resource_List.ngpus", false, 2, (Object) null)) {
                    ngpus = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "Resource_List.mem", false, 2, (Object) null)) {
                    line = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "Resource_List.scratch_local", false, 2, (Object) null)) {
                    scratchLocal = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "exec_host", false, 2, (Object) null)) {
                    execHost = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "qtime", false, 2, (Object) null)) {
                    qtime = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "stime", false, 2, (Object) null)) {
                    stime = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else if (StringsKt.startsWith$default(line2, "estimated.start_time", false, 2, (Object) null)) {
                    estimatedStart = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line2, "=", (String) null, 2, (Object) null)).toString();
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    usedWalltimeSec = usedWalltimeSec2;
                } else {
                    line = mem;
                    ngpus = ngpus2;
                    ncpus = ncpus2;
                    owner = owner2;
                    estimatedStart = estimatedStart2;
                    usedWalltimeSec = usedWalltimeSec2;
                }
            }
        }
        String owner3 = owner;
        long usedWalltimeSec3 = usedWalltimeSec;
        String estimatedStart3 = estimatedStart;
        String ncpus3 = ncpus;
        String ngpus3 = ngpus;
        if (jobId == null) {
            return null;
        }
        String id = jobId;
        if (Intrinsics.areEqual(state, "F")) {
            state = "C";
        }
        if (!Intrinsics.areEqual(state, "Q") || qtime == null) {
            waitStr = (qtime == null || stime == null) ? null : PbsDate.INSTANCE.waitDuration(qtime, stime);
        } else {
            waitStr = PbsDate.INSTANCE.elapsedSince(qtime);
        }
        String str = name;
        if (StringsKt.isBlank(str)) {
            str = id;
        }
        return new Job(id, str, state, queue, reqWalltimeSec, usedWalltimeSec3, estimatedStart3, qtime, waitStr, owner3, false, ncpus3, ngpus3, mem, scratchLocal, execHost, 1024, null);
    }

    private final long parseHmsColon(String hms) {
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

    public final Map<String, JobDetail> fetchJobDetails(List<String> jobIds) throws Throwable {
        Intrinsics.checkNotNullParameter(jobIds, "jobIds");
        if (jobIds.isEmpty()) {
            return MapsKt.emptyMap();
        }
        try {
            Session session = openSession();
            try {
                Map map = new LinkedHashMap();
                for (String fullId : jobIds) {
                    String shortId = StringsKt.substringBefore$default(fullId, ".", (String) null, 2, (Object) null);
                    try {
                        String raw = runCommand(session, "qstat -f " + shortId);
                        map.put(fullId, extractJobDetail(raw));
                    } catch (Exception e) {
                    }
                }
                session.disconnect();
                return map;
            } catch (Throwable th) {
                session.disconnect();
                throw th;
            }
        } catch (Exception e2) {
            return MapsKt.emptyMap();
        }
    }

    private final JobDetail extractJobDetail(String raw) {
        String name = null;
        String qtime = null;
        String stime = null;
        String estimatedStart = null;
        String ncpus = null;
        String ngpus = null;
        String mem = null;
        String scratchLocal = null;
        String execHost = null;
        Iterator<String> it = StringsKt.lines(raw).iterator();
        while (it.hasNext()) {
            String rawLine = it.next();
            String line = StringsKt.trim((CharSequence) rawLine).toString();
            if (StringsKt.startsWith$default(line, "Job_Name", false, 2, (Object) null)) {
                name = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
            } else {
                Iterator<String> it2 = it;
                if (StringsKt.startsWith$default(line, "qtime", false, 2, (Object) null)) {
                    qtime = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "stime", false, 2, (Object) null)) {
                    stime = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "estimated.start_time", false, 2, (Object) null)) {
                    estimatedStart = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "Resource_List.ncpus", false, 2, (Object) null)) {
                    ncpus = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "Resource_List.ngpus", false, 2, (Object) null)) {
                    ngpus = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "Resource_List.mem", false, 2, (Object) null)) {
                    mem = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "Resource_List.scratch_local", false, 2, (Object) null)) {
                    scratchLocal = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else if (StringsKt.startsWith$default(line, "exec_host", false, 2, (Object) null)) {
                    execHost = StringsKt.trim((CharSequence) StringsKt.substringAfter$default(line, "=", (String) null, 2, (Object) null)).toString();
                    it = it2;
                } else {
                    it = it2;
                }
            }
        }
        return new JobDetail(name, qtime, stime, estimatedStart, ncpus, ngpus, mem, scratchLocal, execHost);
    }

    /* JADX INFO: compiled from: SshJobFetcher.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003Ju\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010$\u001a\u00020%HÖ\u0001J\t\u0010&\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000e¨\u0006'"}, d2 = {"Lcom/mc/tracker/ssh/SshJobFetcher$JobDetail;", "", "fullName", "", "qtime", "stime", "estimatedStartTime", "ncpus", "ngpus", "mem", "scratchLocal", "execHost", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEstimatedStartTime", "()Ljava/lang/String;", "getExecHost", "getFullName", "getMem", "getNcpus", "getNgpus", "getQtime", "getScratchLocal", "getStime", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class JobDetail {
        private final String estimatedStartTime;
        private final String execHost;
        private final String fullName;
        private final String mem;
        private final String ncpus;
        private final String ngpus;
        private final String qtime;
        private final String scratchLocal;
        private final String stime;

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFullName() {
            return this.fullName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getQtime() {
            return this.qtime;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getStime() {
            return this.stime;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getEstimatedStartTime() {
            return this.estimatedStartTime;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final String getNcpus() {
            return this.ncpus;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final String getNgpus() {
            return this.ngpus;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final String getMem() {
            return this.mem;
        }

        /* JADX INFO: renamed from: component8, reason: from getter */
        public final String getScratchLocal() {
            return this.scratchLocal;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final String getExecHost() {
            return this.execHost;
        }

        public final JobDetail copy(String fullName, String qtime, String stime, String estimatedStartTime, String ncpus, String ngpus, String mem, String scratchLocal, String execHost) {
            return new JobDetail(fullName, qtime, stime, estimatedStartTime, ncpus, ngpus, mem, scratchLocal, execHost);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof JobDetail)) {
                return false;
            }
            JobDetail jobDetail = (JobDetail) other;
            return Intrinsics.areEqual(this.fullName, jobDetail.fullName) && Intrinsics.areEqual(this.qtime, jobDetail.qtime) && Intrinsics.areEqual(this.stime, jobDetail.stime) && Intrinsics.areEqual(this.estimatedStartTime, jobDetail.estimatedStartTime) && Intrinsics.areEqual(this.ncpus, jobDetail.ncpus) && Intrinsics.areEqual(this.ngpus, jobDetail.ngpus) && Intrinsics.areEqual(this.mem, jobDetail.mem) && Intrinsics.areEqual(this.scratchLocal, jobDetail.scratchLocal) && Intrinsics.areEqual(this.execHost, jobDetail.execHost);
        }

        public int hashCode() {
            String str = this.fullName;
            int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
            String str2 = this.qtime;
            int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
            String str3 = this.stime;
            int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
            String str4 = this.estimatedStartTime;
            int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
            String str5 = this.ncpus;
            int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
            String str6 = this.ngpus;
            int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
            String str7 = this.mem;
            int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
            String str8 = this.scratchLocal;
            int iHashCode8 = (iHashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
            String str9 = this.execHost;
            return iHashCode8 + (str9 != null ? str9.hashCode() : 0);
        }

        public String toString() {
            return "JobDetail(fullName=" + this.fullName + ", qtime=" + this.qtime + ", stime=" + this.stime + ", estimatedStartTime=" + this.estimatedStartTime + ", ncpus=" + this.ncpus + ", ngpus=" + this.ngpus + ", mem=" + this.mem + ", scratchLocal=" + this.scratchLocal + ", execHost=" + this.execHost + ')';
        }

        public JobDetail(String fullName, String qtime, String stime, String estimatedStartTime, String ncpus, String ngpus, String mem, String scratchLocal, String execHost) {
            this.fullName = fullName;
            this.qtime = qtime;
            this.stime = stime;
            this.estimatedStartTime = estimatedStartTime;
            this.ncpus = ncpus;
            this.ngpus = ngpus;
            this.mem = mem;
            this.scratchLocal = scratchLocal;
            this.execHost = execHost;
        }

        public /* synthetic */ JobDetail(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : str8, (i & 256) != 0 ? null : str9);
        }

        public final String getFullName() {
            return this.fullName;
        }

        public final String getQtime() {
            return this.qtime;
        }

        public final String getStime() {
            return this.stime;
        }

        public final String getEstimatedStartTime() {
            return this.estimatedStartTime;
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
    }

    /* JADX INFO: compiled from: SshJobFetcher.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\u0006"}, d2 = {"Lcom/mc/tracker/ssh/SshJobFetcher$Companion;", "", "()V", "sanitizeHost", "", "raw", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String sanitizeHost(String raw) {
            Intrinsics.checkNotNullParameter(raw, "raw");
            String h = StringsKt.trim((CharSequence) raw).toString();
            if (StringsKt.startsWith$default(h, "ssh ", false, 2, (Object) null)) {
                h = StringsKt.trim((CharSequence) StringsKt.removePrefix(h, (CharSequence) "ssh ")).toString();
            }
            if (StringsKt.contains$default((CharSequence) h, (CharSequence) "@", false, 2, (Object) null)) {
                h = StringsKt.substringAfterLast$default(h, "@", (String) null, 2, (Object) null);
            }
            return StringsKt.trim((CharSequence) h).toString();
        }
    }
}
