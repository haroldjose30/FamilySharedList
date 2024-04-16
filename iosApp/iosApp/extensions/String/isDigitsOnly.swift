import Foundation

extension String {
    func isDigitsOnly() -> Bool {
        CharacterSet.decimalDigits.isSuperset(of: CharacterSet(charactersIn: self))
    }
}
