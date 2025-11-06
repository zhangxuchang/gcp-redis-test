import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

object BasicClusterTest {
    fun main() {
        val jedisClusterNodes: MutableSet<HostAndPort> = HashSet()
        //10.150.0.7:6379
        jedisClusterNodes.add(HostAndPort("10.150.0.7", 6379))

        //  本地 redis 不支持 cluster 模式，运行报错： ERR This instance has cluster support disabled
        //  实际使用时请连接到集群的多个节点

        val jedis = JedisCluster(jedisClusterNodes)

        jedis.sadd("cluster-planets", "Mars", "Earth", "Venus")
        val planets = jedis.smembers("cluster-planets")
        println("Planets in cluster: $planets")

    }
}

