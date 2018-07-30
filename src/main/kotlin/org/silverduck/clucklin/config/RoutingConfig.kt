package org.silverduck.clucklin.config

import org.silverduck.clucklin.rest.ClucklingHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Configuration
class RoutingConfig {

    @Bean
    fun cluckingRouter(clucklingHandler : ClucklingHandler) = router {
        ("/clucklings" and accept(MediaType.APPLICATION_JSON)).nest {
            GET("/", clucklingHandler::listClucklings)
            POST("/", clucklingHandler::createCluckling)
            DELETE("/", clucklingHandler::deleteCluckling)
            GET("/cluck", clucklingHandler::sayCluck)
        }
    }
}