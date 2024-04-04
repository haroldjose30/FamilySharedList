import Foundation
import shared

class SettingsViewModel: SettingsViewModelProtocol {
    private let getAccountUseCase: GetAccountUseCase
    private let getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase
    private let setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase

    @Published var myAccount: AccountModel?
    @Published var accountShortCodeForShareTitle: String = "carregando..."
    @Published var accountsSharedWithMeTitle: String = "Acessar conta compartilhada"
    @Published var accountsSharedWithMeSubtitle: String = "Obs: atualmente limitada apenas a 1 conta"

    var goBack: () -> Void = {}

    init(getAccountUseCase: GetAccountUseCase,
         getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
         setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase) {
        self.getAccountUseCase = getAccountUseCase
        self.getLocalAccountUuidUseCase = getLocalAccountUuidUseCase
        self.setSharedAccountByCodeUseCase = setSharedAccountByCodeUseCase
    }

    //@MainActor
    func getAccount() async {

        guard let accountUuid = try? await getLocalAccountUuidUseCase.execute() else {
            //TODO: handle error
            return
        }

        myAccount = try? await getAccountUseCase.execute(accountUuid: accountUuid)
        accountShortCodeForShareTitle = myAccount?.accountShortCodeForShare ?? "carregando..."

        if let sharedAccount = myAccount?.accountsSharedWithMe.first {
            accountsSharedWithMeTitle = "Acessando a conta:"
            accountsSharedWithMeSubtitle = sharedAccount
        }
    }

    //@MainActor
    func accessSharedAccountWithCode(code: String) async {
        guard let accountUuid = try? await getLocalAccountUuidUseCase.execute() else  {
            //TODO: handle error
            return
        }

        //TODO: handle error
        if let _ = try? await setSharedAccountByCodeUseCase.execute(accountUuid: accountUuid, code: code) {
            await getAccount()
        }
    }

    func shareMyCode() {
        if let _ = myAccount?.accountShortCodeForShare {
            //TODO: Implement sharing functionality
        }
    }

    func openAppHomePage() {
        // TODO: mplement opening app home page functionality
    }
}

