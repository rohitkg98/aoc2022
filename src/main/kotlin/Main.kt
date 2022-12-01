fun main(args: Array<String>) {
    val dayToExe: Map<String, Day> = mapOf(
        "1" to Day1,
    )

    dayToExe[args[0]]?.main()
}