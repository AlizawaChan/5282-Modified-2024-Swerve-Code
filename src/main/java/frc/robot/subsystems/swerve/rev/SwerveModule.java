package frc.robot.subsystems.swerve.rev;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;


public interface SwerveModule
 {
    public void setDesiredState(SwerveModuleState desiredState, boolean isClosedLoop);

    public Rotation2d getCANcoder();

    public SwerveModuleState getState();

    public SwerveModulePosition getPosition();
    
    public int getModuleNumber(); 

    public void setModuleNumber(int moduleNumber);

}