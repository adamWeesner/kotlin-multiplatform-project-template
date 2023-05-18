package com.weesnerDevelopment.lavalamp.sdk

interface Rewardable {
    /**
     * How much effort points its going to take.
     */
    val effort: UInt

    /**
     * List of rewards upon completion. If no rewards, a cute quote, or animation works as well.
     */
    val rewards: List<Reward>
}
