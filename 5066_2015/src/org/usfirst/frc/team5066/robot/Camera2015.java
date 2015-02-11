package org.usfirst.frc.team5066.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;

public class Camera2015 {

	CameraServer cs;
	String camName;
	Image frame;
	int session;
	int camQuality;
	public Camera2015(String camName, int camQuality) {
		camName = this.camName;
		camQuality = this.camQuality;
	}
	
	public void startSimpleCamera() {
		cs = CameraServer.getInstance();
		cs.setQuality(camQuality);
		cs.startAutomaticCapture(camName);
	}
	
	public void initCameraForProcessing() {

        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        
//         the camera name (ex cam0) can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera(camName,
        NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
	}
	
	public void processImages() {
        NIVision.IMAQdxStartAcquisition(session);
        
        NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

            NIVision.IMAQdxGrab(session, frame, 1);
            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
            
        CameraServer.getInstance().setImage(frame);

//             robot code here! 
//            Timer.delay(0.005);		 wait for a motor update time
            
        NIVision.IMAQdxStopAcquisition(session);
	}
	
}
