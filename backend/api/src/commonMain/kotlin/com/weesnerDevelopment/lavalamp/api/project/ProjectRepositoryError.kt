package com.weesnerDevelopment.lavalamp.api.project

sealed interface ProjectRepositoryError {
    sealed interface GetAll : ProjectRepositoryError {
        object NoProjects : GetAll
        object Unknown : GetAll
        class Network(val code: Int?, val message: String?) : GetAll
    }

    sealed interface Get : ProjectRepositoryError {
        object ProjectNotFound : Get
    }

    sealed interface Add : ProjectRepositoryError {
        object ActionFailed : Add
    }

    sealed interface Update : ProjectRepositoryError {
        object ActionFailed : Update
    }

    sealed interface Delete : ProjectRepositoryError {
        object ActionFailed : Delete
    }
}
