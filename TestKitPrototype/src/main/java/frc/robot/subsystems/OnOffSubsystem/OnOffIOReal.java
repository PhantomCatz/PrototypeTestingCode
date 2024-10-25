// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.OnOffSubsystem;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

public class OnOffIOReal implements OnOffIO {
    
  TalonFX talonMotor = new TalonFX(1);
  CANSparkMax sparkMotor = new CANSparkMax(1, MotorType.kBrushless);

  /** Creates a new IntakeIOReal. */
  public OnOffIOReal() {
   
  }

  @Override
  public void toggleMotor() { //Toggles the motor speed between 0% and 100%
    if(talonMotor.get() != 0) {
      talonMotor.set(0);
      sparkMotor.set(0);
    } else {
      talonMotor.set(1);
      sparkMotor.set(1);
    }
  }
}
