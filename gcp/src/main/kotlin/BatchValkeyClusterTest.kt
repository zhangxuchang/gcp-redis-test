import io.valkey.HostAndPort
import io.valkey.JedisCluster
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

@Suppress("DuplicatedCode")
object BatchValkeyClusterTest {
    private suspend fun highConcurrencyBatchWrite(
            jedis: JedisCluster,
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
                            println("Valkey cluster save player: $playerId")
                            jedis.set(playerId, playerData)
                        }
                    }
                }
            }
        }
        println("高并发写入完成：$concurrencyNumber 个并发任务，每任务 $batchSize 条，总用时 ${time}ms")
    }

    fun main() {
        println("Starting Valkey cluster batch write:")
        val jedisClusterNodes: MutableSet<HostAndPort> = HashSet()
        //10.150.0.2:6379
        jedisClusterNodes.add(HostAndPort("10.150.0.2", 6379))

        //  本地 redis 不支持 cluster 模式，运行报错： ERR This instance has cluster support disabled
        //  实际使用时请连接到集群的多个节点
        val jedis = JedisCluster(jedisClusterNodes)
        runBlocking {
            highConcurrencyBatchWrite(jedis, 30, 2000)
        }
    }
}

