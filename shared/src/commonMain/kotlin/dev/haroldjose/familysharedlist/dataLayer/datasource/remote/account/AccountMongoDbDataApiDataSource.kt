package dev.haroldjose.familysharedlist.dataLayer.datasource.remote.account

import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.MongoDbDataApiDataSource
import dev.haroldjose.familysharedlist.dataLayer.datasource.remote.mongoDb.resources.MongoDbResources
import dev.haroldjose.familysharedlist.dataLayer.dto.AccountDto

internal class AccountMongoDbDataApiDataSource: MongoDbDataApiDataSource<AccountDto>(
    dataSource = MongoDbResources.DataSource.CLUSTER0.value,
    database = MongoDbResources.Database.ACCOUNT.value,
    collection = MongoDbResources.Collection.ACCOUNT.value
), IAccountMongoDbDataApiDataSource