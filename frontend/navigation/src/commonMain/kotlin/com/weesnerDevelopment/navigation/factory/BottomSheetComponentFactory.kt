package com.weesnerDevelopment.navigation.factory

import com.arkivanov.decompose.ComponentContext
import com.weesnerDevelopment.navigation.BottomSheetConfig
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import com.weesnerDevelopment.navigation.Navigator
import kotlin.coroutines.CoroutineContext

interface BottomSheetComponentFactory {
    fun create(config: BottomSheetConfig, navigator: Navigator): Child.BottomSheet
}

internal class DefaultBottomSheetComponentFactory(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
) : BottomSheetComponentFactory {
    override fun create(config: BottomSheetConfig, navigator: Navigator): Child.BottomSheet {
        return when (config) {
            is Config.SampleForBottomSheet -> {
                Child.SampleForBottomSheet
            }
        }
    }
}
