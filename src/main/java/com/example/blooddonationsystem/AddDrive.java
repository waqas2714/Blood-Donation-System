package com.example.blooddonationsystem;

public class AddDrive {
        private String driveName;
        private String driveLocation;

        // Constructor
        public AddDrive(String driveName, String driveLocation) {
            this.driveName = driveName;
            this.driveLocation = driveLocation;
        }

        // Default constructor
        public AddDrive() {
        }

        // Getter for driveName
        public String getDriveName() {
            return driveName;
        }

        // Setter for driveName
        public void setDriveName(String driveName) {
            this.driveName = driveName;
        }

        // Getter for driveLocation
        public String getDriveLocation() {
            return driveLocation;
        }

        // Setter for driveLocation
        public void setDriveLocation(String driveLocation) {
            this.driveLocation = driveLocation;
        }

}
