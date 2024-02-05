package io.github.youngkim90.kotlinboard.controller.dto

data class PostCreateRequest(
  val title: String,
  val content: String,
  val createdBy: String,
) {}
