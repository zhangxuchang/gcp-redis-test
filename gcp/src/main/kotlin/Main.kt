
fun main(args: Array<String>) {
    val actionType = if (args.isNotEmpty()) args[0] else "default"
    println("----------GCP Test (type:$actionType)-----------")

    when (actionType) {
        "basic" -> {
            BasicTest.main()
        }
        "cluster" -> {
            BasicClusterTest.main()
        }
        "valkey" -> {
            ValkeyClusterTest.main()
        }
        else -> {
            println("No valid action type provided. Please use 'basic' or 'cluster', or valkey.")
        }
    }
}