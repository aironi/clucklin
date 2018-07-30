package org.silverduck.clucklin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClucklinApplication

fun main(args: Array<String>) {
    runApplication<ClucklinApplication>(*args)
}
