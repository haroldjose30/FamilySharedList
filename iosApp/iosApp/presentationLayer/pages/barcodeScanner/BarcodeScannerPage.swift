import SwiftUI
import AVFoundation

struct BarcodeScannerPage: View {
    var onSuccess: (String) -> Void
    @State private var scanResultOldValue: String?
    @State private var scanResult = ""
    @State private var captureSession: AVCaptureSession? = AVCaptureSession()
    var body: some View {
        ZStack(alignment: .center) {

            QRScanner(
                captureSession: $captureSession,
                result: $scanResult
            ).edgesIgnoringSafeArea(.all)

            BarcodeOverlay()
                .edgesIgnoringSafeArea(.all)

            VStack {
                Text("Leia o código de barras")
                    .padding()
                    .foregroundColor(.white)
                    .background(Color.gray)
                    .font(.subheadline)
                    .cornerRadius(20)

                Spacer()
            }
        }
        .onChange(of: scanResult) { newValue in
            guard scanResultOldValue != newValue 
            else {
                return
            }
            scanResultOldValue = newValue
            onSuccess(newValue)
        }
    }
}

struct BarcodeOverlay: View {
    var body: some View {
        ZStack {
            Rectangle()
                .fill(Color.clear)
                .frame(width: 300, height: 200)
                .overlay(
                    Color.red
                        .opacity(0.8)
                        .frame(height: 3 )
                        .padding(8)
                )
            .background(Color.black.opacity(0.5))
            .cornerRadius(20)
        }
    }
}

struct QRScanner: UIViewControllerRepresentable {

    @Binding var captureSession: AVCaptureSession?
    @Binding var result: String


    func makeUIViewController(context: Context) -> QRScannerController {
        let controller = QRScannerController()
        controller.captureSession = captureSession
        controller.delegate = context.coordinator

        return controller
    }

    func makeCoordinator() -> Coordinator {
        Coordinator($result)
    }

    func updateUIViewController(_ uiViewController: QRScannerController, context: Context) {
    }
}

class QRScannerController: UIViewController {
    var captureSession: AVCaptureSession?
    var videoPreviewLayer: AVCaptureVideoPreviewLayer?
    var qrCodeFrameView: UIView?

    var delegate: AVCaptureMetadataOutputObjectsDelegate?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Get the back-facing camera for capturing videos
        guard 
            let captureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back),
            var captureSession
        else {
            print("Failed to get the camera device")
            return
        }

        let videoInput: AVCaptureDeviceInput

        do {
            // Get an instance of the AVCaptureDeviceInput class using the previous device object.
            videoInput = try AVCaptureDeviceInput(device: captureDevice)

        } catch {
            // If any error occurs, simply print it out and don't continue any more.
            print(error)
            return
        }

        // Set the input device on the capture session.
        captureSession.addInput(videoInput)

        // Initialize a AVCaptureMetadataOutput object and set it as the output device to the capture session.
        let captureMetadataOutput = AVCaptureMetadataOutput()
        captureSession.addOutput(captureMetadataOutput)

        // Set delegate and use the default dispatch queue to execute the call back
        captureMetadataOutput.setMetadataObjectsDelegate(delegate, queue: DispatchQueue.main)
        captureMetadataOutput.metadataObjectTypes = [
            .qr,
            .ean8,
            .ean13,
            .pdf417,
            .dataMatrix,
            .upce
        ]
        if #available(iOS 15.4, *) {
            captureMetadataOutput.metadataObjectTypes.append(.gs1DataBar)
            captureMetadataOutput.metadataObjectTypes.append(.gs1DataBarLimited)
            captureMetadataOutput.metadataObjectTypes.append(.gs1DataBarExpanded)
        }

        // Initialize the video preview layer and add it as a sublayer to the viewPreview view's layer.
        videoPreviewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        videoPreviewLayer?.videoGravity = AVLayerVideoGravity.resizeAspectFill
        videoPreviewLayer?.frame = view.layer.bounds
        view.layer.addSublayer(videoPreviewLayer!)

        // Start video capture.
        DispatchQueue.global(qos: .background).async {
            captureSession.startRunning()
        }
    }
}

class Coordinator: NSObject, AVCaptureMetadataOutputObjectsDelegate {

    @Binding var scanResult: String

    init(_ scanResult: Binding<String>) {
        self._scanResult = scanResult
    }

    func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {

        // Check if the metadataObjects array is not nil and it contains at least one object.
        if metadataObjects.count == 0 {
            return
        }

        // Get the metadata object.
        //metadataObj.type == AVMetadataObject.ObjectType.qr
        if let metadataObj = metadataObjects[0] as? AVMetadataMachineReadableCodeObject,
           let result = metadataObj.stringValue {
            scanResult = result
            print(scanResult)

        }
    }
}

#Preview {
    BarcodeScannerPage(onSuccess: {_ in })
}
