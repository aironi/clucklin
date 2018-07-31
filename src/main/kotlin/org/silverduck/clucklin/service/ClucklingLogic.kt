package org.silverduck.clucklin.service

import org.silverduck.clucklin.data.*
import org.silverduck.clucklin.data.LifeCycleState.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import reactor.core.publisher.toMono
import java.util.*
import java.util.logging.Logger
import java.util.stream.Stream
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author <a href="mailto:iiro.hietala@gofore.com">Iiro Hietala</a>
 */
@Component
class ClucklingLogic(val clucklingService: ClucklingService) {
    companion object {
        val LOG = Logger.getLogger(ClucklingLogic::class.java.name)
    }

    val STANDARD_DISTANCE = 1 // The standard distance that the Clucklins will move per turn
    val MATING_CHANCE = 10 // The chance that two clucklings will mate when they are nearby each other


    @Scheduled(fixedRate = 5000)
    fun logic() {
        clucklingService.list()
                .doOnNext({ turnLogic(it) })
                .subscribe()
    }

    fun turnLogic(cluckling: Cluckling) {
        ageCluckling(cluckling)
        moveCluckling(cluckling)
        applyMating(cluckling)
        clucklingService.saveCluckling(cluckling.toMono()).subscribe()
    }

    private fun ageCluckling(cluckling: Cluckling) {
        if (cluckling.lifeCycleState != CORPSE) {
            LOG.info("Aging cluckling ${cluckling.name}")
            cluckling.age = cluckling.age + 1;
        } else {
            return
        }

        if (cluckling.age > 30 && Random().nextInt(100) > 25) {
            cluckling.lifeCycleState = CORPSE
        } else if (cluckling.age > 10) {
            cluckling.lifeCycleState = ADULT
        } else if (cluckling.age > 3) {
            cluckling.lifeCycleState = YOUNGSTER
        } else {
            cluckling.lifeCycleState = EGG
        }

        LOG.info("Cluckling ${cluckling.name} is now ${cluckling.age} and is ${cluckling.lifeCycleState}")
    }

    private fun moveCluckling(cluckling: Cluckling) {
        if (Stream.of(LifeCycleState.YOUNGSTER, ADULT)
                .anyMatch{ it == cluckling.lifeCycleState }) {
            LOG.info("Moving cluckling ${cluckling.name} from ${cluckling.position}")
            cluckling.direction = Random().nextInt(365)
            cluckling.position = newPosition(cluckling.position, cluckling.direction)
            LOG.info("New position for ${cluckling.name} is ${cluckling.position}")
        }
    }

    private fun newPosition(position: Position, direction: Int): Position {
        var newX = STANDARD_DISTANCE * cos(direction.toDouble()) + position.x
        var newY = STANDARD_DISTANCE * sin(direction.toDouble()) + position.y

        return Position(newX, newY)
    }

    private fun applyMating(cluckling: Cluckling) {
        clucklingService.findNearbyClucklings(cluckling)
                .filter { it.gender != cluckling.gender && cluckling.lifeCycleState == ADULT && it.lifeCycleState == ADULT }
                .filter { Random().nextInt(100) < MATING_CHANCE }
                .doOnNext { mate(cluckling, it) }
                .subscribe()
    }

    private fun mate(cluckling: Cluckling, companion: Cluckling) {
        LOG.info("Mating ${cluckling.name} with ${companion.name}")

        val egg = Cluckling(UUID.randomUUID().toString(),
                newNameFrom(cluckling.name, companion.name),
                0,
                Gender.values().get(Random().nextInt(Gender.values().size)),
                Breed(newNameFrom(cluckling.breed.name, companion.breed.name)),
                cluckling.position,
                0,
                EGG)

        LOG.info("<3 <3 <3 New Cluckling ${egg.name} was Born! The breed is ${egg.breed} <3 <3 <3")
        clucklingService.saveCluckling(egg.toMono()).subscribe()
    }
}


fun newNameFrom(name1: String, name2: String): String {
    val nameParts1 = name1.split(" ")
    val nameParts2 = name2.split(" ")
    val namePart1 = nameParts1.get((0..nameParts1.size - 1).random())
    val namePart2 = nameParts2.get((0..nameParts2.size - 1).random())
    return namePart1 + " " + namePart2;
}

// Compliments to https://stackoverflow.com/questions/45685026/how-can-i-get-a-random-number-in-kotlin
private fun IntRange.random(): Int {
    return Random().nextInt((endInclusive + 1) - start) + start
}