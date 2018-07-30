package org.silverduck.clucklin.service

import org.silverduck.clucklin.data.Cluckling
import org.silverduck.clucklin.repository.ClucklingRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Service
class ClucklingService(val clucklingRepository: ClucklingRepository) {

    fun createCluckling(cluckling: Mono<Cluckling>) : Mono<Cluckling> = clucklingRepository.saveAll(cluckling).toMono()

    fun removeCluckling(id: Long) = clucklingRepository.deleteById(id)

    fun list() : Flux<Cluckling> = clucklingRepository.findAll();
}