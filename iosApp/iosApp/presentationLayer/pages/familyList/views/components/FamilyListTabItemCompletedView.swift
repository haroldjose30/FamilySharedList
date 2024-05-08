import SwiftUI
import shared

struct FamilyListTabItemCompletedView<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    var viewModel: ViewModel
    let refreshData: (_ showLoading: Bool) -> Void
    var body: some View {
        VStack {
            if viewModel.familyListModelsGrouped.isEmpty {
                FamilyListEmptyState(action: {
                    refreshData(true)
                })
            } else {

                List {
                    ForEach(viewModel.familyListModelsGrouped) { itemGrouped in
                        Section(header: SectionHeader(itemGrouped)) {
                            ForEach(itemGrouped.items) { item in
                                FamilyListRowItemWithProduct(
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
                        }
                    }
                }.refreshable {
                    refreshData(false)
                }
                .backgroundClear()
            }
        }
    }

    private func SectionHeader(_ itemGrouped: FamilyListModelsGrouped) -> some View {
        HStack {
            Text(getFormattedDate(itemGrouped.id))
                .font(.system(size: 22, weight: .bold))
            Spacer()
            Text(itemGrouped.priceTotal.toCurrencyFormat())
                .font(.system(size: 16, weight: .bold))
        }
        .padding(.top,32)
        .padding(.horizontal,8)
    }

    private func getFormattedDate(_ date: Date) -> String {
        if (date == GlobalStateKt.defaultLocalDateTime.toDateNoTime()) {
            return "\(date.year)"
        } else {
            return date.toString()
        }
    }
}

#Preview {
    let viewModel = FamilyListViewModelMocked()
    return FamilyListTabItemCompletedView(
        viewModel: viewModel,
        refreshData: { _ in }
    )
}

