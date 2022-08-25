package com.keygenqt.core.base

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.Executors


class Bash {
    companion object {
        fun exec(command: String): ArrayList<String> {
            val result = arrayListOf<String>()
            /* "cmd.exe", "/c" */
            val builder = ProcessBuilder("sh", "-c", command).directory(File("./"))
            val process = builder.start()
            BufferedReader(InputStreamReader(process.inputStream)).forEachLine { result.add(it) }
            return result
        }

        fun execRunnable(command: String, listener: () -> Unit) {
            val executor = Executors.newFixedThreadPool(4)
            val builder = ProcessBuilder("sh", "-c", command).directory(File("./"))
            executor.execute {
                val p: Process = builder.start()
                p.waitFor()
                listener.invoke()
            }
        }
    }
}