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
    @Published var isLoading: Bool = true

    var goBack: () -> Void = {}

    init(getAccountUseCase: GetAccountUseCase,
         getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
         setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase) {
        self.getAccountUseCase = getAccountUseCase
        self.getLocalAccountUuidUseCase = getLocalAccountUuidUseCase
        self.setSharedAccountByCodeUseCase = setSharedAccountByCodeUseCase
    }

    @MainActor
    func getAccount() async {

        isLoading = true

        guard let accountUuid = try? await getLocalAccountUuidUseCase.execute() else {
            //TODO: handle error
            return
        }

        //TODO: handle error
        myAccount = try? await getAccountUseCase.execute(accountUuid: accountUuid)

        self.accountShortCodeForShareTitle = self.myAccount?.accountShortCodeForShare ?? "carregando..."

        if let sharedAccount = self.myAccount?.accountsSharedWithMe.first {
            self.accountsSharedWithMeTitle = "Acessando a conta:"
            self.accountsSharedWithMeSubtitle = sharedAccount
        }

        isLoading = false
    }

    @MainActor
    func accessSharedAccountWithCode(code: String) async {
        isLoading = true
        guard let accountUuid = try? await getLocalAccountUuidUseCase.execute() else  {
            //TODO: handle error
            return
        }

        //TODO: handle error
        if let _ = try? await setSharedAccountByCodeUseCase.execute(accountUuid: accountUuid, code: code) {
            await getAccount()
        }

        isLoading = false
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
