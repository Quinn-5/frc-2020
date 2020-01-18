/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.chopshop166.chopshoplib.RobotUtils;
import com.chopshop166.chopshoplib.controls.ButtonXboxController;
import com.google.common.io.Resources;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.maps.RobotMap;
import frc.robot.maps.TempestMap;
import frc.robot.subsystems.ControlPanel;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private Command autonomousCommand;
    private ButtonXboxController driveController = new ButtonXboxController(1);

  RobotMap map = RobotUtils.getMapForName("Tempest", RobotMap.class, "frc.robot.maps", new RobotMap() {
  });

    final private Drive drive = new Drive(map.getDriveMap());
    final private Intake intake = new Intake(map.getIntakeMap());
    final private Shooter shooter = new Shooter(map.getShooterMap());
    final private ControlPanel controlPanel = new ControlPanel(map.getControlPanelMap());
    final private Lift lift = new Lift(map.getLiftMap());

    final private SendableChooser<Command> autoChooser = new SendableChooser<>();
    final private static String UNKNOWN_VALUE = "???";

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        configureButtonBindings();

        autoChooser.setDefaultOption("Nothing", new InstantCommand());

        final ShuffleboardTab tab = Shuffleboard.getTab("BuildData");

        try {
            final URL manifestURL = Resources.getResource("META-INF/MANIFEST.MF");
            final Manifest manifest = new Manifest(manifestURL.openStream());
            final Attributes attrs = manifest.getMainAttributes();

            tab.add("Git Hash", attrs.getValue("Git-Hash")).withPosition(0, 0);
            tab.add("Git Branch", attrs.getValue("Git-Branch")).withPosition(3, 0);
            tab.add("Build Time", attrs.getValue("Build-Time")).withPosition(1, 0).withSize(2, 1);
            tab.add("Git Files", attrs.getValue("Git-Files")).withPosition(0, 1).withSize(4, 1);
        } catch (IOException ex) {
            // Could not read the manifest, just send dummy values
            tab.add("Git Hash", UNKNOWN_VALUE).withPosition(0, 0);
            tab.add("Git Branch", UNKNOWN_VALUE).withPosition(0, 1);
            tab.add("Git Files", UNKNOWN_VALUE).withPosition(1, 0);
            tab.add("Build Time", UNKNOWN_VALUE).withPosition(1, 1);
        }

        drive.setDefaultCommand(drive.drive(driveController::getTriggers, () -> driveController.getX(Hand.kLeft)));
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler. This is responsible for polling buttons, adding
        // newly-scheduled
        // commands, running already-scheduled commands, removing finished or
        // interrupted commands,
        // and running subsystem periodic() methods. This must be called from the
        // robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
        RobotUtils.resetAll(this);
    }

    /**
     * This autonomous runs the autonomous command selected by your
     * {@link RobotContainer} class.
     */
    @Override
    public void autonomousInit() {
        autonomousCommand = autoChooser.getSelected();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        driveController.getButton(Button.kA).whenHeld(intake.intake());
        driveController.getButton(Button.kB).whenHeld(intake.discharge());
        driveController.getButton(Button.kX).whenHeld(controlPanel.spinForwards());
        driveController.getButton(Button.kY).whenHeld(controlPanel.spinBackwards());

    }
}
