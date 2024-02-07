package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.PostDetailResponseDto

data class PostDetailResponse(
  val id: Long,
  val title: String,
  val content: String,
  val createdBy: String,
  val createdAt: String,
  val comments: List<CommentResponse> = emptyList(),
)

fun PostDetailResponseDto.toResponse() = PostDetailResponse(
  id = this.id,
  title = this.title,
  content = this.content,
  createdBy = this.createdBy,
  createdAt = this.createdAt
)
