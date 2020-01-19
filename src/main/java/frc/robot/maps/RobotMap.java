package frc.robot.maps;

import com.chopshop166.chopshoplib.outputs.MockSpeedController;
import com.chopshop166.chopshoplib.outputs.SendableSpeedController;

public interface RobotMap {
    public interface DriveMap {
        public SendableSpeedController left();

        public SendableSpeedController right();
    }

    public DriveMap getDriveMap();

    public interface IntakeMap {
        default public SendableSpeedController roller() {
            return new MockSpeedController();
        }
    }

    public IntakeMap getIntakeMap();

    public interface ShooterMap {
        default public SendableSpeedController shooterWheel() {
            return new MockSpeedController();
        }
    }

    public ShooterMap getShooterMap();

    public interface ControlPanelMap {
        default public SendableSpeedController spinner() {
            return new MockSpeedController();
        }
    }

    public ControlPanelMap getControlPanelMap();

    public interface LiftMap {
        default public SendableSpeedController elevator() {
            return new MockSpeedController();
        }
    }

    public LiftMap getLiftMap();

    public interface LEDMap {
    }

    public LEDMap getLEDMap();
}