import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

fun main() {
    val jedisClusterNodes: MutableSet<HostAndPort> = HashSet()
    jedisClusterNodes.add(HostAndPort("127.0.0.1", 6379))
    jedisClusterNodes.add(HostAndPort("127.0.0.1", 6379))

    //  本地 redis 不支持 cluster 模式，运行报错： ERR This instance has cluster support disabled
    //  实际使用时请连接到集群的多个节点

    val jedis = JedisCluster(jedisClusterNodes)

    jedis.sadd("cluster-planets", "Mars", "Earth", "Venus")
    val planets = jedis.smembers("cluster-planets")
    println("Planets in cluster: $planets");

}