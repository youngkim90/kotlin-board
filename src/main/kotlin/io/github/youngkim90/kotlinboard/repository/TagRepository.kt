package io.github.youngkim90.kotlinboard.repository

import io.github.youngkim90.kotlinboard.controller.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long> {
  fun findByPostId(postId: Long): List<Tag>
}
