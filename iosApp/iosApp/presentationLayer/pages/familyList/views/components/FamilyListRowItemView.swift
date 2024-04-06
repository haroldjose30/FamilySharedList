import Foundation
import shared
import SwiftUI


struct FamilyListRowItemView<ViewModel>: View where ViewModel: FamilyListViewModelProtocol {

    @StateObject var viewModel: ViewModel
    @State var item: FamilyListModel

    @State private var nameTextFieldValue = ""
    @State private var nameInEditMode = false
    @State private var isBusy = false

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
                            .frame(maxWidth: .infinity, minHeight: 20, maxHeight: 80)
                            .lineLimit(3)
                            .border(.gray.opacity(0.2), width: 0.5)
                    }
                } else {
                    Text(item.name)
                        .font(.body)
                        .lineLimit(3)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(10)

                    if isBusy {
                        ProgressView()
                            .frame(width: 16, height: 16)
                    } else {
                        Image(systemName: "pencil")
                            .resizable()
                            .frame(width: 16, height: 16)
                            .foregroundColor(.blue)
                            .onTapGesture {
                                nameTextFieldValue = item.name
                                nameInEditMode = true
                            }
                    }
                }
            }

            if nameInEditMode {
                HStack {
                    Spacer()

                    Image(systemName: "checkmark")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(.blue)
                        .onTapGesture {
                            item.name = nameTextFieldValue
                            nameInEditMode = false
                            processUpdate { await viewModel.updateName(uuid: item.uuid, name: nameTextFieldValue)}
                        }

                    Image(systemName: "xmark")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundColor(.blue)
                        .onTapGesture {
                            nameInEditMode = false
                        }
                }
            } else {
                HStack {
                    if !item.isCompleted {
                        QuantitySelectionView(value: item.quantity.toInt()) { newValue in
                            item.quantity = newValue.toInt32()
                            processUpdate { await viewModel.updateQuantity(uuid: item.uuid, quantity: newValue) }
                        }
                        Spacer()
                        Image(systemName: item.isPrioritized ? "cart.fill" : "cart")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .foregroundColor(.blue)
                            .onTapGesture {
                                item.isPrioritized = !item.isPrioritized
                                processUpdate { await viewModel.updateIsPrioritized(uuid: item.uuid, isPrioritized: item.isPrioritized) }
                            }
                    }
                    Spacer()
                    Image(systemName: item.isCompleted ? "checkmark.circle.fill" : "checkmark.circle")
                        .resizable()
                        .frame(width: 20, height: 20)
                        .foregroundColor(.blue)
                        .onTapGesture {
                            item.isCompleted = !item.isCompleted
                            processUpdate { await viewModel.updateIsCompleted(uuid: item.uuid, isCompleted: item.isCompleted) }
                        }
                }
            }
        }
        .padding(10)
        .background(Color.white)
        .cornerRadius(10)
        .shadow(radius: 5)
    }

    func processUpdate(block: @escaping () async -> Void ) {
        Task {
            isBusy = true
            await block()
            isBusy = false
        }
    }
}
