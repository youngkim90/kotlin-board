package io.github.youngkim90.kotlinboard.service.dto

data class CommentUpdateRequestDto(
  val content: String,
  val updatedBy: String,
)
