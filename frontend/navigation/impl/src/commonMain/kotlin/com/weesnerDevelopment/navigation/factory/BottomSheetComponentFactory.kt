package com.weesnerDevelopment.navigation.factory

import com.weesnerDevelopment.navigation.BottomSheetConfig
import com.weesnerDevelopment.navigation.Child
import com.weesnerDevelopment.navigation.Config
import org.kodein.di.DI
import org.kodein.di.DIAware

internal interface BottomSheetComponentFactory {
    fun create(config: BottomSheetConfig): Child.BottomSheet
}

internal class DefaultBottomSheetComponentFactory(
    override val di: DI
) : BottomSheetComponentFactory, DIAware {
    override fun create(config: BottomSheetConfig): Child.BottomSheet {
        return when (config) {
            is Config.SampleForBottomSheet -> {
                Child.SampleForBottomSheet
            }
        }
    }
}
