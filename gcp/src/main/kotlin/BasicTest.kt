
import redis.clients.jedis.JedisPool

object BasicTest {
    fun main() {
        println("Test redis connect:")

        val jedisPool = JedisPool("localhost", 6379)

        // demo to add data and retrieve data
        jedisPool.resource.use { jedis ->
            jedis.set("mykey", "Hello, World!")
            val value = jedis.get("mykey")
            println("Retrieved value from Redis: $value")
        }


    }
}

