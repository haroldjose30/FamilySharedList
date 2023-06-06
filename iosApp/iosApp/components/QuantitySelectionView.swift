import SwiftUI
import Combine

struct QuantitySelectionView: View {
    
    //MARK: State
    @State var value: Int
    var incValue: Int = 1
    var minValue: Int = Int.min
    var maxValue: Int = Int.max
    var onValueChanged: ((Int) -> Void)?
    
    var minusDisabled: Bool {
        value <= minValue
    }
    var moreDisabled: Bool {
        value >= maxValue
    }
    
    private let debouncePublisher = PassthroughSubject<Int, Never>()
   
    
    //MARK: UI
    
    var body: some View {
        HStack {
            
            Image(systemName: "minus.circle")
            .imageScale(.large)
            .disabled(minusDisabled)
            .frame(width: 50)
            .onTapGesture {
                internalOnMinusClicked()
            }
            
            Text("\(value)")
            
            Image(systemName: "plus.circle")
            .imageScale(.large)
            .disabled(moreDisabled)
            .frame(width: 50)
            .onTapGesture {
                internalOnMoreClicked()
            }
        } .onReceive(
            debouncePublisher
                .debounce(
                    for: .milliseconds(500),
                    scheduler: DispatchQueue.main
                )
        ) { debouncedValue in
            onValueChanged?(debouncedValue)
        }
    }
    
    //MARK: Functions
    
    private func internalOnMinusClicked(){
        
        if (self.value > minValue) {
            
            self.value = self.value - incValue
            debouncePublisher.send(self.value)
        }
    }
    
    private func internalOnMoreClicked(){
        
        if (self.value < maxValue) {
            
            self.value = self.value + incValue
            debouncePublisher.send(self.value)
        }
    }
}

struct QuantitySelectionView_Previews: PreviewProvider {
    static var previews: some View {
        QuantitySelectionView(
            value: 1,
            minValue: 1,
            maxValue: 10
        )
    }
}
