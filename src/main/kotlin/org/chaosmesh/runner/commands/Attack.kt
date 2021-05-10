package org.chaosmesh.runner.commands

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import mu.KotlinLogging
import org.apache.logging.log4j.util.Strings
import org.chaosmesh.runner.ChaosRecover
import org.chaosmesh.runner.config.AttackConfigurationFromFile
import picocli.CommandLine
import software.amazon.awsssmchaosrunner.attacks.SSMAttack
import java.time.Duration


private val log = KotlinLogging.logger { }

@CommandLine.Command(
    name = "attack",
    aliases = ["a"],
    description = ["execute a chaos"],
    mixinStandardHelpOptions = true,
)
class Attack : Runnable {
    @CommandLine.Option(
        names = ["-c", "--config"],
        required = false,
        description = ["file of attack configuration"],
    )
    var config: String = ""

    @CommandLine.Option(
        names = ["--endpoint-url"],
        required = false,
        description = ["the url of aws service"]
    )
    var endpointUrl: String = ""

    @CommandLine.Option(
        names = ["--region"],
        required = false,
        description = ["the reigion of aws service"]
    )
    var region: String = ""

    @CommandLine.Option(
        names = ["-e", "--exit-on-deadline"],
        required = false,
        description = ["automatic exit after the duration"]
    )
    var exitOnDeadline: Boolean = false

    var working: Boolean = true

    override fun run() {
        if (Strings.isEmpty(config)) {
            log.info { "you must specify a config file by -c" }
            return
        }
        Runtime.getRuntime().addShutdownHook(Thread(ChaosRecover.getShutdownHook()))

        val ssmBuilder = AWSSimpleSystemsManagementClientBuilder.standard()
        ssmBuilder.withCredentials(DefaultAWSCredentialsProviderChain())

        if (Strings.isNotEmpty(endpointUrl) || Strings.isNotEmpty(region)) {
            ssmBuilder.setEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpointUrl, region))

        }
        val ssmClient = ssmBuilder.build()
        val attackConfig = AttackConfigurationFromFile(config).fetchAttackConfiguration()
        val attack = SSMAttack.getAttack(ssmClient, attackConfig)
        val command = attack.start()
        ChaosRecover.registerAttack(attack, command)

        if (exitOnDeadline) {
            Thread.sleep(Duration.parse(attackConfig.duration).toMillis())
        } else {
            while (working) {
                Thread.sleep(oneMinutes)
            }
        }
    }

    companion object {
        private val oneMinutes = Duration.ofMinutes(1).toMillis()
    }
}