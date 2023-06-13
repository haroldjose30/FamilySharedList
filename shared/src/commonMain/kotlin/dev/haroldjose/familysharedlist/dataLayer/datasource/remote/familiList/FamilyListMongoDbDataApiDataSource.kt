package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familiList

import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.FilterByUuidDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestDocumentDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.mongoDb.request.MongoDbRequestFilterUpdateDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindAllResponseDto
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.response.MongoDbFindByUuidResponseDto
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual


internal class FamilyListMongoDbDataApiDataSource: MongoDbDataApiDataSource<FamilyListDto>(
    dataSource = MongoDbResources.DataSource.CLUSTER0.value,
    database = MongoDbResources.Database.FAMILYSHAREDLIST.value,
    collection = MongoDbResources.Collection.SHOPPINGLIST.value,
    serializers = SerializersModule {
        contextual(MongoDbFindAllResponseDto.serializer(FamilyListDto.serializer()))
        contextual(MongoDbFindByUuidResponseDto.serializer(FamilyListDto.serializer()))
        contextual(MongoDbRequestDocumentDto.serializer(FamilyListDto.serializer()))
        contextual(MongoDbRequestFilterUpdateDto.serializer(FilterByUuidDto.serializer(), FamilyListDto.serializer()))
    }
), IFamilyListMongoDbDataApiDataSource
