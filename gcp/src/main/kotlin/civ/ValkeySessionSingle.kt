package civ

import io.valkey.Jedis
import io.valkey.resps.Tuple
import io.valkey.util.SafeEncoder

class ValkeySessionSingle(private var jedis: Jedis) : RedisSession {
    override fun set(key: String?, value: String?): Boolean {
        val ret = jedis.set(key, value) ?: return false
        return ret == "OK"
    }

    override fun set(key: String?, value: String?, nxxx: String?, expx: String?, time: Long): Boolean {
        // todo：nxxx ?  expx ?
        //val ret: String = jedis.set(key, value, nxxx, expx, time) ?: return false
        val ret: String = jedis.set(key, value) ?: return false

        jedis.expire(key, time)

        return ret == "OK"
    }

    override fun get(key: String?): String {
        return jedis[key]
    }

    override fun exists(key: String?): Boolean {
        return jedis.exists(key)
    }

    override fun del(vararg keys: String?): Long {
        return jedis.del(*keys)
    }

    override fun del(key: String?): Long {
        return jedis.del(key)
    }

    override fun keys(pattern: String?): Set<String> {
        return jedis.keys(pattern)
    }

    override fun expire(key: String?, seconds: Int): Long {
        return jedis.expire(key, seconds.toLong())
    }

    override fun setnx(key: String?, value: String?): Long {
        return jedis.setnx(key, value)
    }

    override fun setex(key: String?, seconds: Int, value: String?): String {
        return jedis.setex(key, seconds.toLong(), value)
    }

    override fun incr(key: String?): Long {
        return jedis.incr(key)
    }

    override fun append(key: String?, value: String?): Long {
        return jedis.append(key, value)
    }

    override fun hset(key: String?, field: String?, value: String?): Long {
        return jedis.hset(key, field, value)
    }

    override fun hset(key: String?, field: String?, bytes: ByteArray?): Long {
        return jedis.hset(SafeEncoder.encode(key), SafeEncoder.encode(field), bytes)
    }

    override fun hgetBin(key: String?, field: String?): ByteArray {
        return jedis.hget(SafeEncoder.encode(key), SafeEncoder.encode(field))
    }

    override fun hgetAllBin(key: String?): Map<ByteArray?, ByteArray?> {
        return jedis.hgetAll(SafeEncoder.encode(key))
    }

    override fun hDelBin(key: String?, field: String?): Long {
        return jedis.hdel(SafeEncoder.encode(key), SafeEncoder.encode(field))
    }

    override fun hget(key: String?, field: String?): String {
        return jedis.hget(key, field)
    }

    override fun hkeys(key: String?): Set<String> {
        return jedis.hkeys(key)
    }

    override fun hmset(key: String?, hash: Map<String?, String?>?): String {
        return jedis.hmset(key, hash)
    }

    override fun hmsetBin(key: String?, hash: Map<String?, ByteArray?>?): String? {
        val map: MutableMap<ByteArray, ByteArray> = HashMap()
        if (hash != null) {
            for ((key1, value) in hash) {
                map[SafeEncoder.encode(key1)] = value!!
            }
        }
        return jedis.hmset(SafeEncoder.encode(key), map)
    }

    override fun hincrBy(key: String?, field: String?, value: Long): Long {
        return jedis.hincrBy(key, field, value)
    }

    override fun hdel(key: String?, vararg fields: String?): Long {
        return jedis.hdel(key, *fields)
    }

    override fun hgetAll(key: String?): Map<String?, String?> {
        return jedis.hgetAll(key)
    }

    override fun zadd(key: String?, score: Double, member: String?): Long {
        return jedis.zadd(key, score, member)
    }

    override fun zrangeByScore(key: String?, min: Double, max: Double): Set<String> {
        return jedis.zrangeByScore(key, min, max).toSet()
    }

    override fun zrank(key: String?, member: String?): Long {
        return jedis.zrank(key, member)
    }

    override fun zrevrank(key: String?, member: String?): Long {
        return jedis.zrevrank(key, member)
    }

    override fun zrevrange(key: String?, start: Long, end: Long): Set<String> {
        return jedis.zrevrange(key, start, end).toSet()
    }

    override fun zrevrangeWithScores(key: String?, start: Long, end: Long): Set<Tuple> {
        return jedis.zrevrangeWithScores(key, start, end).toSet()
    }

    override fun zscore(key: String?, member: String?): Double {
        return jedis.zscore(key, member)
    }

    override fun zcard(key: String?): Long {
        return jedis.zcard(key)
    }

    override fun zrem(key: String?, member: String?): Long {
        return jedis.zrem(key, member)
    }

    override fun eval(script: String?, keys: List<String?>?, args: List<String?>?): Any {
        return jedis.eval(script, keys, args)
    }

}