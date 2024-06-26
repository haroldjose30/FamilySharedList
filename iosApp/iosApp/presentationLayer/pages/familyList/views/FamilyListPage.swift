import SwiftUI
import shared
import Foundation

struct FamilyListPage<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @StateObject var viewModel: ViewModel

    var body: some View {
        ZStack {
            switch viewModel.viewState {
            case let .error(message, retryAction):
                ErrorPage(message: message, retryAction: retryAction)
            default:
                FamilyListSuccessView()
            }

            if viewModel.viewState == .loading {
                ProgressView()
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

    private func FamilyListSuccessView() -> some View {
        VStack {
            HStack {
                Button {
                    Task { viewModel.isShowingBarcodeBottomSheet = true }
                } label: {
                    Image(systemName: SystemName.qrcodeViewfinder.rawValue)
                }
                TextField("Informe o novo item", text: $viewModel.newItemName)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Button {
                    Task { await viewModel.add() }
                } label: {
                    Image(systemName: SystemName.plusCircle.rawValue)
                }
            }
            .padding(8)
            .background(.gray.opacity(0.05))
            .cornerRadius(10)
            .shadow(radius: 1)
            .padding(.horizontal,20)
            .padding(.top,16)
            .sheet(isPresented: $viewModel.isShowingBarcodeBottomSheet) {
                BarcodeScannerPage { code in
                    if viewModel.isShowingBarcodeBottomSheet {
                        viewModel.isShowingBarcodeBottomSheet = false
                        Task {
                            await viewModel.addBy(barcode: code)
                        }
                    }
                }
            }


            TabView(selection: $viewModel.tabIndex) {

                FamilyListView(viewModel: viewModel, items: viewModel.familyListModels.filter({$0.isPrioritized && !$0.isCompleted}), refreshData: refreshData)
                    .tabItem { Label("Priorizado", systemImage: SystemName.cart.rawValue) }
                    .tag(FamilyListPageTabEnum.prioritized)
                    .padding(.horizontal, -20)

                FamilyListView(viewModel: viewModel, items: viewModel.familyListModels.filter({!$0.isPrioritized && !$0.isCompleted}), refreshData: refreshData)
                    .tabItem { Label("Pendente", systemImage: SystemName.listBullet.rawValue) }
                    .tag(FamilyListPageTabEnum.pending)
                    .padding(.horizontal, -20)

                FamilyListView(viewModel: viewModel, items: viewModel.familyListModels.filter({$0.isCompleted}), refreshData: refreshData)
                    .tabItem { Label("Comprado", systemImage: SystemName.checkmarkCircle.rawValue) }
                    .tag(FamilyListPageTabEnum.completed)
                    .padding(.horizontal, -20)
            }
        }
    }

    private func refreshData(_ showLoading: Bool) {
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
