package frc.robot.lib.util.swerveUtil;

import edu.wpi.first.math.geometry.Rotation2d;

public class RevSwerveModuleConstants {
    public final int driveMotorID;
    public final int angleMotorID;
    public final int cancoderID;
    public final Rotation2d rotOffset;

    /**
     * Swerve Module Constants to be used when creating swerve modules.
     * @param driveMotorID
     * @param angleMotorID
     * @param canCoderID
     * @param rotOffset
     * @param canBus
     */
    public RevSwerveModuleConstants(int driveMotorID, int angleMotorID, int canCoderID, Rotation2d rotOffset) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.cancoderID = canCoderID;
        this.rotOffset = rotOffset;
    }
}