import SwiftUI
import UIKit

struct CurrencyTextField: View {

    @Binding var value: Int

    var body: some View {
        CurrencyTextFieldInternal(value: $value)
            .padding(20)
            .overlay(RoundedRectangle(cornerRadius: 16)
                .stroke(Color.gray.opacity(0.3), lineWidth: 2))
            .frame(height: 50)
    }
}

private struct CurrencyTextFieldInternal: UIViewRepresentable {

    typealias UIViewType = CurrencyUITextField

    let numberFormatter: NumberFormatter
    let currencyField: CurrencyUITextField

    init(numberFormatter: NumberFormatter? = nil, value: Binding<Int>) {

        if let numberFormatter {
            self.numberFormatter = numberFormatter
        } else {
            self.numberFormatter = NumberFormatter()
            self.numberFormatter.numberStyle = .currency
            self.numberFormatter.maximumFractionDigits = 2
        }

        currencyField = CurrencyUITextField(formatter: self.numberFormatter, value: value)
    }

    func makeUIView(context: Context) -> CurrencyUITextField {
        return currencyField
    }

    func updateUIView(_ uiView: CurrencyUITextField, context: Context) { }
}

private class CurrencyUITextField: UITextField {

    @Binding private var value: Int
    private let formatter: NumberFormatter

    init(formatter: NumberFormatter, value: Binding<Int>) {
        self.formatter = formatter
        self._value = value
        super.init(frame: .zero)
        self.text = currency(from: Decimal(value.wrappedValue)/100)
        setupViews()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func willMove(toSuperview newSuperview: UIView?) {
        addTarget(self, action: #selector(editingChanged), for: .editingChanged)
        addTarget(self, action: #selector(resetSelection), for: .allTouchEvents)
        keyboardType = .numberPad
        textAlignment = .right
        sendActions(for: .editingChanged)
    }

    override func deleteBackward() {
        text = textValue.digits.dropLast().string
        sendActions(for: .editingChanged)
    }

    private func setupViews() {
        tintColor = .clear
        font = .systemFont(ofSize: 20, weight: .regular)
    }

    @objc private func editingChanged() {
        text = currency(from: decimal)
        resetSelection()
        value = Int(doubleValue * 100)
    }

    @objc private func resetSelection() {
        selectedTextRange = textRange(from: endOfDocument, to: endOfDocument)
    }

    private var textValue: String {
        return text ?? ""
    }

    private var doubleValue: Double {
        return (decimal as NSDecimalNumber).doubleValue
    }

    private var decimal: Decimal {
        return textValue.decimal / pow(10, formatter.maximumFractionDigits)
    }

    private func currency(from decimal: Decimal) -> String {
        return formatter.string(for: decimal) ?? ""
    }
}

private extension StringProtocol where Self: RangeReplaceableCollection {
    var digits: Self { filter (\.isWholeNumber) }
}

private extension String {
    var decimal: Decimal { Decimal(string: digits) ?? 0 }
}

private extension LosslessStringConvertible {
    var string: String { .init(self) }
}


#Preview {
    CurrencyTextFieldPreview()
}

private struct CurrencyTextFieldPreview: View {
    @State var price: Int = 12
    var body: some View {
        VStack {
            Text("price: \(price)")
            Text("price: \(Double(price)/100)")
            CurrencyTextField(
                value: $price
            )
        }
    }
}
