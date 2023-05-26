package com.weesnerDevelopment.lavalamp.sdk

sealed interface Reward : Item.Named, Item.Stateful {
    data class OneOff(
        override val id: String,
        override val name: String,
        override val notes: String,
        override val state: State,
        override val createdDate: String,
        override val lastUpdatedDate: String,
    ) : Reward

    sealed interface Usable : Reward {
        val value: UInt
    }

    data class Redeemable(
        override val id: String,
        override val name: String,
        override val notes: String,
        override val state: State,
        override val value: UInt,
        override val createdDate: String,
        override val lastUpdatedDate: String,
    ) : Usable

    data class SeasonPass(
        override val id: String,
        override val name: String,
        override val notes: String,
        override val state: State,
        override val value: UInt,
        override val createdDate: String,
        override val lastUpdatedDate: String,
    ) : Usable
}

data class RewardCategory(
    val rewards: List<Reward>,
    override val id: String,
    override val name: String,
    override val notes: String,
    override val createdDate: String,
    override val lastUpdatedDate: String,
) : Item.Named

// rewards in the reward pool are isolated from the rewards backend, theyre their own thing, so
// modifications to the rewards (not in the reward pool) do not cause changes to the ones in the
// pool
data class RewardPool(
    val all: List<Reward>,
    val redeemable: Redeemable<Reward.Usable>,
) {
    data class Redeemable<T : Reward.Usable>(
        val rewards: List<T>,
        val redeemed: List<Redeemed>
    ) {
        val left: UInt
            get() = rewards.map { reward ->
                val redeemedAmount = redeemed.find { it.id == reward.id }?.used ?: 0u
                reward.value - redeemedAmount
            }.reduce { acc, amount ->
                acc + amount
            }

        data class Redeemed(
            val id: String,
            val used: UInt
        )
    }
}
