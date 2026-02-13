# GoogleORToolsExample
Simple example using Google OR-Tools

## Overview
This project demonstrates how to use Google OR-Tools in Java with Gradle. OR-Tools is a powerful optimization library for solving linear programming, constraint programming, and other optimization problems.

## Example
The `SimpleSolver` class demonstrates solving a linear programming problem:

**Objective:** Maximize `3x + 4y`

**Subject to:**
- `x + 2y ≤ 14`
- `3x - y ≥ 0`
- `x - y ≤ 2`
- `x, y ≥ 0`

## Prerequisites
- Java 11 or higher
- Gradle (wrapper included)

## Building
```bash
./gradlew build
```

## Running
```bash
./gradlew run
```

Or on Windows:
```bash
.\gradlew.bat run
```

## Expected Output
The program will find the optimal solution and display:
- The optimal values of x and y
- The maximum objective function value
- Solver statistics (time, iterations)

## Dependencies
- Google OR-Tools 9.8.3296
- Java 11 or higher
- Gradle 8.10.2 (wrapper included)

## Project Structure
```
GoogleORToolsExample/
├── build.gradle
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── SimpleSolver.java
└── README.md
```

