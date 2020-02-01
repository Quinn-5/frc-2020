package frc.robot.maps;

import com.chopshop166.chopshoplib.RobotMapFor;
import com.chopshop166.chopshoplib.outputs.SendableSpeedController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

@RobotMapFor("Tempest")
public class TempestMap extends RobotMap {

    @Override
    public DriveMap getDriveMap() {
        return new DriveMap() {
            @Override
            public SendableSpeedController left() {
                return SendableSpeedController.wrap(new SpeedControllerGroup(new WPI_TalonSRX(4), new WPI_TalonSRX(1)));
            }

            @Override
            public SendableSpeedController right() {
                return SendableSpeedController.wrap(new SpeedControllerGroup(new WPI_TalonSRX(2), new WPI_TalonSRX(3)));
            }
        };
    }

    @Override
    public IntakeMap getIntakeMap() {
        return new IntakeMap() {
            @Override
            public SendableSpeedController intake() {
                final Victor intakeMotor = new Victor(0);
                return SendableSpeedController.wrap(intakeMotor);
            }

            @Override
            public SendableSpeedController singulator() {
                final Victor indexingMotor = new Victor(0);
                return SendableSpeedController.wrap(indexingMotor);
            }

            @Override
            public SendableSpeedController pierre() {
                final Victor pierreMotor = new Victor(0);
                return SendableSpeedController.wrap(pierreMotor);
            }
        };
    }

    @Override
    public ShooterMap getShooterMap() {
        return new ShooterMap() {
            @Override
            public SendableSpeedController shooterWheel1() {
                final Talon rollerMotor = new Talon(0);
                return SendableSpeedController.wrap(rollerMotor);
            }

            @Override
            public SendableSpeedController shooterWheel2() {
                final Talon rollerMotor = new Talon(1);
                return SendableSpeedController.wrap(rollerMotor);
            }
        };
    }
}