package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familyList

import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

interface IFamilyListRemoteDataSource {

    var database: String
    suspend fun insert(item: FamilyListDto)
    suspend fun insert(items: List<FamilyListDto>)
    suspend fun findAll(): List<FamilyListDto>
    suspend fun findBy(uuid: String): FamilyListDto?
    suspend fun update(item: FamilyListDto)
    suspend fun delete(uuid: String)
}