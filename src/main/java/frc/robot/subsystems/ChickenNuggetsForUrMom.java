// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChickenNuggetsForUrMom extends SubsystemBase {

  private DutyCycleEncoder chickenPoop;

  /** Creates a new ChickenNuggetsForUrMom. */
  public ChickenNuggetsForUrMom() {
    chickenPoop = new DutyCycleEncoder(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("pos", chickenPoop.getAbsolutePosition() * 360);
  }
}
