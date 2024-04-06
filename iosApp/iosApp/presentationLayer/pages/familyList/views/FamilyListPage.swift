import SwiftUI
import shared
import Foundation

struct FamilyListPage<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @ObservedObject var viewModel: ViewModel
    @State private var tabIndex: FamilyListPageTabEnum = .pending

    var body: some View {
        VStack {
            TabView(selection: $tabIndex) {
                FamilyListTabItemView(viewModel: viewModel, tabIndex: tabIndex)
                    .tabItem {
                        Label("Priorizado", systemImage: "cart")
                    }.tag(FamilyListPageTabEnum.prioritized)
                FamilyListTabItemView(viewModel: viewModel, tabIndex: tabIndex)
                    .tabItem {
                        Label("Pendente", systemImage: "list.bullet")
                    }.tag(FamilyListPageTabEnum.pending)
                FamilyListTabItemView(viewModel: viewModel, tabIndex: tabIndex)
                    .tabItem {
                        Label("Comprado", systemImage: "checkmark.circle")
                    }.tag(FamilyListPageTabEnum.completed)
            }
        }
        .navigationTitle("Lista de Compras")
        .toolbar(content: {
            Button {
                self.viewModel.goToQuickInsert()
            } label: {
                Image(systemName: "text.badge.plus")
            }
            Button {
                self.viewModel.goToSetting()
            } label: {
                Image(systemName: "gear")
            }
        })
//        .onAppear {
//            Task {  await viewModel.loadData(tabIndex: tabIndex, fromNetwork: true) }
//        }
    }
}

#Preview {
    NavigationView {
        FamilyListPage(viewModel: FamilyListViewModelMocked())
    }
}
