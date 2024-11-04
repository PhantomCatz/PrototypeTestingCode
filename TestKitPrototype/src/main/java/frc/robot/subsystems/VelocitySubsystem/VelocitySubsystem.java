// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.VelocitySubsystem;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Utilities.LoggedTunableNumber;
import frc.robot.subsystems.OnOffSubsystem.OnOffIOReal;
import frc.robot.subsystems.PositionSubsystem.PositionIO;
import frc.robot.subsystems.VelocitySubsystem.VelocityIO.VelocityIOInputs;

public class VelocitySubsystem extends SubsystemBase {

  private final VelocityIO io;
  private final VelocityIOInputsAutoLogged inputs = new VelocityIOInputsAutoLogged();

  private double targetRpm;

  private LoggedTunableNumber tunableNumber = new LoggedTunableNumber("VelocitySetpositionRPM", 500);


  /** Creates a new VelocitySubsystem. */
  public VelocitySubsystem() {
        io = new VelocityIOReal() {};
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Velocity", inputs);
    // This method will be called once per scheduler run
    if(DriverStation.isDisabled()) {

    } else {
      io.runVelocity(targetRpm);
    }

    Logger.recordOutput("Velocity", targetRpm);
  }

  public Command onVelocity(){
    return startEnd(() -> targetRpm = tunableNumber.get(), () -> targetRpm = 0.0);
  }

  // public Command setVelocityOff() {
  //   return startEnd(null, null)
  // }

}

