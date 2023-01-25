package dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.dto.FamilyListDto

internal interface IFamilyListRepository {
    suspend fun insert(item: FamilyListDto)
    suspend fun findAll(): List<FamilyListDto>
    suspend fun update(item: FamilyListDto)
    suspend fun delete(uuid: String)
}