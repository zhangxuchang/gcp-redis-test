import redis.clients.jedis.JedisPool
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope

class BatchTest {

    suspend fun highConcurrencyBatchWrite(
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
                            val playerData = row[playerId] ?: ""
                            println("Redis save player: $playerId")
                            jedisPool.resource.use { jedis ->
                                jedis.set(playerId, playerData)
                            }
                        }
                    }
                }
            }
        }
        println("高并发写入完成：$concurrencyNumber 个任务，每任务 $batchSize 行，总用时 ${time}ms")
    }
}

suspend fun main() {
    println("Starting batch write:")

    val test = BatchTest()
    val jedisPool = JedisPool("localhost", 6379)

    test.highConcurrencyBatchWrite(jedisPool, 2, 10)
}