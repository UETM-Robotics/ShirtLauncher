// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private DriveTrain drive = new DriveTrain();
  private Pneumatics pneumatics = new Pneumatics();

  private Joystick left = new Joystick(2);
  private Joystick right = new Joystick(0);

  double lp = 0;
  double rp = 0;

  double lDriveModifier = 1.0;
  double rDriveModifier = 1.0;

  boolean compButtonPressed = false;
  boolean compressing = false;

  boolean triggerPressed = false;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    drive.init();
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

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  private final CANSparkMax fl = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax hl = new CANSparkMax(2, MotorType.kBrushless);

  private final CANSparkMax fr = new CANSparkMax(4, MotorType.kBrushless);
  
  private final Joystick stickL = new Joystick(0);
  private final Joystick stickR = new Joystick(1);

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    pneumatics.stop();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    fl.set( -stickL.getRawAxis(1) );
    hl.set( -stickL.getRawAxis(1) );

    fr.set( -stickR.getRawAxis(1) );


    // lp = left.getRawAxis(1) * lDriveModifier;
    // rp = right.getRawAxis(1) * rDriveModifier;

    // drive.set(lp, rp);

    if (stickR.getRawButton(2) && !compButtonPressed) {
      if (compressing) {
        pneumatics.stop();
        compressing = false;
      } else {
        pneumatics.start();
        compressing = true;
      }

      compButtonPressed = true;

    } else if (!right.getRawButton(2)) {
      compButtonPressed = false;
    }

    if (stickR.getRawButton(1) && !triggerPressed) {
      pneumatics.shoot();
      triggerPressed = true;
    } else if (!stickR.getRawButton(1)) {
      triggerPressed = false;
    }

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
