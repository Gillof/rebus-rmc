package svampyrerna.controllers

import io.micronaut.context.annotation.Value
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import svampyrerna.controllers.ScheduledJobs.ScheduledJobs.LOG

class ScheduledJobs(
    @Client(id = "ping") private val pingClient: HttpClient,
    @Value("\${ping.enabled:false}") private val pingEnabled: Boolean
) {


    @Scheduled(fixedRate = "10m")
    fun everyFiveMinutes() {
        if (pingEnabled) {
            LOG.info("Pinging /health to ensure uptime")
            val response = pingClient.toBlocking().exchange<Any>("/health")
            LOG.info("Got ${response.status} from /health")
        } else {
            LOG.debug("Ping disabled")
        }
    }

    object ScheduledJobs {
        val LOG: Logger = LoggerFactory.getLogger(ViewController::class.java)
    }
}