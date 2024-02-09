package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.domain.Comment

data class CommentResponseDto(
  val id: Long,
  val content: String,
  val createdBy: String,
  val createdAt: String,
)

fun Comment.toResponseDto() = CommentResponseDto(
  id = this.id,
  content = this.content,
  createdBy = this.createdBy,
  createdAt = this.createdAt.toString()
)
