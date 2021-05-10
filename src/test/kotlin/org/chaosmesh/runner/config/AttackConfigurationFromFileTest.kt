package org.chaosmesh.runner.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import software.amazon.awsssmchaosrunner.attacks.SSMAttack
import java.time.Duration

internal class AttackConfigurationFromFileTest {
    private val objectMapper = ObjectMapper()

    @Test
    fun generateExample() {
        val content = SSMAttack.Companion.AttackConfiguration(
            "NetworkInterfaceLatencyAttack",
            Duration.ofSeconds(20).toString(),
            10,
            "",
            listOf(),
            100,
            mapOf("networkInterfaceLatencyMs" to "")
        )
        val json = objectMapper.writeValueAsString(content)
        println(json)
    }
}

