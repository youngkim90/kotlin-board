package io.github.youngkim90.kotlinboard.repository

import io.github.youngkim90.kotlinboard.controller.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>
