package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.CommentResponseDto

data class CommentResponse(
  val id: Long,
  val content: String,
  val createdBy: String,
  val createdAt: String,
)

fun CommentResponseDto.toResponse() = CommentResponse(
  id = this.id,
  content = this.content,
  createdBy = this.createdBy,
  createdAt = this.createdAt,
)
