package io.github.youngkim90.kotlinboard.repository

import io.github.youngkim90.kotlinboard.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
