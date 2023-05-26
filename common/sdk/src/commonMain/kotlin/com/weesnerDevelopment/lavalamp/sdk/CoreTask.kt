package com.weesnerDevelopment.lavalamp.sdk

sealed interface CoreTask : Item.Named, Item.Stateful, Rewardable {
    /**
     * What [Project]s to find this in.
     */
    val foundIn: List<Project>
}

data class Task(
    override val id: String,
    override val name: String,
    override val notes: String,
    override val state: State,
    override val effort: UInt,
    override val rewards: List<Reward>,
    override val foundIn: List<Project>,
    val dueDate: String,
    val goal: List<ProgressionItem>,
    val stretchGoal: List<ProgressionItem>,
    val goalDate: String,
    override val createdDate: String,
    override val lastUpdatedDate: String,
) : CoreTask {
    data class ProgressionItem(
        override val id: String,
        override val state: State,
        override val effort: UInt,
        override val rewards: List<Reward>,
        override val createdDate: String,
        override val lastUpdatedDate: String,
    ) : Item.Stateful, Rewardable
}

/**
 * Generally pre-req or post-req things for projects.
 */
data class Checklist(
    override val id: String,
    override val name: String,
    override val notes: String,
    override val state: State,
    override val effort: UInt,
    override val rewards: List<Reward>,
    override val foundIn: List<Project>,
    override val createdDate: String,
    override val lastUpdatedDate: String,
    val items: List<ChecklistItem>,
) : CoreTask {
    interface ChecklistItem : Item.Stateful, Rewardable {
        val name: String
    }
}
