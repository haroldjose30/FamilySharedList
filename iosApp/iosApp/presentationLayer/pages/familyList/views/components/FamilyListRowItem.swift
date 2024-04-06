import Foundation
import SwiftUI
import shared

struct FamilyListRowItem<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {
    @State private var nameTextFieldValue = ""
    @State private var nameInEditMode = false
    @State private var isCompleted = false
    @State private var isPrioritized = false

    let item: FamilyListModel
    @ObservedObject var viewModel: ViewModel

    var body: some View {
        VStack(spacing: 10) {
            HStack {
                if nameInEditMode {
                    VStack {
                        Text("Alterando descrição")
                            .font(.caption2)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        TextEditor(text: $nameTextFieldValue)
                            .textFieldStyle(.roundedBorder)
                            .keyboardType(.default)
                            .disableAutocorrection(true)
                            .multilineTextAlignment(.leading)
                            .frame(maxWidth: .infinity, maxHeight: 80)
                            .lineLimit(3)
                            .border(.gray.opacity(0.2), width: 0.5)
                    }
                } else {
                    Text(item.name)
                        .font(.body)
                        .lineLimit(3)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(10)

                    Button(action: {
                        nameTextFieldValue = item.name
                        nameInEditMode = true
                    }) {
                        Image(systemName: "pencil")
                            .resizable()
                            .frame(width: 16, height: 16)
                    }
                }
            }

            if nameInEditMode {
                HStack {
                    Spacer()
                    Button(action: {
                        item.name = nameTextFieldValue
                        Task { await viewModel.updateName(uuid: item.uuid, name: nameTextFieldValue) }
                        nameInEditMode = false
                    }) {
                        Image(systemName: "checkmark")
                            .resizable()
                            .frame(width: 16, height: 16)
                    }
                    Button(action: {
                        nameInEditMode = false
                    }) {
                        Image(systemName: "xmark")
                            .resizable()
                            .frame(width: 16, height: 16)
                    }
                }
            } else {
                HStack {
                    if !isCompleted {
                        QuantitySelectionView(value: item.quantity.toInt(), minValue: 1, maxValue: 50) { newValue in
                            item.quantity = newValue.toInt32()
                            Task { await viewModel.updateQuantity(uuid: item.uuid, quantity: newValue) }
                        }
                        Spacer()
                        Button(action: {
                            item.isPrioritized = !item.isPrioritized
                            isPrioritized = item.isPrioritized
                            Task { await viewModel.updateIsPrioritized(uuid: item.uuid, isPrioritized: isPrioritized) }
                        }) {
                            Image(systemName: isPrioritized ? "cart.fill" : "cart")
                                .resizable()
                                .frame(width: 20, height: 20)
                        }
                    }
                    Spacer()
                    Button(action: {
                        item.isCompleted = !item.isCompleted
                        isCompleted = item.isCompleted
                        Task { await viewModel.updateIsCompleted(uuid: item.uuid, isCompleted: isCompleted) }
                    }) {
                        Image(systemName: isCompleted ? "checkmark.circle.fill" : "checkmark.circle")
                            .resizable()
                            .frame(width: 20, height: 20)
                    }
                }
            }
        }
        .padding(10)
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 5)
    }
}

#Preview {
    FamilyListRowItem(
        item: FamilyListModel(
            name: "Item no preview com um texto muito longo que pode ter muitas linhas quebrando o texto com o limite maximo de 3 linhas",
            isCompleted: false,
            isPrioritized: false,
            quantity: 2
        ),
        viewModel: FamilyListViewModelMocked()
    )
}
