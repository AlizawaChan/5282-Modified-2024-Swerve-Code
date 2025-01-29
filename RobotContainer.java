// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


// ***************** DO NOT delete unused imports from this file *****************/
package frc.robot;


import frc.robot.commands.Autos;
import frc.robot.commands.TeleopSwerve;
import frc.robot.subsystems.AprilTags;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.swerve.rev.RevSwerve;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // add your subsystem here in the same format as the lines below here; Example
 
  //private final CommandXboxController m_xboxDriverController0;
  
  //private static final double Double = 0;
  // Controllers Defined
  private final Joystick driver = new Joystick(0); //swerve drive
  private final CommandXboxController m_xboxController1 = new CommandXboxController(1); //mechanisms

  /* Drive Controls Defined*/
  private final int translationAxis = XboxController.Axis.kLeftY.value;
  private final int strafeAxis = XboxController.Axis.kLeftX.value;
  private final int rotationAxis = XboxController.Axis.kRightX.value;
  
  //Limit Switch Method doubles for button code
  private final int m_intakeRollerInButton = XboxController.Button.kB.value;
  private final int m_intakeArmUpButton = XboxController.Button.kB.value;

  /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);

  // Subsystems Defined
  private final AprilTags m_aprilTags;
  //private final FloorIntakeRoller m_floorIntakeRoller;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  //private final FloorIntakeArm m_floorIntakeArm;
 // private final ShooterAndSourceIntake m_shooterSubsystem;
  private final RevSwerve s_Swerve = new RevSwerve();
  //private final Hanger m_hanger = new Hanger();

  SendableChooser<Command> m_AutonomousChooser = new SendableChooser<>();
   
  //CommandJoystick driverController = new CommandJoystick(1);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  /*private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  */
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
      //SparkMax motorController22 = new SparkMax(22, SparkLowLevel.MotorType.kBrushless); //shooter
      //SparkMax motorController23 = new SparkMax(23, SparkLowLevel.MotorType.kBrushless); //shooter
       //TalonSRX motorController18 = new TalonSRX(18); //code in subsystem - intake arm
       //TalonSRX motorController19 = new TalonSRX(19); //code in subsystem - intake roller
       //TalonSRX motorController20 = new TalonSRX(20); //code in subsystem - intake roller/unused
       //TalonSRX motorController21 = new TalonSRX(21); //code in subsystem - hanger
      //m_shooterSubsystem = new ShooterAndSourceIntake(motorController22, motorController23);
      m_aprilTags = new AprilTags();
      //m_floorIntakeRoller = new FloorIntakeRoller();
      //m_floorIntakeArm = new FloorIntakeArm();
      

    s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> false
            )
        );
    
  m_AutonomousChooser.setDefaultOption("Nothing", getAutonomousCommand());
  //m_AutonomousChooser.addOption("Straight Out Of Start Zone", Autos.driveStraightOutOfStartZone(s_Swerve)); 
 //m_AutonomousChooser.addOption("Shoot in Speaker", Autos.AutoShootInSpeakerCommand(m_shooterSubsystem, m_floorIntakeRoller));
  m_AutonomousChooser.addOption("Additional Auto Option", getAutonomousCommand());
  m_AutonomousChooser.addOption("Additional Auto Option", getAutonomousCommand());
  m_AutonomousChooser.addOption("Additional Auto Option", getAutonomousCommand());
  SmartDashboard.putData(m_AutonomousChooser);
    // Configure the trigger bindings
    configureBindings();
    
  }
  
  //***************************** Button Returns for the RobotContaner File - Returns for these button are found in the subsystem files ***************************************/

  private void configureBindings() {
      // Assign commands to buttons
      //new JoystickButton(m_xboxController1, 3).onTrue(new SpinMotorInFromSourceCommand(m_shooterSubsystem::buttonACondition)); // Just trying new button code...to be continued later or delete.

      //shooter
      /*new Trigger(m_shooterSubsystem::buttonACondition)
            .onTrue(new SpinMotorInFromSourceCommand(m_shooterSubsystem));
            m_xboxController1.a().whileTrue(m_shooterSubsystem.spinMotorInFromSource());
            m_xboxController1.a().whileFalse(m_shooterSubsystem.stopMotors());
      
      new Trigger(m_shooterSubsystem::buttonXCondition)
            .onTrue(new SpinMotorOutToAmpCommand(m_shooterSubsystem));
            m_xboxController1.x().whileTrue(m_shooterSubsystem.spinMotorOutAtAmp());
            m_xboxController1.x().whileFalse(m_shooterSubsystem.stopMotors());
      
      new Trigger(m_shooterSubsystem::rightTriggerCondition)
            .onTrue(new SpinMotorOutToAmpCommand(m_shooterSubsystem));
            m_xboxController1.rightTrigger().whileTrue(m_shooterSubsystem.spinMotorOutAtSpeaker());
            m_xboxController1.rightTrigger().whileFalse(m_shooterSubsystem.stopMotors());
      

        //floor intake roller
      new Trigger(m_floorIntakeRoller::buttonBCondition)
          .onTrue(new SpinFloorIntakeInCommand(m_floorIntakeRoller));
          m_xboxController1.b().whileTrue(m_floorIntakeRoller.spinFloorIntakeRollerInLimitSwitch(m_intakeRollerInButton)); //button 2
          m_xboxController1.b().whileFalse(m_floorIntakeRoller.stopFloorIntakeRoller()); //button 2
          
      new Trigger(m_floorIntakeRoller::buttonYCondition)
          .onTrue(new SpinFloorIntakeOutCommand(m_floorIntakeRoller));
          m_xboxController1.y().whileTrue(m_floorIntakeRoller.spinFloorIntakeRollerOut()); //button 4
          m_xboxController1.y().whileFalse(m_floorIntakeRoller.stopFloorIntakeRoller()); //button 4
        
      new Trigger(m_floorIntakeRoller::rightTriggerCondition)
      .onTrue(new SpinFloorIntakeOutCommand(m_floorIntakeRoller));
          m_xboxController1.rightTrigger().whileTrue(m_floorIntakeRoller.spinFloorIntakeRollerOut());
          m_xboxController1.rightTrigger().whileFalse(m_floorIntakeRoller.stopFloorIntakeRoller()); //button 4
        

        //floor intake arm
      new Trigger(m_floorIntakeArm::buttonLeftBumper)
          .onTrue(new FloorIntakeArmUpCommand(m_floorIntakeArm));
          m_xboxController1.b().whileTrue(m_floorIntakeArm.FloorIntakeArmUpGamePieceLimitSwitch(m_intakeArmUpButton)); //button 2
          m_xboxController1.b().whileFalse(m_floorIntakeArm.stopFloorIntakeArm()); //button 2
          m_xboxController1.leftBumper().whileTrue(m_floorIntakeArm.FloorIntakeArmUpLimitSwitch(m_intakeArmUpButton)); //button 5
          m_xboxController1.leftBumper().whileFalse(m_floorIntakeArm.stopFloorIntakeArm()); //button 5
           
      new Trigger(m_floorIntakeArm::buttonRightBumper)
          .onTrue(new FloorIntakeArmDownCommand(m_floorIntakeArm));
          m_xboxController1.rightBumper().whileTrue(m_floorIntakeArm.FloorIntakeArmDown());
          m_xboxController1.rightBumper().whileFalse(m_floorIntakeArm.stopFloorIntakeArm());
         
          
      // Hanger
      new Trigger(m_hanger::ButtonRightStickClickCondition)
          .onTrue(new HangerUpCommand(m_hanger));
          m_xboxController1.leftStick().whileTrue(m_hanger.HangerUp());
          m_xboxController1.leftStick().whileFalse(m_hanger.StopHanger());
        
      new Trigger(m_hanger::ButtonRightStickClickCondition)
          .onTrue(new HangerDownCommand(m_hanger));
          m_xboxController1.rightStick().whileTrue(m_hanger.HangerDown());
          m_xboxController1.rightStick().whileFalse(m_hanger.StopHanger());
        
          /*
        new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));
        */
       
        //m_xboxController1.b().whileTrue(m_floorIntakeArm.FloorIntakeArmUpLimitSwitch(rotationAxis));
        //m_xboxController1.b().whileFalse(m_floorIntakeArm.FloorIntakeArmUpLimitSwitch(rotationAxis));*/
        
       
       
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        /* // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
            // cancelling on release.
            m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand()); */
         /*The Xbox controller buttons and triggers are typically numbered as follows:
          Buttons:
          A: 1
          B: 2
          X: 3
          Y: 4
          LB (Left Bumper): 5
          RB (Right Bumper): 6
          Back: 7
          Start: 8
          Left Stick Click: 9
          Right Stick Click: 10
          Left Trigger: Axis 2 (value ranges from 0.0 to 1.0)
          Right Trigger: Axis 3 (value ranges from 0.0 to 1.0) */
               
        /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
 
}


  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //return Autos.driveStraightOutOfStartZone(s_Swerve);
    return m_AutonomousChooser.getSelected();
    // An example command will be run in autonomous
    //return drivebase.getAutonomousCommand("New Path", true);
  }

  public void setDriveMode()
  {
    //drivebase.setDefaultCommand();
  }

  

}

//Credit for this code: Team 5282 Programmers, Chat GPT helped a little, YAGSL example code and library imports