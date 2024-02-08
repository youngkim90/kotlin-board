package io.github.youngkim90.kotlinboard.controller.dto

import io.github.youngkim90.kotlinboard.service.dto.PostSearchRequestDto
import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
  @RequestParam
  val title: String?,
  @RequestParam
  val createdBy: String?,
  @RequestParam
  val tag: String?,
)

fun PostSearchRequest.toDto() = PostSearchRequestDto(
  title = this.title,
  createdBy = this.createdBy,
  tag = this.tag
)
