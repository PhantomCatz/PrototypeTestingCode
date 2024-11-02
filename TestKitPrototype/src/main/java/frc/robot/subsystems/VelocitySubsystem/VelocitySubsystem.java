// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.OnOffSubsystem.OnOffIOReal;
import frc.robot.subsystems.PositionSubsystem.PositionIO;
import frc.robot.subsystems.VelocitySubsystem.VelocityIO.VelocityIOInputs;

public class VelocitySubsystem extends SubsystemBase {

  private final VelocityIO io;
  private final VelocityIOInputsAutoLogged inputs = new VelocityIOInputsAutoLogged();


  /** Creates a new VelocitySubsystem. */
  public VelocitySubsystem() {
        io = new VelocityIOReal() {};
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Velocity", inputs);
    // This method will be called once per scheduler run
  }

  public Command onVelocity(){
    return runOnce(() -> io.runVelocity(10));
  }
}

