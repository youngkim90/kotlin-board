package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.CommentUpdateRequestDto

data class CommentUpdateRequest(
  val content: String,
  val updatedBy: String,
)

fun CommentUpdateRequest.toDto() = CommentUpdateRequestDto(
  content = this.content,
  updatedBy = this.updatedBy
)
