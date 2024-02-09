package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.domain.Comment
import io.github.youngkim90.kotlinboard.domain.Post

data class CommentCreateRequestDto(
  val content: String,
  val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post) = Comment(
  content = this.content,
  createdBy = this.createdBy,
  post = post
)
