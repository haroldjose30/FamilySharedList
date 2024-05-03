import Foundation
import SwiftUI
import shared

struct SettingsPage<ViewModel>: View where ViewModel: SettingsViewModelProtocol {
    @StateObject var viewModel: ViewModel

    var body: some View {
        VStack(spacing: 16) {

            switch  viewModel.viewState {
            case let .Initial(accountShortCodeForShareTitle, accountsSharedWithMeTitle, accountsSharedWithMeSubtitle):
                SettingsView(
                    accountShortCodeForShareTitle: accountShortCodeForShareTitle,
                    accountsSharedWithMeTitle: accountsSharedWithMeTitle,
                    accountsSharedWithMeSubtitle: accountsSharedWithMeSubtitle
                )
            case .loading:
                ProgressView()
            case let .Success(accountShortCodeForShareTitle, accountsSharedWithMeTitle, accountsSharedWithMeSubtitle):
                SettingsView(
                    accountShortCodeForShareTitle: accountShortCodeForShareTitle,
                    accountsSharedWithMeTitle: accountsSharedWithMeTitle,
                    accountsSharedWithMeSubtitle: accountsSharedWithMeSubtitle
                )
            case let .Error(message, retryAction):
                Text(message)
            }
        }
        .padding()
        .navigationTitle("Settings")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar(content: {
            ToolbarItem(placement: .topBarLeading) {
                Button {
                    self.viewModel.goBack()
                } label: {
                    Image(systemName: SystemName.chevronLeft.rawValue)
                }
            }
        })
        .onAppear {
            Task { await viewModel.getAccount()}
        }

    }

    private func SettingsView(
        accountShortCodeForShareTitle: String,
        accountsSharedWithMeTitle: String,
        accountsSharedWithMeSubtitle: String
    ) -> some View {
        VStack(spacing: 16) {
            SettingsItemWithTitleAndSubtitleView(
                title: "Meu código de compartilhamento",
                subtitle: accountShortCodeForShareTitle
            ) {
                viewModel.shareMyCode()
            }

            SettingsItemWithInputTextView(
                title: accountsSharedWithMeTitle,
                subtitle: accountsSharedWithMeSubtitle
            ) { code in
                Task { await viewModel.accessSharedAccountWithCode(code: code) }
            }

            SettingsItemWithTitleView(title: "Sobre este app") {
                viewModel.openAppHomePage()
            }

            Spacer()
        }
    }
}

#Preview {
    SettingsPage(viewModel: SettingsViewModelMocked())
}
