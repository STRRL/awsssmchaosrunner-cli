package org.chaosmesh.runner.commands

import picocli.CommandLine

@CommandLine.Command(
    name = "runner.sh",
    description = ["execute a chaos"],
    mixinStandardHelpOptions = true,
    subcommands = [
        Attack::class,
    ]
)
class Runner
