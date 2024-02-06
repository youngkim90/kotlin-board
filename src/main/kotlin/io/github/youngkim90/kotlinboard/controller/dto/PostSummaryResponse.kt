package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.PostSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponse(
  val id: Long,
  val title: String,
  val createdBy: String,
  val createdAt: String,
)

fun Page<PostSummaryResponseDto>.toResponse() = PageImpl<PostSummaryResponse>(
  this.content.map { it.toResponse() },
  this.pageable,
  this.totalElements,
)

fun PostSummaryResponseDto.toResponse() = PostSummaryResponse(
  id = this.id,
  title = this.title,
  createdBy = this.createdBy,
  createdAt = this.createdAt,
)
