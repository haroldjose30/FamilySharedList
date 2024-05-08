import Foundation
import shared

extension Kotlinx_datetimeLocalDateTime {
    
    func toDatetime() -> Date {
        Calendar.current.date(
            from: DateComponents(
                year: self.year.toInt(),
                month: self.monthNumber.toInt(),
                day: self.dayOfMonth.toInt(),
                hour: self.hour.toInt(),
                minute: self.minute.toInt(),
                second: self.second.toInt(),
                nanosecond: self.nanosecond.toInt()
            )
        )!
    }
}
