package org.chaosmesh.runner.config

import com.amazonaws.services.simplesystemsmanagement.model.Target
import com.fasterxml.jackson.databind.ObjectMapper
import software.amazon.awsssmchaosrunner.attacks.SSMAttack
import java.io.File

class AttackConfigurationFromFile(
    private val configFile: String
) : AttackConfigurationProvider {

    private val objectMapper = ObjectMapper()

    override fun fetchAttackConfiguration(): SSMAttack.Companion.AttackConfiguration {
        val temp: EmbedAttackConfiguration =
            objectMapper.readValue(File(configFile), EmbedAttackConfiguration::class.java);
        return SSMAttack.Companion.AttackConfiguration(
            temp.name,
            temp.duration,
            temp.timeoutSeconds,
            temp.cloudWatchLogGroupName,
            temp.targets,
            temp.concurrencyPercentage,
            temp.otherParameters,
        )
    }

    class EmbedAttackConfiguration {
        var name: String = ""
        var duration: String = ""
        var timeoutSeconds: Int = 120
        var cloudWatchLogGroupName: String = ""
        var targets: List<Target> = emptyList()
        var concurrencyPercentage: Int = 100
        var otherParameters: Map<String, String> = emptyMap()
    }

}