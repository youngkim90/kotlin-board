package io.github.youngkim90.kotlinboard.repository

import io.github.youngkim90.kotlinboard.domain.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long>
