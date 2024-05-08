import Foundation
import shared
extension Kotlinx_datetimeLocalDateTime {

    func toDateNoTime() -> Date {
        Calendar.current.date(
            from: DateComponents(
                year: self.year.toInt(),
                month: self.monthNumber.toInt(),
                day: self.dayOfMonth.toInt()
            )
        )!
    }
}
