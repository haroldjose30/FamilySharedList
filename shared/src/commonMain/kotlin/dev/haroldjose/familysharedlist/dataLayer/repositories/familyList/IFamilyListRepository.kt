package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList

import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

interface IFamilyListRepository{
    suspend fun insert(item: FamilyListDto)
    suspend fun findAll(): List<FamilyListDto>
    suspend fun update(item: FamilyListDto)
    suspend fun delete(uuid: String)
    fun setDataBase(databaseName: String)
}