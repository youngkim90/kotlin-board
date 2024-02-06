package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.controller.domain.Post

data class PostCreateRequestDto(
  val title: String,
  val content: String,
  val createdBy: String,
)

// 확장함수
fun PostCreateRequestDto.toEntity() = Post(
  title = title,
  content = content,
  createdBy = createdBy
)
