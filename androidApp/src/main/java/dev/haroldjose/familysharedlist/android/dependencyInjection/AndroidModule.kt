package dev.haroldjose.familysharedlist.android.dependencyInjection

import dev.haroldjose.familysharedlist.android.pages.familyList.FamilyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::FamilyListViewModel)
}