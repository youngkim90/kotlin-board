package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.controller.domain.Post

data class PostDetailResponseDto(
  val id: Long,
  val title: String,
  val content: String,
  val createdBy: String,
  val createdAt: String,
  val comments: List<CommentResponseDto>,
)

fun Post.toDetailResponseDto() = PostDetailResponseDto(
  id = this.id,
  title = this.title,
  content = this.content,
  createdBy = this.createdBy,
  createdAt = this.createdAt.toString(),
  comments = comments.map { it.toResponseDto() }
)
