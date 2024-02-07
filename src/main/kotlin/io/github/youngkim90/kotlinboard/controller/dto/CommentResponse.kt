package io.github.youngkim90.kotlinboard.controller.dto

data class CommentResponse(
  val id: Long,
  val content: String,
  val createdBy: String,
  val createdAt: String,
)
