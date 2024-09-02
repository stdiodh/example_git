package com.example.git_example.controller

import com.example.git_example.entity.Human
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/humans")

class HumanController {
    final var humans = mutableListOf<Human>()

    init {
        humans.addAll(
            listOf(
                Human(name = "짱구", age = 5, id = 1),
                Human(name = "맹구", age = 5, id = 2),
                Human(name = "철수", age = 5, id = 3),
                Human(name = "유리", age = 5, id = 4),
            )
        )
    }

    @GetMapping
    private fun getHumanList(): ResponseEntity<List<Human>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(humans)
    }

    @GetMapping("/{id}")
    private fun getHumanById(@PathVariable id: String): ResponseEntity<Any> {
        for (h in humans) {
            if (h.id == id.toLong()) {
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(h)
            }
        }
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

    @PostMapping
    fun postHuman(@RequestBody human: Human): ResponseEntity<Human> {
        humans.add(human)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(human)
    }

    @PutMapping("/{id}")
    fun putHuman(@PathVariable id: String, @RequestBody human: Human): ResponseEntity<Human> {
        var index: Int = -1
        for (h in humans) {
            if (h.id == id.toLong()) {
                index = humans.indexOf(h)
                humans[index] = human
            }
        }

        return if (index != -1) ResponseEntity.status(HttpStatus.OK).body(human) else postHuman(human)
    }

    @DeleteMapping("/{id}")
    fun deleteHuman(@PathVariable id : String) : ResponseEntity<Any> {
        humans.removeIf{ it.id == id.toLong() }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()

    }
}
