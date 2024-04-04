import Foundation
import SwiftUI
import shared

struct SettingsPage<ViewModel>: View where ViewModel: SettingsViewModelProtocol {
    @ObservedObject var viewModel: ViewModel

    var body: some View {
        VStack(spacing: 16) {
            SettingsItemWithTitleAndSubtitleView(
                title: "Meu código de compartilhamento",
                subtitle: viewModel.accountShortCodeForShareTitle
            ) {
                viewModel.shareMyCode()
            }

            SettingsItemWithInputTextView(
                title: viewModel.accountsSharedWithMeTitle,
                subtitle: viewModel.accountsSharedWithMeSubtitle
            ) { code in
                Task { await viewModel.accessSharedAccountWithCode(code: code) }
            }

            SettingsItemWithTitleView(title: "Sobre este app") {
                viewModel.openAppHomePage()
            }
        }
        .padding()
        .navigationTitle("Settings")
        .onAppear {
            Task { await viewModel.getAccount()}
        }
    }
}

#Preview {
    SettingsPage(viewModel: SettingsViewModelMocked())
}
