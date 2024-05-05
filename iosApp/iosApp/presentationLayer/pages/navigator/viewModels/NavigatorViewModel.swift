import Foundation
import shared
import SwiftUI
import KMPNativeCoroutinesAsync

class NavigatorViewModel: NavigatorViewModelProtocol {
    @Published var viewState: NavigatorViewState = .loading

    private let getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase
    private let crashlytics: IFirebaseCrashlytics

    init(
        getOrCreateAccountFromLocalUuidUseCase: GetOrCreateAccountFromLocalUuidUseCase,
        crashlytics: IFirebaseCrashlytics
    ) {
        self.getOrCreateAccountFromLocalUuidUseCase = getOrCreateAccountFromLocalUuidUseCase
        self.crashlytics = crashlytics
    }

    @MainActor
    func checkIfNeedToCreateNewAccount() async {
        viewState = .loading
        do {
            _ = try await asyncFunction(for: getOrCreateAccountFromLocalUuidUseCase.execute())
            viewState = .success
        } catch {
            showError(e: error)
            return
        }
    }

    func showError(e: Error) {
        crashlytics.record(error: e)
        viewState = .Error(message: e.localizedDescription, retryAction: {
            Task {
                await self.checkIfNeedToCreateNewAccount()
            }
        })
    }
}

