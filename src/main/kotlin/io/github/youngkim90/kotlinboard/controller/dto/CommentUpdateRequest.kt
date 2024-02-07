package io.github.youngkim90.kotlinboard.controller.dto

data class CommentUpdateRequest(
  val content: String,
  val updatedBy: String,
)
