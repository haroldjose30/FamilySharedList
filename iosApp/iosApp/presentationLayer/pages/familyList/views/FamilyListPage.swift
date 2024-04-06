import SwiftUI
import shared
import Foundation

struct FamilyListPage<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @StateObject var viewModel: ViewModel

    @State var nameTextFieldValue = ""
    @State var nameInEditMode = false

    var body: some View {
        VStack {
            TabView(selection: $viewModel.tabIndex) {

                FamilyListView(items: viewModel.familyListModels.filter({$0.isPrioritized && !$0.isCompleted}))
                    .tabItem { Label("Priorizado", systemImage: "cart") }
                    .tag(FamilyListPageTabEnum.prioritized)
                    .padding(.horizontal, -20)

                FamilyListView(items: viewModel.familyListModels.filter({!$0.isPrioritized && !$0.isCompleted}))
                    .tabItem { Label("Pendente", systemImage: "list.bullet") }
                    .tag(FamilyListPageTabEnum.pending)
                    .padding(.horizontal, -20)

                FamilyListView(items: viewModel.familyListModels.filter({$0.isCompleted}))
                    .tabItem { Label("Comprado", systemImage: "checkmark.circle") }
                    .tag(FamilyListPageTabEnum.completed)
                    .padding(.horizontal, -20)
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
        .onAppear {
            Task {  await viewModel.loadData(fromNetwork: true) }
        }
    }
    
    func FamilyListView(items: [FamilyListModel]) -> some View {
        Group {
            if viewModel.loading {
                ProgressView()
            } else {
                List(items) { item in
                    FamilyListRowItemView(
                        viewModel: viewModel,
                        item: item
                    ).hideRowSeparator()
                }
                .backgroundClear()
            }
        }
    }
}

#Preview {
    NavigationView {
        FamilyListPage(viewModel: FamilyListViewModelMocked())
    }
}
