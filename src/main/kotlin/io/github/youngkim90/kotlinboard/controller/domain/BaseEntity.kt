package io.github.youngkim90.kotlinboard.controller.domain

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass // 상속받는 클래스에게 매핑 정보를 전달
abstract class BaseEntity(
  val createdBy: String,
) {
  val createdAt: LocalDateTime = LocalDateTime.now()

  var updatedBy: String? = null
    protected set // class 외부에서 수정할 수 없도록 함

  var updatedAt: LocalDateTime? = null
    protected set

  fun update(updatedBy: String) {
    this.updatedBy = updatedBy
    this.updatedAt = LocalDateTime.now()
  }
}
