package io.github.youngkim90.kotlinboard.service.dto

import io.github.youngkim90.kotlinboard.domain.Post

data class PostCreateRequestDto(
  val title: String,
  val content: String,
  val createdBy: String,
  val tags: List<String> = emptyList(),
)

// 확장함수
fun PostCreateRequestDto.toEntity() = Post(
  title = title,
  content = content,
  createdBy = createdBy,
  tags = tags
)
