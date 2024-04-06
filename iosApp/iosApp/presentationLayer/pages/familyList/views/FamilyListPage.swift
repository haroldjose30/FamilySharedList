import SwiftUI
import shared
import Foundation

struct FamilyListPage<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @StateObject var viewModel: ViewModel

    var body: some View {
        VStack {
            if viewModel.isLoading {
                ProgressView()
            }
            TabView(selection: $viewModel.tabIndex) {

                FamilyListView(items: viewModel.familyListModels.filter({$0.isPrioritized && !$0.isCompleted}))
                    .tabItem { Label("Priorizado", systemImage: SystemName.cart.rawValue) }
                    .tag(FamilyListPageTabEnum.prioritized)
                    .padding(.horizontal, -20)

                FamilyListView(items: viewModel.familyListModels.filter({!$0.isPrioritized && !$0.isCompleted}))
                    .tabItem { Label("Pendente", systemImage: SystemName.listBullet.rawValue) }
                    .tag(FamilyListPageTabEnum.pending)
                    .padding(.horizontal, -20)

                FamilyListView(items: viewModel.familyListModels.filter({$0.isCompleted}))
                    .tabItem { Label("Comprado", systemImage: SystemName.checkmarkCircle.rawValue) }
                    .tag(FamilyListPageTabEnum.completed)
                    .padding(.horizontal, -20)
            }
        }
        .navigationTitle("Lista de Compras")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar(content: {
            Button {
                self.viewModel.goToQuickInsert()
            } label: {
                Image(systemName: SystemName.textBadgePlus.rawValue)
            }
            Button {
                self.viewModel.goToSetting()
            } label: {
                Image(systemName: SystemName.gear.rawValue)
            }
        })
        .onAppear { refreshData(true) }
    }

    func FamilyListView(items: [FamilyListModel]) -> some View {
        Group {
            if items.isEmpty {
                EmptState()
            } else {
                List(items) { item in
                    FamilyListRowItemView(
                        viewModel: viewModel,
                        item: item
                    )
                    .background(Color.white)
                    .cornerRadius(10)
                    .shadow(radius: 1)
                    .swipeActions(edge: .trailing) {
                        Button(role: .destructive) {
                            Task { await viewModel.remove(uuid: item.uuid) }
                        } label: {
                            Label("Excluir", systemImage: SystemName.trash.rawValue)

                        }.background(.red)
                    }
                    .hideRowSeparator()
                }
                .refreshable {
                    refreshData(false)
                }
                .backgroundClear()
            }
        }
    }

    func EmptState() -> some View {
        VStack {
            Image(systemName: SystemName.zzz.rawValue)
                .resizable(resizingMode: .tile)
                .frame(width: 48, height: 48)
                .foregroundColor(.gray)
            Spacer().frame(height: 48)
            Text("nenhum item encontrado")

            Button("Atualizar", systemImage: SystemName.arrowTriangle2CirclepathIcloud.rawValue) {
                refreshData(true)
            }
        }
    }

    func refreshData(_ showLoading: Bool) {
        Task {
            await viewModel.loadData(fromNetwork: true, showLoading: showLoading)
        }
    }
}

#Preview {
    NavigationView {
        FamilyListPage(viewModel: FamilyListViewModelMocked())
    }
}
