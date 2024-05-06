import Foundation

public extension Double {
    func toCurrencyFormat(fractionDigits: Int = 2) -> String {
        let formatter = NumberFormatter()
        formatter.locale = Locale.current
        formatter.numberStyle = .currency
        formatter.maximumFractionDigits = fractionDigits
        if let formattedTipAmount = formatter.string(from: self as NSNumber) {
            return formattedTipAmount
        }
        return "\(self)"
    }
}
