// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

public class PIDMotorGroup extends PIDSubsystem implements MotorController {
  private MotorControllerGroup motors;

  private double kpLow;
  private double kpHigh;

  private double maxVelocityLow;
  private double maxVelocityHigh;

  private Encoder encoder;

  private SimpleMotorFeedforward ffLow;
  private SimpleMotorFeedforward ffHigh;

  private boolean inLowGear;

  /** Creates a new PIDMotorGroup. */
  public PIDMotorGroup(MotorControllerGroup motors, double maxVelocityLow, double ksLow, Encoder encoder, double kpLow, double maxVelocityHigh, double ksHigh, double kpHigh) {
    super(
        // The PIDController used by the subsystem
        new PIDController(kpLow, 0, 0));
          this.motors = motors;
          this.maxVelocityLow = maxVelocityLow;
          this.maxVelocityHigh = maxVelocityHigh;
          this.encoder = encoder;
          this.kpLow = kpLow;
          this.kpHigh = kpHigh;
          this.inLowGear = true;

          ffLow = new SimpleMotorFeedforward(ksLow, 12.0/maxVelocityLow);
          ffHigh = new SimpleMotorFeedforward(ksHigh, 12.0/maxVelocityHigh);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    if(inLowGear) {
      motors.setVoltage(output + ffLow.calculate(setpoint));
    } else {
      motors.setVoltage(output + ffHigh.calculate(setpoint));
    }
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return encoder.getRate();
  }

  @Override
  public void set(double speed) {
    // TODO Auto-generated method stub
    if(inLowGear) {
      setSetpoint(speed * maxVelocityLow);
    }
    else {
      setSetpoint(speed * maxVelocityHigh);
    }
  }

  @Override
  public double get() {
    // TODO Auto-generated method stub
    return motors.get();
  }

  @Override
  public void setInverted(boolean isInverted) {
    // TODO Auto-generated method stub
    motors.setInverted(isInverted);
  }

  @Override
  public boolean getInverted() {
    // TODO Auto-generated method stub
    return motors.getInverted();
  }

  @Override
  public void stopMotor() {
    // TODO Auto-generated method stub
    setSetpoint(0);
  }

  public void shiftGear(boolean inLowGear) {
    this.inLowGear = inLowGear;

    if(inLowGear) {
      super.getController().setP(kpLow);
    }
    else {
      super.getController().setP(kpHigh);
    }
  }
}