import SwiftUI
import shared

struct FamilyListPage: View {
    
    @StateObject var viewModel: FamilyListViewModel
    
    var body: some View {
        VStack {
            HStack {
                Spacer()
                Text("Lista de Compras")
                    .accessibilityIdentifier(Identifier.textTitle)
                    .font(.title)
                Spacer()
            }
            
            if viewModel.loading {
                ProgressView()
            }
            
            
            if #available(iOS 15.0, *) {
                List {
                    ForEach(viewModel.familyListModels) { item in
                        FamilyListRow(
                            item: item,
                            onItemChanged: { item in
                                Task {
                                    await viewModel.update(item: item)
                                }
                            }
                        )
                    }
                    .onDelete { indexSet in
                        viewModel.onDelete(at: indexSet)
                    }
                }.refreshable {
                    await viewModel.loadData()
                }
            } else {
                List {
                    ForEach(viewModel.familyListModels) { item in
                        FamilyListRow(
                            item: item,
                            onItemChanged: { item in
                                Task {
                                    await viewModel.update(item: item)
                                }
                            }
                        )
                    }
                    .onDelete { indexSet in
                        viewModel.onDelete(at: indexSet)
                    }
                }
            }

            
            HStack {
                
                TextField(
                    "Informe o novo item",
                    text: $viewModel.newItemName
                ).accessibilityIdentifier(Identifier.textFieldAdd)
                
                Button(action: {
                    Task {
                        await viewModel.add()
                    }
                }) {
                    Image(systemName: "plus.circle.fill")
                        .imageScale(.large)
                }
                .accessibilityIdentifier(Identifier.buttonAdd)
                .frame(width: 50)
                
            }.padding(
                EdgeInsets(
                    top: 8,
                    leading: 8,
                    bottom: 16,
                    trailing: 8
                )
            )
        }
        .onAppear {
            Task {
                await viewModel.loadData()
            }
        }
        
    }
}

private struct FamilyListRow: View {
    
    let item: FamilyListModel
    let onItemChanged: ((FamilyListModel) -> Void)
    @State private var checkedState: Bool
    
    init(
        item: FamilyListModel,
        onItemChanged: @escaping (FamilyListModel) -> Void
    ) {
        self.item = item
        self.onItemChanged = onItemChanged
        self.checkedState = item.isCompleted
    }
    
    
    
    var body: some View {
        VStack( alignment: HorizontalAlignment.leading) {
            Text(item.name)
                .font(.system(size: 16))
                .padding(10)
            
            HStack {
                
                QuantitySelectionView(
                    value: item.quantity.toInt(),
                    minValue: 1,
                    maxValue: 50,
                    onValueChanged:  { value in
                        let itemMutable = item
                        item.quantity = value.toInt32()
                        onItemChanged(itemMutable)
                    }
                )
                
                Spacer()
                
                Toggle("", isOn: $checkedState)
                    .labelsHidden()
                    .onChange(of: checkedState) { value in
                        let itemMutable = item
                        item.isCompleted = value
                        onItemChanged(itemMutable)
                    }
            }
            
        }
    }
}


private extension FamilyListPage {
    
    private enum Identifier {
        static let textTitle = AccessibilityId.FamilyListPage.textTitle.rawValue
        static let buttonAdd = AccessibilityId.FamilyListPage.buttonAdd.rawValue
        static let textFieldAdd = AccessibilityId.FamilyListPage.textFieldAdd.rawValue
    }
}

struct FamilyListPage_Previews: PreviewProvider {
    static var previews: some View {
        //TODO: inject mocked service
        GlobalState.companion.isRunningUITests = true
        
        return FamilyListPage(
            viewModel: ResolverPreview().resolve()
        )
    }
}
