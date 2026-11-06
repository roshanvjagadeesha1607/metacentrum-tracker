package com.mc.tracker.ssh

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import java.io.ByteArrayOutputStream

/**
 * Connects to MetaCentrum over SSH using a private key (no passphrase) and
 * runs `qstat -f -u <username>`, returning the raw text output.
 *
 * NOTE: StrictHostKeyChecking is disabled for simplicity. If you want stronger
 * verification, add the MetaCentrum host key fingerprint and check it here.
 */
class SshJobFetcher(
    private val host: String,
    private val username: String,
    private val privateKey: String,
    private val port: Int = 22
) {
    fun fetchQstatOutput(): String {
        val jsch = JSch()
        jsch.addIdentity("mc-key", privateKey.toByteArray(Charsets.UTF_8), null, null)

        val session = jsch.getSession(username, host, port)
        session.setConfig("StrictHostKeyChecking", "no")
        session.timeout = 15000
        session.connect(15000)

        try {
            val channel = session.openChannel("exec") as ChannelExec
            channel.setCommand("qstat -f -u $username")

            val errStream = ByteArrayOutputStream()
            channel.setErrStream(errStream)

            val inStream = channel.inputStream
            channel.connect(15000)

            val output = inStream.bufferedReader().readText()

            var waited = 0
            while (!channel.isClosed && waited < 20000) {
                Thread.sleep(100)
                waited += 100
            }
            channel.disconnect()

            return output
        } finally {
            session.disconnect()
        }
    }
}
