#file:instruction

This file tracks the work items for JIRA issue AB-1 and the steps to prepare, build, test, and push the feature.

## Issue

- JIRA: AB-1

## Tasks

1. Get the details of JIRA ISSUE AB-1
2. Re-add the description and begin development and make Jira Status "In-Progress"
3. Ensure use latest Java and Gradle configuration
4. Write JUnit test cases for the project
5. Build the code
6. Run the code for confirmation
7. Create a new feature branch
8. Commit and push the changes to feature branch
9. Raise a PR from feature to develop branch and make Jira Status "In-Review"
10 create a Docker file for Deployment

## Quick Notes & Commands

- Java/Gradle: Use the project's Gradle wrapper and a recent Java LTS (e.g., Java 17 or newer).
- Build (Linux/macOS): `./gradlew clean build`
- Build (Windows): `gradlew.bat clean build`
- Run tests: `./gradlew test` or `gradlew.bat test`
- Create feature branch: `git checkout -b feature/your-feature-name`
- Commit: `git add .` then `git commit -m "implement: <short description>"`
- Push: `git push -u origin feature/your-feature-name`
- Open PR: create a pull request from `feature/your-feature-name` into `develop` in your Git host UI.

## Acceptance

- All unit tests pass.
- The project builds successfully with the specified Java/Gradle setup.
- A pull request is opened targeting `develop`.
