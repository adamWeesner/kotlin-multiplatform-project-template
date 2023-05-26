package com.weesnerDevelopment.lavalamp.api

import com.weesnerDevelopment.lavalamp.sdk.Either
import com.weesnerDevelopment.lavalamp.sdk.Project

interface ProjectRepository {
    suspend fun getAll(): Either<List<Project>, ProjectRepositoryError.GetAll>
    suspend fun getAll(
        response: Either<List<Project>, ProjectRepositoryError.GetAll>.() -> Unit
    )

    suspend fun get(
        id: String
    ): Either<Project, ProjectRepositoryError.Get>

    suspend fun get(
        id: String,
        response: Either<Project, ProjectRepositoryError.Get>.() -> Unit
    )

    suspend fun add(
        new: Project
    ): Either<Project, ProjectRepositoryError.Add>

    suspend fun add(
        new: Project,
        response: Either<Project, ProjectRepositoryError.Add>.() -> Unit
    )

    suspend fun update(
        updated: Project
    ): Either<Project, ProjectRepositoryError.Update>

    suspend fun update(
        updated: Project,
        response: Either<Project, ProjectRepositoryError.Update>.() -> Unit
    )

    suspend fun delete(
        id: String
    ): Either<Unit, ProjectRepositoryError.Delete>

    suspend fun delete(
        id: String,
        response: Either<Unit, ProjectRepositoryError.Delete>.() -> Unit
    )
}
