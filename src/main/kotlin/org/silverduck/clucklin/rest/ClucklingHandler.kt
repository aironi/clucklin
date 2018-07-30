package org.silverduck.clucklin.rest

import org.silverduck.clucklin.data.Cluckling
import org.silverduck.clucklin.service.ClucklingService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Component
class ClucklingHandler(val clucklingService: ClucklingService) {

    fun sayCluck(request: ServerRequest) = ok().body("Cluck cluck".toMono())

    fun listClucklings(request: ServerRequest) = ok().body(clucklingService.list())

    fun createCluckling(request: ServerRequest): Mono<ServerResponse> {
        val cluckling = request.bodyToMono<Cluckling>()
        return ok().body(clucklingService.createCluckling(cluckling))
    }

    fun deleteCluckling(request: ServerRequest): Mono<ServerResponse> {
        val id = request.queryParam("id")
                .map { idParam -> idParam.toLong() }
                .orElseThrow { IllegalArgumentException("Required 'id' query parameter was not present") }

        return ok().body(clucklingService.removeCluckling(id))
    }
}