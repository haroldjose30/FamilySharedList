package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {

    //ViewModels
    factoryOf(::SettingsViewModel) bind ISettingsViewModel::class
    factoryOf(::FamilyListViewModel) bind IFamilyListViewModel::class
}