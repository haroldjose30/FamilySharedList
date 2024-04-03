package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.FamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.familyList.IFamilyListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.INavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.navigator.NavigatorViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.IQuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.quickInsertList.QuickInsertListViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.ISettingsViewModel
import dev.haroldjose.familysharedlist.android.presentationLayer.pages.settings.SettingsViewModel
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