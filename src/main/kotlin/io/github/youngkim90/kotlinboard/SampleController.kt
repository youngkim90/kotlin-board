package io.github.youngkim90.kotlinboard

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {

  @GetMapping("/hello")
  fun sample(): String {
    return "Hello, Kotlin!"
  }

  @PostMapping("/sample")
  fun samplePost(@RequestParam name: String): String {
    return "Sample name : $name"
  }
}
