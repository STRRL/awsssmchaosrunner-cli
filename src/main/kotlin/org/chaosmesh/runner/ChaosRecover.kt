package org.chaosmesh.runner

import com.amazonaws.services.simplesystemsmanagement.model.Command
import mu.KotlinLogging
import software.amazon.awsssmchaosrunner.attacks.SSMAttack

private val log = KotlinLogging.logger { }

object ChaosRecover {
    private val attacks = HashMap<SSMAttack, MutableList<Command>>()

    fun getShutdownHook(): Runnable {
        return Runnable {
            attacks.forEach { (attack, commands) ->
                run {
                    commands.forEach { command ->
                        run {
                            try {
                                attack.stop(command)
                            } catch (t: Throwable) {
                                log.error(t) { "failed to stop commands" }
                            }
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

