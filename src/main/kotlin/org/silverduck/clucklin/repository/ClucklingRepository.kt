package org.silverduck.clucklin.repository

import org.silverduck.clucklin.data.Cluckling
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Repository
interface ClucklingRepository : ReactiveCrudRepository<Cluckling, Long>