package com.weesnerDevelopment.lavalamp.api.project

import com.weesnerDevelopment.lavalamp.logging.Logger
import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project

internal object InMemoryProjectRepository : ProjectRepository {
    private val projects: MutableList<Project> = mutableListOf()

    override suspend fun getAll(): Either<List<Project>, ProjectRepositoryError.GetAll> {
        Logger.info("Fetching all projects")

        if (projects.isEmpty()) {
            Logger.info("No projects found")
            return Either.failure(ProjectRepositoryError.GetAll.NoProjects)
        }

        Logger.info("Found (${projects.size}) projects")
        return Either.success(projects)
    }

    override suspend fun getAll(
        response: Either<List<Project>, ProjectRepositoryError.GetAll>.() -> Unit
    ) {
        response(getAll())
    }

    override suspend fun get(id: String): Either<Project, ProjectRepositoryError.Get> {
        Logger.info("Getting project ($id)")

        val foundProject = projects.find { it.id == id }

        if (foundProject == null) {
            Logger.info("Failed to get project ($id) - not found")
            return Either.failure(ProjectRepositoryError.Get.ProjectNotFound)
        }

        Logger.info("Found project ($id)")
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
        Logger.info("Adding project (${new.id})")

        val added = projects.add(new)

        if (!added) {
            Logger.error("Failed to add project - $new")
            return Either.failure(ProjectRepositoryError.Add.ActionFailed)
        }

        Logger.info("Successfully added project (${new.id})")
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
        Logger.info("Updating project (${updated.id})")

        val foundProject = projects.find { it.id == updated.id }
        val index = projects.indexOf(foundProject)

        if (foundProject == null) {
            Logger.warn("Failed to update project (${updated.id}) - not found")
            return Either.failure(ProjectRepositoryError.Update.ActionFailed)
        }

        val deleted = projects.remove(foundProject)

        if (!deleted) {
            Logger.error("Failed to update project (${updated.id}) - local delete failed")
            return Either.failure(ProjectRepositoryError.Update.ActionFailed)
        }

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

        Logger.info("Successfully updated project (${updatedProject.id})")
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
        Logger.info("Deleting project ($id)")

        val foundProject = projects.find { it.id == id }

        if (foundProject == null) {
            Logger.error("Failed to delete project ($id) - not found")
            return Either.failure(ProjectRepositoryError.Delete.ActionFailed)
        }

        val deleted = projects.remove(foundProject)

        if (!deleted) {
            Logger.error("Failed to delete project ($id) - local delete failed")
            return Either.failure(ProjectRepositoryError.Delete.ActionFailed)
        }

        Logger.error("Successfully deleted project ($id)")

        return Either.success(Unit)
    }

    override suspend fun delete(
        id: String,
        response: Either<Unit, ProjectRepositoryError.Delete>.() -> Unit
    ) {
        response(delete(id))
    }
}