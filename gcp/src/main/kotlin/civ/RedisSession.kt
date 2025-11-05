package civ

import redis.clients.jedis.resps.Tuple

interface RedisSession {
    fun set(key: String?, value: String?): Boolean

    fun set(key: String?, value: String?, nxxx: String?, expx: String?, time: Long): Boolean

    fun get(key: String?): String?

    fun exists(key: String?): Boolean?

    fun del(vararg keys: String?): Long?

    fun del(key: String?): Long?

    fun keys(pattern: String?): Set<String?>?

    fun expire(key: String?, seconds: Int): Long?

    fun setnx(key: String?, value: String?): Long?

    fun setex(key: String?, seconds: Int, value: String?): String?

    fun incr(key: String?): Long?

    fun append(key: String?, value: String?): Long?

    fun hset(key: String?, field: String?, value: String?): Long?

    fun hset(key: String?, field: String?, bytes: ByteArray?): Long?

    fun hgetBin(key: String?, field: String?): ByteArray?
    fun hgetAllBin(key: String?): Map<ByteArray?, ByteArray?>?
    fun hDelBin(key: String?, field: String?): Long?

    fun hget(key: String?, field: String?): String?

    fun hkeys(key: String?): Set<String?>?

    fun hmset(key: String?, hash: Map<String?, String?>?): String?

    fun hmsetBin(key: String?, hash: Map<String?, ByteArray?>?): String?

    fun hincrBy(key: String?, field: String?, value: Long): Long?

    fun hdel(key: String?, vararg fields: String?): Long?

    fun hgetAll(key: String?): Map<String?, String?>?

    fun zadd(key: String?, score: Double, member: String?): Long?

    fun zrangeByScore(key: String?, min: Double, max: Double): Set<String?>?

    fun zrank(key: String?, member: String?): Long?

    fun zrevrank(key: String?, member: String?): Long?

    fun zrevrange(key: String?, start: Long, end: Long): Set<String?>?
    fun zrevrangeWithScores(key: String?, start: Long, end: Long): Set<Tuple?>?

    fun zscore(key: String?, member: String?): Double?

    fun zcard(key: String?): Long

    fun zrem(key: String?, member: String?): Long?

    fun eval(script: String?, keys: List<String?>?, args: List<String?>?): Any?
}