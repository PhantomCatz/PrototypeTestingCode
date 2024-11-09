// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.PositionSubsystem;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.SparkRelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class PositionIOSparkMax implements PositionIO {
    
  CANSparkMax sparkMotor;
  // CANSparkMax sparkMotor = new CANSparkMax(30, MotorType.kBrushless);
  //Control for Spark Max
  private PIDController shooterPivotFeedback = new PIDController(0.17, 0, 0, 0.02);  
  
  public PositionIOSparkMax(){
    sparkMotor = new CANSparkMax(23, MotorType.kBrushless);

    sparkMotor.restoreFactoryDefaults();
    sparkMotor.setSmartCurrentLimit(30);
    sparkMotor.setIdleMode(IdleMode.kBrake);
    sparkMotor.enableVoltageCompensation(12.0);
    // sparkMotor.getEncoder().setPositionConversionFactor(1);
    sparkMotor.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 32767);
    sparkMotor.burnFlash();
  }

  @Override
  public void updateInputs(PositionIOInputs inputs) {
    inputs.SparkPosMechs = sparkMotor.getEncoder().getPosition();
    inputs.VelocityRads = Units.rotationsToRadians(sparkMotor.getEncoder().getVelocity());
    inputs.SupplyCurrentAmps = sparkMotor.getOutputCurrent();
    inputs.TempCelsius = sparkMotor.getMotorTemperature();
  }
  
  //Since spark max doesnt got a set position method, we calculate percent output and put method in periodic  
  @Override
  public void setPosition(double setpointTicks) {
      double percentOutput = shooterPivotFeedback.calculate(sparkMotor.getEncoder().getPosition(), setpointTicks);
      sparkMotor.set(percentOutput);

  }

  @Override
  public void setPID(double kP, double kI, double kD) {
    sparkMotor.getPIDController().setP(kP);
    sparkMotor.getPIDController().setI(kI);
    sparkMotor.getPIDController().setD(kD);
    System.out.println("kP: " + kP + " kI: " + kI + " kD: " + kD);
    sparkMotor.burnFlash();
  }

  @Override
  public void runCharacterizationMotor(double input) {
    sparkMotor.setVoltage(input); 
  }
}
