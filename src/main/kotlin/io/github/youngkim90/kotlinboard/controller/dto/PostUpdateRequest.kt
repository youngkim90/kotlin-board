package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
  val title: String,
  val content: String,
  val updatedBy: String,
  val tags: List<String> = emptyList(),
)

fun PostUpdateRequest.toDto() = PostUpdateRequestDto(
  title = this.title,
  content = this.content,
  updatedBy = this.updatedBy,
  tags = this.tags
)
