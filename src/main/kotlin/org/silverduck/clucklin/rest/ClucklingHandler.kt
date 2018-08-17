package org.silverduck.clucklin.rest

import org.silverduck.clucklin.data.Cluckling
import org.silverduck.clucklin.service.ClucklingService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.stream.Stream

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Component
class ClucklingHandler(val clucklingService: ClucklingService) {

    fun sayCluck(request: ServerRequest): Mono<ServerResponse> {
        val interval = Flux.interval(Duration.ofSeconds(1))
        val clucks = Flux.fromStream<String>(Stream.generate({ "Cluck cluck" }))
        return ok().bodyToServerSentEvents(Flux.zip(interval, clucks).map({ it.t2 }))
    }

    fun listClucklings(request: ServerRequest) = ok().body(clucklingService.list())

    fun createCluckling(request: ServerRequest): Mono<ServerResponse> {
        val cluckling = request.bodyToMono<Cluckling>()
        return ok().body(clucklingService.saveCluckling(cluckling))
    }

    fun deleteCluckling(request: ServerRequest): Mono<ServerResponse> {
        val id = request.queryParam("id")
                .orElseThrow { IllegalArgumentException("Required 'id' query parameter was not present") }

        return ok().body(clucklingService.removeCluckling(id))
    }
}