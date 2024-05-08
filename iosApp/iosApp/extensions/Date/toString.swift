import Foundation

extension Date {

    func toString(style: Style = .ddMMyyyy) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = style.rawValue
        return dateFormatter.string(from: self)
    }

    enum Style: String {
        case yyyyMMdd = "yyyy-MM-dd"
        case ddMMyyyy = "dd/MM/yyyy"
        case ddMMyyyyHHmmss = "dd/MM/yyyy HH:mm:ss"
    }
}
