package io.github.youngkim90.kotlinboard.service.dto

data class PostUpdateRequestDto(
  val title: String,
  val content: String,
  val updatedBy: String,
  val tags: List<String> = emptyList(),
)
