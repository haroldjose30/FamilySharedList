package dev.haroldjose.familysharedlist.dataLayer.repositories.familyList

import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto

internal class FamilyListInMemoryRepository: IFamilyListRepository {

    companion object {
        private val familyListDataSource = mutableListOf<FamilyListDto>()
    }

    override suspend fun insert(item: FamilyListDto) {

        familyListDataSource.add(item)
    }

    override suspend fun findAll(): List<FamilyListDto> {

        return familyListDataSource.toList()
    }

    override suspend fun update(item: FamilyListDto) {
        val index = familyListDataSource.indexOfFirst { it.uuid == item.uuid }
        if (index != -1) {
            familyListDataSource[index] = item
        }
    }

    override suspend fun delete(uuid: String) {
        familyListDataSource.removeAll { it.uuid == uuid }
    }
}