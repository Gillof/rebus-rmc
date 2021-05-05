package rally.montecarl

import io.micronaut.openapi.annotation.OpenAPIInclude
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.tags.Tag

@OpenAPIDefinition(
        info = Info(
                title = "Rally Monte Carl",
                version = "0.0",
                description = "Rally Monte Carl Rebus API",
        )
)
@OpenAPIInclude(
        classes = [
            io.micronaut.security.endpoints.LoginController::class,
            io.micronaut.security.endpoints.LogoutController::class
        ],
        tags = [Tag(name = "Security")]
)
object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.run(Application.javaClass)
    }
}

