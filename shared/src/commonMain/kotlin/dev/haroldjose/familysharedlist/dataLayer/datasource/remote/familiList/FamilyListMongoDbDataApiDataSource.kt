package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.familiList

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.FamilyListDto


internal class FamilyListMongoDbDataApiDataSource: MongoDbDataApiDataSource<FamilyListDto>(
    dataSource = MongoDbResources.DataSource.CLUSTER0.value,
    database = MongoDbResources.Database.FAMILYSHAREDLIST.value,
    collection = MongoDbResources.Collection.SHOPPINGLIST.value
), IFamilyListMongoDbDataApiDataSource
