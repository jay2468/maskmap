package jay2468.maskmap.data.bean

data class Result(
    val status: String,
    val origin_addresses: List<String>,
    val destination_addresses: List<String>,
    val rows: List<Rows>,
    val error_message: String
)
