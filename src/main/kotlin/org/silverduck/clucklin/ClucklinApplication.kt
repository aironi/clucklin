package org.silverduck.clucklin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ClucklinApplication

fun main(args: Array<String>) {
    runApplication<ClucklinApplication>(*args)
}