package io.github.youngkim90.kotlinboard.domain

import io.github.youngkim90.kotlinboard.Exception.CommentNotUpdatableException
import io.github.youngkim90.kotlinboard.service.dto.CommentUpdateRequestDto
import jakarta.persistence.*

@Entity
@Table(indexes = [Index(name = "idx_post_id", columnList = "post_id")])
class Comment(
  content: String,
  post: Post,
  createdBy: String,
) : BaseEntity(createdBy) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L

  var content: String = content
    protected set

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 외래키 제약조건 제거
  var post: Post = post
    protected set

  fun update(updateRequestDto: CommentUpdateRequestDto) {
    if (updateRequestDto.updatedBy != this.createdBy) throw CommentNotUpdatableException()

    this.content = updateRequestDto.content
    super.updatedBy(updateRequestDto.updatedBy)
  }
}
