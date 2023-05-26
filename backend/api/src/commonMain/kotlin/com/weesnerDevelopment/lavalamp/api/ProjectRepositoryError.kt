package com.weesnerDevelopment.lavalamp.api

sealed interface ProjectRepositoryError {
    sealed interface GetAll {
        object NoProjects : GetAll
    }

    sealed interface Get {
        object ProjectNotFound : Get
    }

    sealed interface Add {
        object ActionFailed : Add
    }

    sealed interface Update {
        object ProjectNotFound : Update
        object ActionFailed : Update
    }

    sealed interface Delete {
        object ProjectNotFound : Delete
        object ActionFailed : Delete
    }
}
