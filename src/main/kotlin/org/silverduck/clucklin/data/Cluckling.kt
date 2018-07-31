package org.silverduck.clucklin.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Document
data class Cluckling(@Id val id: String,
                     val name: String,
                     var age: Int,
                     val gender: Gender,
                     val breed: Breed,
                     var position: Position,
                     var direction: Int,
                     var lifeCycleState: LifeCycleState) {

}