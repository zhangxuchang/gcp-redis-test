import redis.clients.jedis.JedisPool
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

object BatchTest {

    private suspend fun highConcurrencyBatchWrite(
            jedisPool: JedisPool,
            concurrencyNumber: Int = 2,
            batchSize: Int = 10
    ) {
        val time = measureTimeMillis {
            coroutineScope {
                repeat(concurrencyNumber) { taskId ->
                    launch {
                        val players = (taskId * batchSize + 1..(taskId + 1) * batchSize).map { playerId ->
                            mapOf(
                                "player:$playerId" to "player-cache-data-$playerId"
                            )
                        }
                        players.forEach { row ->
                            val playerId = row.keys.first()
//                            val playerData = row[playerId] ?: ""
//                            println("Redis single save player: $playerId")
//                            jedisPool.resource.use { jedis ->
//                                jedis.set(playerId, playerData)
//                            }
                            val data = jedisPool.resource.use { jedis ->
                                jedis.get(playerId)
                            }
                            println("Redis single read player: $data")
                        }
                    }
                }
            }
        }
        println("高并发完成：$concurrencyNumber 个并发任务，每任务 $batchSize 条，总用时 ${time}ms")
    }

    fun main() {
        println("Starting batch write:")
        // 10.19.80.21:6379
        val jedisPool = JedisPool("10.19.80.20", 6379)
        runBlocking {
            highConcurrencyBatchWrite(jedisPool, 50, 10000)
        }
    }
}

