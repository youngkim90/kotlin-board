package io.github.youngkim90.kotlinboard.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisUtil(
  private val redisTemplate: RedisTemplate<String, Any>,
) {
  fun setData(key: String, value: Any) {
    // 레디에스 key-value 저장
    redisTemplate.opsForValue().set(key, value.toString())
  }

  fun getData(key: String): Any? {
    return redisTemplate.opsForValue().get(key)
  }
}
