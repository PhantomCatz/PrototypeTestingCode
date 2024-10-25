// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.PositionSubsystem;


import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;

public class PositionIOReal implements PositionIO {
    
  TalonFX talonMotor = new TalonFX(2);
  CANSparkMax sparkMotor = new CANSparkMax(2, MotorType.kBrushless);
  //Control for Spark Max
  private PIDController shooterPivotFeedback = new PIDController(100, 0, 0, 0.02); //Prayer numbers


  /** Creates a new IntakeIOReal. */
  public PositionIOReal() {
   
  }

  @Override
  public void setPosition(double pos) //Set the motor position in mechanism rotations
  { 
    talonMotor.setPosition(pos);
  }
  
  //Since spark max doesnt got a set position method, we calculate percent output and put method in periodic  
  @Override
  public void runSetpointTicks(double setpointTicks) {
      double percentOutput = shooterPivotFeedback.calculate(sparkMotor.getEncoder().getPosition(), setpointTicks);
      sparkMotor.set(percentOutput);

  }
}
