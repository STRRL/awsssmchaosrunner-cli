package org.chaosmesh.runner.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import software.amazon.awsssmchaosrunner.attacks.SSMAttack

internal class AttackConfigurationFromFileTest {
    private val objectMapper = ObjectMapper()

    @Test
    fun generateJson() {
        val content = SSMAttack.Companion.AttackConfiguration(
            "example-cpu-hug",
            "20s",
            10,
            "",
            listOf(),
            0,
            mapOf("networkInterfaceLatencyMs" to "")
        )
        val json = objectMapper.writeValueAsString(content)
        println(json)
    }
}

