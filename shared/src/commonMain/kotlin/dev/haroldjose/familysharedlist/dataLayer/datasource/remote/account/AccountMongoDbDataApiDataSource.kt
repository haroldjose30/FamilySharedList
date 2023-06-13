package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.FilterByUuidDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDocumentDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterUpdateDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindAllResponseDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindByUuidResponseDto
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

internal class AccountMongoDbDataApiDataSource: MongoDbDataApiDataSource<AccountDto>(
    dataSource = MongoDbResources.DataSource.CLUSTER0.value,
    database = MongoDbResources.Database.ACCOUNT.value,
    collection = MongoDbResources.Collection.ACCOUNT.value,
    serializers = SerializersModule {
        contextual(MongoDbFindAllResponseDto.serializer(AccountDto.serializer()))
        contextual(MongoDbFindByUuidResponseDto.serializer(AccountDto.serializer()))
        contextual(MongoDbRequestDocumentDto.serializer(AccountDto.serializer()))
        contextual(MongoDbRequestFilterUpdateDto.serializer(FilterByUuidDto.serializer(), AccountDto.serializer()))
    }
), IAccountMongoDbDataApiDataSource