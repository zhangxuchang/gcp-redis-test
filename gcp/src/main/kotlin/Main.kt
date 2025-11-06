
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
        else -> {
            println("No valid action type provided. Please use 'valkey' or 'cluster'.")
        }
    }
}