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
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;

public class PositionIOReal implements PositionIO {
    
  TalonFX talonMotor = new TalonFX(2);
  // CANSparkMax sparkMotor = new CANSparkMax(30, MotorType.kBrushless);
  //Control for Spark Max
  private PIDController shooterPivotFeedback = new PIDController(100, 0, 0, 0.02); //Prayer numbers

  private final PositionVoltage positionControl = new PositionVoltage(0).withUpdateFreqHz(0.0);

  private final TalonFXConfiguration config = new TalonFXConfiguration();

  private final StatusSignal<Double> Position;
  private final StatusSignal<Double> Velocity;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> SupplyCurrent;
  private final StatusSignal<Double> TorqueCurrent;
  private final StatusSignal<Double> TempCelsius;
  
  
  public PositionIOReal(){
    Position = talonMotor.getPosition();
    Velocity = talonMotor.getVelocity();
    AppliedVolts = talonMotor.getMotorVoltage();
    SupplyCurrent = talonMotor.getSupplyCurrent();
    TorqueCurrent = talonMotor.getTorqueCurrent();
    TempCelsius = talonMotor.getDeviceTemp();

    BaseStatusSignal.setUpdateFrequencyForAll(
      100.0,
      Position,
      Velocity,
      AppliedVolts,
      SupplyCurrent,
      TorqueCurrent,
      TempCelsius
    );

    config.Slot0.kP = 4;
    config.Slot0.kI = 0;
    config.Slot0.kD = 0;

    config.CurrentLimits.SupplyCurrentLimit = 60.0;
    config.CurrentLimits.SupplyCurrentLimitEnable = true;

    config.MotionMagic.MotionMagicCruiseVelocity = 4;
    config.MotionMagic.MotionMagicAcceleration = 8;
    config.MotionMagic.MotionMagicJerk = 1600;

    talonMotor.getConfigurator().apply(config, 1.0);

    talonMotor.setPosition(0);

  }

  @Override
  public void updateInputs(PositionIOInputs inputs) {
    inputs.isPositionIOMotorConnected =
        BaseStatusSignal.refreshAll(
                Position,
                Velocity,
                AppliedVolts,
                SupplyCurrent,
                TorqueCurrent,
                TempCelsius)
            .isOK();
    inputs.PositionMechs = Units.rotationsToRadians(Position.getValueAsDouble());
    inputs.VelocityRpm = Velocity.getValueAsDouble() * 60.0;
    inputs.AppliedVolts = AppliedVolts.getValueAsDouble();
    inputs.SupplyCurrentAmps = SupplyCurrent.getValueAsDouble();
    inputs.TorqueCurrentAmps = TorqueCurrent.getValueAsDouble();
    inputs.TempCelsius = TempCelsius.getValueAsDouble();
  }

  @Override
  public void setPosition(double pos) //Set the motor position in mechanism rotations
  {
    PositionSubsystem.position = pos;
    talonMotor.setControl(positionControl.withPosition(pos));
    System.out.println(pos);
  }
  
  //Since spark max doesnt got a set position method, we calculate percent output and put method in periodic  
  // @Override
  // public void runSetpointTicks(double setpointTicks) {
  //     double percentOutput = shooterPivotFeedback.calculate(sparkMotor.getEncoder().getPosition(), setpointTicks);
  //     sparkMotor.set(percentOutput);

  // }
}
