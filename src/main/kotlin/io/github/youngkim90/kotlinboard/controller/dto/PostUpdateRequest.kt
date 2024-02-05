package io.github.youngkim90.kotlinboard.controller.dto

data class PostUpdateRequest(
  val title: String,
  val content: String,
  val updatedBy: String,
) {}
