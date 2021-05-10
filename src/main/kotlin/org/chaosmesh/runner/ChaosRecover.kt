package org.chaosmesh.runner

import com.amazonaws.services.simplesystemsmanagement.model.Command
import software.amazon.awsssmchaosrunner.attacks.SSMAttack


object ChaosRecover {
    private val attacks = HashMap<SSMAttack, MutableList<Command>>()

    fun getShutdownHook(): Runnable {
        return Runnable {
            attacks.forEach { (attack, commands) ->
                run {
                    commands.forEach { command ->
                        run {
                            attack.stop(command)
                        }
                    }
                }
            }
        }
    }

    fun registerAttack(ssmAttack: SSMAttack, command: Command) {
        attacks.getOrPut(ssmAttack) { mutableListOf() }.add(command)
    }

}

