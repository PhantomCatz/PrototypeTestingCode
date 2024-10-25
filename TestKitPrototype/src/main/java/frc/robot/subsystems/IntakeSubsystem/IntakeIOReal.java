// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.IntakeSubsystem;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeIOReal implements IntakeIO {
  
  TalonFX talonMotor = new TalonFX(0);
  CANSparkMax sparkMotor = new CANSparkMax(0, MotorType.kBrushless);
  
  /** Creates a new IntakeIOReal. */
  public IntakeIOReal() {
   
  }

  //Sets the motor speed to "speed" found in IntakeIO. Currently the number is 1000%
  @Override
  public void runMotor() {  
    talonMotor.set(IntakeIOInputs.speed);
    sparkMotor.set(IntakeIOInputs.speed);

    Logger.recordOutput("Intake Motor Speed", IntakeIOInputs.speed); // Logs the motor speed and names it to "Intake Motor Speed" in AdvantageScope

  }
}
