package com.weesnerDevelopment.lavalamp.sdk

data class Project(
    override val id: String,
    override val name: String,
    override val notes: String,
    override val state: State,
    override val effort: UInt,
    override val rewards: List<Reward>,
    val dueDate: String?,
    val goalDate: String?,
    override val createdDate: String,
    override val lastUpdatedDate: String,
) : Item.Named, Item.Stateful, Rewardable
