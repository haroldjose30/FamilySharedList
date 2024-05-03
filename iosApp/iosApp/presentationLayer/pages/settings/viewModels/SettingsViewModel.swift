import Foundation
import shared
import KMPNativeCoroutinesAsync

class SettingsViewModel: SettingsViewModelProtocol {

    @Published var viewState: SettingsViewState = .Initial(
        accountShortCodeForShareTitle: "carregando...",
        accountsSharedWithMeTitle: "Acessar conta compartilhada",
        accountsSharedWithMeSubtitle: "Obs: atualmente limitada apenas a 1 conta"
    )
    @Published var myAccount: AccountModel?

    var goBack: () -> Void = {}

    private let getAccountUseCase: GetAccountUseCase
    private let getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase
    private let setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase

    init(getAccountUseCase: GetAccountUseCase,
         getLocalAccountUuidUseCase: GetLocalAccountUuidUseCase,
         setSharedAccountByCodeUseCase: SetSharedAccountByCodeUseCase) {
        self.getAccountUseCase = getAccountUseCase
        self.getLocalAccountUuidUseCase = getLocalAccountUuidUseCase
        self.setSharedAccountByCodeUseCase = setSharedAccountByCodeUseCase
    }

    @MainActor
    func getAccount() async {

        viewState = .loading

        do {
            let accountUuid = try await asyncFunction(for: getLocalAccountUuidUseCase.execute())
            myAccount = try await asyncFunction(for: getAccountUseCase.execute(accountUuid: accountUuid))
        } catch {
            showError(e: error)
            return
        }

        let accountShortCodeForShareTitle = self.myAccount?.accountShortCodeForShare ?? "carregando..."
        var accountsSharedWithMeTitle = "Acessar conta compartilhada"
        var accountsSharedWithMeSubtitle = "Obs: atualmente limitada apenas a 1 conta"

        if let sharedAccount = self.myAccount?.accountsSharedWithMe.first {
            accountsSharedWithMeTitle = "Acessando a conta:"
            accountsSharedWithMeSubtitle = sharedAccount
        }

        viewState = .Success(
            accountShortCodeForShareTitle: accountShortCodeForShareTitle,
            accountsSharedWithMeTitle: accountsSharedWithMeTitle,
            accountsSharedWithMeSubtitle: accountsSharedWithMeSubtitle
        )
    }

    @MainActor
    func accessSharedAccountWithCode(code: String) async {

        viewState = .loading

        do {
            let accountUuid = try await asyncFunction(for: getLocalAccountUuidUseCase.execute())
            _ = try await asyncFunction(for: setSharedAccountByCodeUseCase.execute(accountUuid: accountUuid, code: code))
            await getAccount()
        } catch {
            showError(e: error)
            return
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

    func showError(e: Error) {

        viewState = .Error(message: e.localizedDescription, retryAction: {
            Task {
                await self.getAccount()
            }
        })
    }
}
