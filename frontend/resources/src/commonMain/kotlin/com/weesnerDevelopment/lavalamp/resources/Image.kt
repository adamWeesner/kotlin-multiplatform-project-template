package com.weesnerDevelopment.lavalamp.resources

sealed class Image(val fileName: String) {
    object Cancel : Image(fileName = "cancel")
    object BackArrow : Image(fileName = "backArrow")
}
