// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.OnOffSubsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.PositionSubsystem.PositionIOInputsAutoLogged;

public class OnOffSubsystem extends SubsystemBase {

  private final OnOffIO io;
  private final OnOffIOInputsAutoLogged inputs = new OnOffIOInputsAutoLogged();


  /** Creates a new OnOffSubsystem. */
  public OnOffSubsystem() {
    io = new OnOffIOReal() {};
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("OnOff", inputs);
    // This method will be called once per scheduler run
  }

  public Command toggleOnOffMotor() {
    return runOnce(() -> io.toggleMotor());
  }
}
