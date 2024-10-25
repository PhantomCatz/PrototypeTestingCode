// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

  private final IntakeIO io;

  /** Creates a new OnOffSubsystem. */
  public IntakeSubsystem() {
    io = new IntakeIO() {};
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public Command runMotor() {
    return runOnce(() -> io.runMotor());
  }
}