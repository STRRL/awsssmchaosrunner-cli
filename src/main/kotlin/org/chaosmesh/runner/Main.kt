package org.chaosmesh.runner

import org.chaosmesh.runner.commands.Runner
import picocli.CommandLine
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val commandLine = CommandLine(Runner())
    if (commandLine.isUsageHelpRequested) {
        commandLine.usage(System.out);
        return
    }
    exitProcess(commandLine.execute(*args))
}
