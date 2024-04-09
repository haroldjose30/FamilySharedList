package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.viewmodels.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.INavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.viewmodels.NavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.IQuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.viewmodesls.QuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.viewmodels.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {
    //ViewModels
    factoryOf(::SettingsViewModel) bind ISettingsViewModel::class
    factoryOf(::FamilyListViewModel) bind IFamilyListViewModel::class
    factoryOf(::QuickInsertListViewModel) bind IQuickInsertListViewModel::class
    factoryOf(::NavigatorViewModel) bind INavigatorViewModel::class
}