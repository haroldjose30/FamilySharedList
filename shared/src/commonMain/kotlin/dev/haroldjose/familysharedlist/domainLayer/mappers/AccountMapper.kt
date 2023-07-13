package dev.haroldjose.familysharedlist.domainLayer.mappers


import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto
import dev.haroldjose.familysharedlist.domainLayer.models.AccountModel

internal fun AccountDto.toModel(): AccountModel {
    return AccountModel(
        uuid = this.uuid,
        createdDate = this.createdDate,
        updatedDate = this.updatedDate,
        name = this.name,
        email = this.email,
        myAccountIsSharedWith = this.myAccountIsSharedWith,
        accountsSharedWithMe = this.accountsSharedWithMe,
        platform = this.platform
    )
}

internal fun AccountModel.toDto(): AccountDto {
    return AccountDto(
        uuid = this.uuid,
        createdDate = this.createdDate,
        updatedDate = this.updatedDate,
        name = this.name,
        email = this.email,
        myAccountIsSharedWith = this.myAccountIsSharedWith,
        accountsSharedWithMe = this.accountsSharedWithMe,
        platform = this.platform
    )
}

