package dev.haroldjose.sharedfamilylist.dataLayer.repositories.familyList

import dev.haroldjose.sharedfamilylist.dataLayer.dto.FamilyListDto

internal class FamilyListInMemoryRepository: IFamilyListRepository {

    companion object {
        private val familyListDataSource = mutableListOf<FamilyListDto>()
    }

    override suspend fun insert(item: FamilyListDto) {
        familyListDataSource.add(item)
    }

    override suspend fun findAll(): List<FamilyListDto> {

        if (familyListDataSource.isEmpty()) {

            familyListDataSource.add(
                FamilyListDto(
                    uuid = "uuid1",
                    name = "Item A",
                    isCompleted = false
                )
            )
            familyListDataSource.add(
                FamilyListDto(
                    uuid = "uuid2",
                    name = "Item B",
                    isCompleted = false
                )
            )
            familyListDataSource.add(
                FamilyListDto(
                    uuid = "uuid3",
                    name = "Item C",
                    isCompleted = false
                )
            )
            familyListDataSource.add(
                FamilyListDto(
                    uuid = "uuid4",
                    name = "Item D",
                    isCompleted = false
                )
            )
        }
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