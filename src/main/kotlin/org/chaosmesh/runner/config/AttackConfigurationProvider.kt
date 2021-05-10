package org.chaosmesh.runner.config

import software.amazon.awsssmchaosrunner.attacks.SSMAttack

interface AttackConfigurationProvider {
    fun fetchAttackConfiguration(): SSMAttack.Companion.AttackConfiguration
}