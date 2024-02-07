package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.CommentCreateRequestDto

data class CommentCreateRequest(
  val content: String,
  val createdBy: String,
)

fun CommentCreateRequest.toDto() = CommentCreateRequestDto(
  content = this.content,
  createdBy = this.createdBy
)
