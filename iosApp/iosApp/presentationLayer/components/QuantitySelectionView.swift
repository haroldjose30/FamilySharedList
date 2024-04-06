import Foundation
import SwiftUI

struct QuantitySelectionView: View {
    @State private var quantity: Int
    let minValue: Int
    let maxValue: Int
    let incValue: Int
    let onValueChanged: (Int) -> Void

    init(value: Int, minValue: Int = Int.min, maxValue: Int = Int.max, incValue: Int = 1, onValueChanged: @escaping (Int) -> Void) {
        self._quantity = State(initialValue: value)
        self.minValue = minValue
        self.maxValue = maxValue
        self.incValue = incValue
        self.onValueChanged = onValueChanged
    }

    var body: some View {
        HStack {
            Button(action: {
                internalOnMinusClicked()
            }) {
                Image(systemName: "chevron.left")
                    .foregroundColor(quantity <= minValue ? .gray : .blue)
            }
            Text("\(quantity)")
                .font(.system(size: 16, weight: .bold))
            Button(action: {
                internalOnMoreClicked()
            }) {
                Image(systemName: "chevron.right")
                    .foregroundColor(quantity >= maxValue ? .gray : .blue)
            }
        }
    }

    private func internalOnMinusClicked() {
        if quantity > minValue {
            quantity -= incValue
            onValueChanged(quantity)
        }
    }

    private func internalOnMoreClicked() {
        if quantity < maxValue {
            quantity += incValue
            onValueChanged(quantity)
        }
    }
}


#Preview {
    QuantitySelectionView(
        value: 1,
        minValue: 1,
        maxValue: 10,
        incValue: 1,
        onValueChanged: {_ in }
    )
}
