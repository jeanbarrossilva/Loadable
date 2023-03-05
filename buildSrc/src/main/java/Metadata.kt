object Metadata {
    const val GROUP = "com.jeanbarrossilva.loadable"
    const val ARTIFACT = "loadable"
    const val NAMESPACE = GROUP

    fun namespace(suffix: String): String {
        return "$NAMESPACE.$suffix"
    }
}
