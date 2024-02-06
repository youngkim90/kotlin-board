package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.PostCreateRequestDto

data class PostCreateRequest(
  val title: String,
  val content: String,
  val createdBy: String,
)

fun PostCreateRequest.toDto() = PostCreateRequestDto(
  title = this.title,
  content = this.content,
  createdBy = this.createdBy,
)
