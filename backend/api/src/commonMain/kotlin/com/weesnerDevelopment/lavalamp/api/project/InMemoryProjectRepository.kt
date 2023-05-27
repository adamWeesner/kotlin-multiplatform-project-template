package com.weesnerDevelopment.lavalamp.api.project

import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project

object InMemoryProjectRepository : ProjectRepository {
    private val projects: MutableList<Project> = mutableListOf()

    override suspend fun getAll(): Either<List<Project>, ProjectRepositoryError.GetAll> {
        if (projects.isEmpty())
            return Either.failure(ProjectRepositoryError.GetAll.NoProjects)

        return Either.success(projects)
    }

    override suspend fun getAll(
        response: Either<List<Project>, ProjectRepositoryError.GetAll>.() -> Unit
    ) {
        response(getAll())
    }

    override suspend fun get(id: String): Either<Project, ProjectRepositoryError.Get> {
        val foundProject = projects.find { it.id == id }

        if (foundProject == null)
            return Either.failure(ProjectRepositoryError.Get.ProjectNotFound)

        return Either.success(foundProject)
    }

    override suspend fun get(
        id: String,
        response: Either<Project, ProjectRepositoryError.Get>.() -> Unit
    ) {
        response(get(id))
    }

    override suspend fun add(
        new: Project
    ): Either<Project, ProjectRepositoryError.Add> {
        val added = projects.add(new)

        if (!added)
            return Either.failure(ProjectRepositoryError.Add.ActionFailed)

        return Either.success(new)
    }

    override suspend fun add(
        new: Project,
        response: Either<Project, ProjectRepositoryError.Add>.() -> Unit
    ) {
        response(add(new))
    }

    override suspend fun update(
        updated: Project
    ): Either<Project, ProjectRepositoryError.Update> {
        val foundProject = projects.find { it.id == updated.id }
        val index = projects.indexOf(foundProject)

        if (foundProject == null)
            return Either.failure(ProjectRepositoryError.Update.ProjectNotFound)

        val deleted = projects.remove(foundProject)

        if (!deleted)
            return Either.failure(ProjectRepositoryError.Update.ActionFailed)

        val updatedProject = foundProject.copy(
            name = updated.name,
            notes = updated.notes,
            state = updated.state,
            effort = updated.effort,
            rewards = updated.rewards,
            dueDate = updated.dueDate,
            goalDate = updated.goalDate,
            lastUpdatedDate = updated.lastUpdatedDate
        )

        projects.add(index, updatedProject)
        return Either.success(updatedProject)
    }

    override suspend fun update(
        updated: Project,
        response: Either<Project, ProjectRepositoryError.Update>.() -> Unit
    ) {
        response(update(updated))
    }

    override suspend fun delete(
        id: String
    ): Either<Unit, ProjectRepositoryError.Delete> {
        val foundProject = projects.find { it.id == id }

        if (foundProject == null)
            return Either.failure(ProjectRepositoryError.Delete.ProjectNotFound)

        val deleted = projects.remove(foundProject)

        if (!deleted)
            return Either.failure(ProjectRepositoryError.Delete.ActionFailed)

        return Either.success(Unit)
    }

    override suspend fun delete(
        id: String,
        response: Either<Unit, ProjectRepositoryError.Delete>.() -> Unit
    ) {
        response(delete(id))
    }
}