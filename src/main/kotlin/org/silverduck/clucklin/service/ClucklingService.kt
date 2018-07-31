package org.silverduck.clucklin.service

import org.silverduck.clucklin.data.Cluckling
import org.silverduck.clucklin.repository.ClucklingRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import kotlin.math.abs

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Service
class ClucklingService(val clucklingRepository: ClucklingRepository) {

    fun saveCluckling(cluckling: Mono<Cluckling>): Mono<Cluckling> = clucklingRepository.saveAll(cluckling).toMono()

    fun removeCluckling(id: String) = clucklingRepository.deleteById(id)

    fun list(): Flux<Cluckling> = clucklingRepository.findAll();


    fun findNearbyClucklings(cluckling: Cluckling): Flux<Cluckling> {
        return clucklingRepository.findAll().filter {
            abs(cluckling.position.x - it.position.x) < 2 &&
            abs(cluckling.position.y - it.position.y) < 2
        }.toFlux();

    }

}