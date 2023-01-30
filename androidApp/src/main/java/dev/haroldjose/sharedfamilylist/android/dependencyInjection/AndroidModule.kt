package dev.haroldjose.sharedfamilylist.android.dependencyInjection

import dev.haroldjose.sharedfamilylist.android.pages.familyList.FamilyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    viewModelOf(::FamilyListViewModel)
}