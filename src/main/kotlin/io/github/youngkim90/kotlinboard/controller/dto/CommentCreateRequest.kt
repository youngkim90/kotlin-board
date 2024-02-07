package io.github.youngkim90.kotlinboard.controller.dto

data class CommentCreateRequest(
  val content: String,
  val createdBy: String,
)
