package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.NavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.QuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    //ViewModels
    viewModelOf(::NavigatorViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::FamilyListViewModel)
    viewModelOf(::QuickInsertListViewModel)
}