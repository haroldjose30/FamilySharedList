/*
package dev.haroldjose.familysharedlist.presentationLayer.pages.barcodeScanner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import dev.haroldjose.familysharedlist.DispatcherBackground
import dev.haroldjose.familysharedlist.Logger
import dev.haroldjose.familysharedlist.NsQueueDispatcher
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.AVFoundation.*
import platform.AVFoundation.AVCaptureDeviceDiscoverySession.Companion.discoverySessionWithDeviceTypes
import platform.AVFoundation.AVCaptureDeviceInput.Companion.deviceInputWithDevice
import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate
import platform.CoreGraphics.CGRect
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSSelectorFromString
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.darwin.dispatch_get_main_queue

private sealed interface CameraAccess {
    object Undefined : CameraAccess
    object Denied : CameraAccess
    object Authorized : CameraAccess
}

private val deviceTypes = listOf(
    AVCaptureDeviceTypeBuiltInWideAngleCamera,
    AVCaptureDeviceTypeBuiltInDualWideCamera,
    AVCaptureDeviceTypeBuiltInDualCamera,
    AVCaptureDeviceTypeBuiltInUltraWideCamera,
    AVCaptureDeviceTypeBuiltInDuoCamera
)

@Composable
actual fun QrScannerScreen(modifier: Modifier, onQrCodeScanned: (String) -> Unit) {
    var cameraAccess: CameraAccess by remember { mutableStateOf(CameraAccess.Undefined) }
    LaunchedEffect(Unit) {
        when (AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)) {
            AVAuthorizationStatusAuthorized -> {
                cameraAccess = CameraAccess.Authorized
            }

            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> {
                cameraAccess = CameraAccess.Denied
            }

            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(
                    mediaType = AVMediaTypeVideo
                ) { success ->
                    cameraAccess = if (success) CameraAccess.Authorized else CameraAccess.Denied
                }
            }
        }
    }
    Box(
        modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when (cameraAccess) {
            CameraAccess.Undefined -> {
                // Waiting for the user to accept permission
            }

            CameraAccess.Denied -> {
                Text("Sua camera não está disponivel!", color = Color.White)
            }

            CameraAccess.Authorized -> {
                AuthorizedCamera(onQrCodeScanned)
            }
        }
    }
}

@Composable
private fun BoxScope.AuthorizedCamera(onQrCodeScanned: (String) -> Unit) {
    val camera: AVCaptureDevice? = remember {
        discoverySessionWithDeviceTypes(
            deviceTypes = deviceTypes,
            mediaType = AVMediaTypeVideo,
            position = AVCaptureDevicePositionBack,
        ).devices.firstOrNull() as? AVCaptureDevice
    }
    if (camera != null) {
        RealDeviceCamera(camera, onQrCodeScanned)
    } else {
        Text(
            """
            Camera is not available on simulator.
            Please try to run on a real iOS device.
        """.trimIndent(), color = Color.White
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun RealDeviceCamera(
    camera: AVCaptureDevice,
    onQrCodeScanned: (String) -> Unit,
) {
    Logger.d("RealDeviceCamera","Activated")
    val capturePhotoOutput = remember { AVCapturePhotoOutput() }
    var actualOrientation by remember {
        mutableStateOf(
            AVCaptureVideoOrientationPortrait
        )
    }

    val captureSession: AVCaptureSession = remember {
        AVCaptureSession().also { captureSession ->
            captureSession.sessionPreset = AVCaptureSessionPresetPhoto
            val captureDeviceInput: AVCaptureDeviceInput =
                deviceInputWithDevice(device = camera, error = null)!!
            captureSession.addInput(captureDeviceInput)
            captureSession.addOutput(capturePhotoOutput)

            //Initialize an AVCaptureMetadataOutput object and set it as the output device to the capture session.
            val metadataOutput = AVCaptureMetadataOutput()
            if (captureSession.canAddOutput(metadataOutput)) {
                //Set delegate and use default dispatch queue to execute the call back
                // fixed with https://youtrack.jetbrains.com/issue/KT-45755/iOS-delegate-protocol-is-empty
                captureSession.addOutput(metadataOutput)
                metadataOutput.setMetadataObjectsDelegate(objectsDelegate = object : NSObject(),
                    AVCaptureMetadataOutputObjectsDelegateProtocol {
                    override fun captureOutput(
                        output: AVCaptureOutput,
                        didOutputMetadataObjects: List<*>,
                        fromConnection: AVCaptureConnection
                    ) {
                        Logger.d("QrScannerScreen","captureOutput executed")
                        didOutputMetadataObjects.firstOrNull()?.let { metadataObject ->
                            Logger.d("QrScannerScreen","metadataObject readed")
                            val readableObject = metadataObject as? AVMetadataMachineReadableCodeObject
                            val code = readableObject?.stringValue ?: ""
                            AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
                            Logger.d("QrScannerScreen","code $code")
                            if (code.isNotEmpty()) {
                                onQrCodeScanned(code)
                                captureSession.stopRunning()
                            }
                        }
                    }
                }, queue = dispatch_get_main_queue())

                metadataOutput.metadataObjectTypes = metadataOutput.availableMetadataObjectTypes()
            }

        }
    }
    val cameraPreviewLayer = remember {
        AVCaptureVideoPreviewLayer(session = captureSession)
    }

    DisposableEffect(Unit) {
        class OrientationListener : NSObject() {
            @Suppress("UNUSED_PARAMETER")
            @ObjCAction
            fun orientationDidChange(arg: NSNotification) {
                val cameraConnection = cameraPreviewLayer.connection
                if (cameraConnection != null) {
                    actualOrientation = when (UIDevice.currentDevice.orientation) {
                        UIDeviceOrientation.UIDeviceOrientationPortrait ->
                            AVCaptureVideoOrientationPortrait

                        UIDeviceOrientation.UIDeviceOrientationLandscapeLeft ->
                            AVCaptureVideoOrientationLandscapeRight

                        UIDeviceOrientation.UIDeviceOrientationLandscapeRight ->
                            AVCaptureVideoOrientationLandscapeLeft

                        UIDeviceOrientation.UIDeviceOrientationPortraitUpsideDown ->
                            AVCaptureVideoOrientationPortrait

                        else -> cameraConnection.videoOrientation
                    }
                    cameraConnection.videoOrientation = actualOrientation
                }
                capturePhotoOutput.connectionWithMediaType(AVMediaTypeVideo)
                    ?.videoOrientation = actualOrientation
            }
        }

        val listener = OrientationListener()
        val notificationName = platform.UIKit.UIDeviceOrientationDidChangeNotification
        NSNotificationCenter.defaultCenter.addObserver(
            observer = listener,
            selector = NSSelectorFromString(
                OrientationListener::orientationDidChange.name + ":"
            ),
            name = notificationName,
            `object` = null
        )
        onDispose {
            NSNotificationCenter.defaultCenter.removeObserver(
                observer = listener,
                name = notificationName,
                `object` = null
            )
        }
    }
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        background = Color.Black,
        factory = {
            val cameraContainer = UIView()
            cameraContainer.layer.addSublayer(cameraPreviewLayer)
            cameraPreviewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            NsQueueDispatcher(dispatch_get_main_queue())
                .dispatch(
                    context = Dispatchers.Default,
                    block = Runnable {
                        captureSession.startRunning()
                    }
                )
            //captureSession.startRunning()
            cameraContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            cameraPreviewLayer.setFrame(rect)
            CATransaction.commit()
        },
    )
}
*/
