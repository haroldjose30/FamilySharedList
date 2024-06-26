import Foundation
import SwiftUI

struct QuantitySelectionView: View {
    @State private var quantity: Int
    private let minValue: Int
    private let maxValue: Int
    private let incValue: Int
    private let onValueChanged: (Int) -> Void

    init(value: Int, minValue: Int = 1, maxValue: Int = 50, incValue: Int = 1, onValueChanged: @escaping (Int) -> Void) {
        self._quantity = State(initialValue: value)
        self.minValue = minValue
        self.maxValue = maxValue
        self.incValue = incValue
        self.onValueChanged = onValueChanged
    }

    var body: some View {
        HStack {

            Image(systemName: SystemName.chevronLeft.rawValue)
                .resizable()
                .frame(width: 12, height: 20)
                .foregroundColor(quantity <= minValue ? .gray : .blue)
                .onTapGesture { internalOnMinusClicked() }

            Text("\(quantity)")
                .font(.system(size: 16, weight: .bold))
            
            Image(systemName: SystemName.chevronRight.rawValue)
                .resizable()
                .frame(width: 12, height: 20)
                .foregroundColor(quantity >= maxValue ? .gray : .blue)
                .onTapGesture { internalOnMoreClicked() }
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
