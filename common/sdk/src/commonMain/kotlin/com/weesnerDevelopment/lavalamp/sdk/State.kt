package com.weesnerDevelopment.lavalamp.sdk

// circle back on historical/check in time and feelings for checkin
sealed interface State {
    val feelings: List<Feeling>
    val notes: String
    val date: String

    data class Started(
        override val feelings: List<Feeling>,
        override val notes: String,
        override val date: String,
    ) : State

    data class Paused(
        override val feelings: List<Feeling>,
        override val notes: String,
        override val date: String,
        val resumeDate: String,
        val reason: String?,
    ) : State

    data class Finished(
        override val feelings: List<Feeling>,
        override val notes: String,
        override val date: String,
        val recap: String?
    ) : State

    data class Cancelled(
        override val feelings: List<Feeling>,
        override val notes: String,
        override val date: String,
        val reason: String?
    ) : State
}
