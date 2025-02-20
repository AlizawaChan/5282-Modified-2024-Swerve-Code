// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VendorLibraries extends SubsystemBase {
  /** This File is for all the required 2024 vendor libraries you need to install. */

// YAGSL Vendor Library:  https://broncbotz3481.github.io/YAGSL-Lib/yagsl/yagsl.json
// Pheonix 6 Vendor Library: https://maven.ctr-electronics.com/release/com/ctre/phoenix6/latest/Phoenix6-frc2024-latest.json
// Pheonix 5 Vendor Library: https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2024-latest.json
// REV Robotics Vendor Library: https://software-metadata.revrobotics.com/REVLib-2024.json
// Redux Vendor Library:  https://frcsdk.reduxrobotics.com/ReduxLib_2024.json
// Photon Vision Library: https://maven.photonvision.org/repository/internal/org/photonvision/photonlib-json/1.0/photonlib-json-1.0.json
// NavX Library: https://dev.studica.com/releases/2023/NavX.json
// Path Planner Library: https://3015rangerrobotics.github.io/pathplannerlib/PathplannerLib.json


















  public VendorLibraries() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
