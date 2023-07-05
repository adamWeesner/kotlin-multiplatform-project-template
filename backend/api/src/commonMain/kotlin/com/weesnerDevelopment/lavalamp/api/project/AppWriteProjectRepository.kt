package com.weesnerDevelopment.lavalamp.api.project

import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Databases

object AppwriteProjectRepository : ProjectRepository {
    private val client = Client().apply {
        setSelfSigned(false)
        setEndpoint("https://appwrite.weesnerdevelopment.com/v1")
        setKey("245f4c9d0a890f221fde45cd0377f24285ba521000f234da97d5c349e24456d432fd2a92750b57d2fcdafd064155683bea0c631ed25028cb4e160d56eaeecdd06306fa5eb4250401fd0bc9d1162965d5874596d32384cdf8860e2a50b7bb22db44321a40bb42cc9821b8b7e5a77d8f32c90bcf90d630f627cb63ff5663321d29")
        setProject("lavalamp")
    }
    private val databases = Databases(client)
    private val db = "lavalamp-db"
    private val collection = "projects"

    private val projects: MutableList<Project> = mutableListOf()

    override suspend fun getAll(): Either<List<Project>, ProjectRepositoryError.GetAll> {
        runCatching {
            val docs = databases.listDocuments<Project>(db, collection).documents

            if (docs.isEmpty())
                return Either.failure(ProjectRepositoryError.GetAll.NoProjects)

            return Either.success(docs.map { it.data })
        }.getOrElse {
            if (it is AppwriteException) {
                return Either.failure(ProjectRepositoryError.GetAll.Network(it.code, it.message))
            }

            return Either.failure(ProjectRepositoryError.GetAll.Unknown)
        }
    }

    override suspend fun getAll(
        response: Either<List<Project>, ProjectRepositoryError.GetAll>.() -> Unit
    ) {
        response(getAll())
    }

    override suspend fun get(id: String): Either<Project, ProjectRepositoryError.Get> {
        runCatching {
            val foundProject = databases.getDocument<Project>(db, collection, id).data

            return Either.success(foundProject)
        }.getOrElse {
            return Either.failure(ProjectRepositoryError.Get.ProjectNotFound)
        }
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
        runCatching {
            databases.createDocument<Project>(db, collection, ID.unique(), new)

            return Either.success(new)
        }.getOrElse {
            return Either.failure(ProjectRepositoryError.Add.ActionFailed)
        }
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
        runCatching {
            databases.updateDocument<Project>(db, collection, updated.id, updated)

            return Either.success(updated)
        }.getOrElse {
            return Either.failure(ProjectRepositoryError.Update.ActionFailed)
        }
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
        runCatching {
            databases.deleteDocument(db, collection, id)
            return Either.success(Unit)
        }.getOrElse {
            return Either.failure(ProjectRepositoryError.Delete.ActionFailed)
        }
    }

    override suspend fun delete(
        id: String,
        response: Either<Unit, ProjectRepositoryError.Delete>.() -> Unit
    ) {
        response(delete(id))
    }
}