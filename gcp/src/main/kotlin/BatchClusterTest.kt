import kotlin.system.measureTimeMillis
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

@Suppress("DuplicatedCode")
object BatchClusterTest {

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
                            println("Redis cluster save player: $playerId")
                            jedis.set(playerId, playerData)
//                            val data = jedis.get(playerId)
//                            println("Redis cluster read player: $data")
                        }
                    }
                }
            }
        }
        println("高并发完成：$concurrencyNumber 个并发任务，每任务 $batchSize 条，总用时 ${time}ms")
    }

    fun main() {
        println("Starting cluster batch write:")
        val jedisClusterNodes: MutableSet<HostAndPort> = HashSet()
        //10.150.0.12:6379
        jedisClusterNodes.add(HostAndPort("10.150.0.12", 6379))

        //  本地 redis 不支持 cluster 模式，运行报错： ERR This instance has cluster support disabled
        //  实际使用时请连接到集群的多个节点
        val jedis = JedisCluster(jedisClusterNodes)
        runBlocking {
            highConcurrencyBatchWrite(jedis, 30, 2000)
        }
    }
}

